package com.cooksys.socialmedia.services.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.ErrorDto;
import com.cooksys.socialmedia.dtos.ProfileDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.embeddables.CredentialsEmbeddable;
import com.cooksys.socialmedia.embeddables.ProfileEmbeddable;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.BadRequestException;
import com.cooksys.socialmedia.exceptions.NotAuthorizedException;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import com.cooksys.socialmedia.mappers.CredentialsMapper;
import com.cooksys.socialmedia.mappers.ProfileMapper;
import com.cooksys.socialmedia.mappers.TweetMapper;
import com.cooksys.socialmedia.mappers.UserMapper;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final CredentialsMapper credentialsMapper;
	private final ProfileMapper profileMapper;
	private final TweetMapper tweetMapper;
	private final TweetRepository tweetRepository;
	private final ValidateServiceImpl validateServiceImpl;

	private User getUserEntity(String username) throws NotFoundException {
		Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
		if (validateServiceImpl.usernameExists(username) == false
				|| userRepository.findByCredentialsUsernameAndDeletedFalse(username).isEmpty()
				|| userRepository.findByCredentialsUsernameAndDeletedFalse(username).get().isDeleted() == true) {
			throw new NotFoundException("User not found with username: " + username);
		}
		return optionalUser.get();
	}

	@Override
	public List<UserResponseDto> getAllUsers() {
		List<User> userList = userRepository.findAllByDeletedFalse();
		List<UserResponseDto> userDtoList = new ArrayList<>();
		for (User user : userList) {
			UserResponseDto userDto = userMapper.entityToDto(user);
			userDto.setUsername(user.getCredentials().getUsername());
			userDtoList.add(userDto);
		}
		return userDtoList;

	}

	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) throws BadRequestException {
		if (validateServiceImpl.usernameExists(userRequestDto.getCredentials().getUsername())) {
			throw new BadRequestException("username already exists");
		}
		for (User userCheck : userRepository.findAll()) {
			if (userMapper.dtoToEntity(userRequestDto).getCredentials().equals(userCheck.getCredentials())) {
				if (userCheck.isDeleted() == true) {
					userCheck.setDeleted(false);
					return userMapper.entityToDto(userCheck);
				} else {
					throw new BadRequestException("user already exists");
				}
			}
		}
		if (userRequestDto.getCredentials().getUsername() == null
				|| userRequestDto.getCredentials().getPassword() == null
				|| userRequestDto.getProfile().getEmail() == null) {
			throw new BadRequestException("Not enough information given");
		} else {
			User user = userMapper.dtoToEntity(userRequestDto);
			user.setJoined(Timestamp.from(Instant.now()));
			userRepository.saveAndFlush(user);
			String username = user.getCredentials().getUsername();
			UserResponseDto userDto = userMapper.entityToDto(user);
			userDto.setUsername(username);
			return userDto;
		}
	}

	@Override
	public UserResponseDto getUser(String username) {
		User user = getUserEntity(username);
		String usernameDto = user.getCredentials().getUsername();
		UserResponseDto userDto = userMapper.entityToDto(user);
		userDto.setUsername(usernameDto);
		return userDto;

	}

	@Override
	public UserResponseDto updateUser(UserRequestDto userDto, String username) throws NotAuthorizedException {
		User user = getUserEntity(username);

		if (user.getCredentials().equals(userMapper.dtoToEntity(userDto).getCredentials())) {

			user.setProfile(userMapper.dtoToEntity(userDto).getProfile());
			userRepository.saveAndFlush(user);
			UserResponseDto userResponseDto = userMapper.entityToDto(user);

			return userResponseDto;
		} else {
			throw new NotAuthorizedException("Credentials do not match username");
		}
	}

	@Override
	public UserResponseDto deleteUser(CredentialsDto credentialsDto, String username) throws NotAuthorizedException {
		User user = getUserEntity(username);
		if (user.getCredentials().equals(credentialsMapper.dtoToEntity(credentialsDto))) {
			user.setDeleted(true);
			userRepository.saveAndFlush(user);
			return userMapper.entityToDto(user);
		} else {
			throw new NotAuthorizedException("Credentials do not match username");
		}
	}

	@Override
	public void followUser(CredentialsDto credentialsDto, String username)
			throws NotAuthorizedException, BadRequestException {
		boolean checker = false;
		for (User userCheck : userRepository.findAll()) {
			if (credentialsMapper.dtoToEntity(credentialsDto).equals(userCheck.getCredentials())) {
				User followerCheck = getUserEntity(credentialsDto.getUsername());
				if (getUserEntity(username).getFollowers().contains(followerCheck)) {

					throw new BadRequestException("user is already followed by this person");
				}
				User follower = getUserEntity(credentialsMapper.dtoToEntity(credentialsDto).getUsername());
				User followed = getUserEntity(username);
				List<User> followingList = follower.getFollowing();
				followingList.add(followed);
				follower.setFollowing(followingList);
				userRepository.saveAndFlush(follower);
				checker = true;
			}
		}
		if (checker == false) {
			throw new NotAuthorizedException("Credentials do not match");
		}

	}

	@Override
	public void unfollowUser(CredentialsDto credentialsDto, String username)
			throws NotAuthorizedException, BadRequestException {
		boolean checker = false;
		for (User userCheck : userRepository.findAll()) {
			if (userCheck.getCredentials().equals(credentialsMapper.dtoToEntity(credentialsDto))) {
				User followerCheck = getUserEntity(credentialsDto.getUsername());
				if (!getUserEntity(username).getFollowers().contains(followerCheck)) {
					throw new BadRequestException("user is not followed by this person");
				} else {
					User unfollower = getUserEntity(credentialsDto.getUsername());
					User unfollowed = getUserEntity(username);
					List<User> following = unfollower.getFollowing();
					following.remove(unfollowed);
					unfollower.setFollowing(following);
					userRepository.saveAndFlush(unfollower);
					checker = true;
				}
			}
		}
		if (checker == false) {
			throw new NotAuthorizedException("Credentials do not match");
		}

	}

	@Override
	public List<TweetResponseDto> getFeed(String username) {

		User user = getUserEntity(username);

		List<TweetResponseDto> feed = new ArrayList<>();
		for (Tweet tweet : tweetRepository.findAll()) {
			if (user.getFollowing().contains(tweet.getAuthor())
					|| user.getTweets().contains(tweet) && tweet.isDeleted() == false) {
				feed.add(tweetMapper.entityToDto(tweet));
			}
		}
		List<TweetResponseDto> feedReversed = new ArrayList<>();
		for (int i = feed.size() - 1; i >= 0; i--) {
			feedReversed.add(feed.get(i));
		}
		return feedReversed;
	}

	@Override
	public List<TweetResponseDto> getTweets(String username) {

		List<TweetResponseDto> tweetResponseDtoList = new ArrayList<>();

		User user = getUserEntity(username);
		for (Tweet tweet : user.getTweets()) {
			if (tweet.isDeleted() == false) {
				tweetResponseDtoList.add(tweetMapper.entityToDto(tweet));
			}
		}

		for (TweetResponseDto tweetResponseDto : tweetResponseDtoList) {
			tweetResponseDto.getAuthor().setUsername(username);
		}
		return tweetResponseDtoList;
	}

	@Override
	public List<TweetResponseDto> getMentions(String username) {

		User user = getUserEntity(username);
		List<TweetResponseDto> tweetDtoList = new ArrayList<>();
		for (Tweet tweet : user.getMentions()) {
			if (tweet.isDeleted() == false) {
				String usernameDto = tweet.getAuthor().getCredentials().getUsername();
				TweetResponseDto tweetDto = tweetMapper.entityToDto(tweet);
				tweetDto.getAuthor().setUsername(usernameDto);
				tweetDtoList.add(tweetDto);
			}
		}

		return tweetDtoList;
	}

	@Override
	public List<UserResponseDto> getFollowers(String username) {

		User user = getUserEntity(username);
		List<UserResponseDto> userDtoList = new ArrayList<>();
		for (User userObj : user.getFollowers()) {
			if (userObj.isDeleted() == false) {
				String usernameDto = userObj.getCredentials().getUsername();
				UserResponseDto userDto = userMapper.entityToDto(userObj);
				userDto.setUsername(usernameDto);
				userDtoList.add(userDto);
			}
		}
		return userDtoList;

	}

	@Override
	public List<UserResponseDto> getFollowing(String username) {

		User user = getUserEntity(username);
		List<UserResponseDto> userDtoList = new ArrayList<>();
		for (User userObj : user.getFollowing()) {
			if (userObj.isDeleted() == false) {
				String usernameDto = userObj.getCredentials().getUsername();
				UserResponseDto userDto = userMapper.entityToDto(userObj);
				userDto.setUsername(usernameDto);
				userDtoList.add(userDto);
			}
		}
		return userDtoList;
	}

}
