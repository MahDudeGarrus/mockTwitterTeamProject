package com.cooksys.socialmedia.services.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.cooksys.socialmedia.dtos.ContextDto;
import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.HashtagResponseDto;
import com.cooksys.socialmedia.dtos.TweetRequestDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.embeddables.CredentialsEmbeddable;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.BadRequestException;
import com.cooksys.socialmedia.exceptions.NotAuthorizedException;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import com.cooksys.socialmedia.mappers.CredentialsMapper;
import com.cooksys.socialmedia.mappers.HashtagMapper;
import com.cooksys.socialmedia.mappers.TweetMapper;
import com.cooksys.socialmedia.mappers.UserMapper;
import com.cooksys.socialmedia.repositories.HashtagRepository;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.TweetService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

	private final TweetMapper tweetMapper;
	private final CredentialsMapper credentialsMapper;
	private final UserMapper userMapper;
	private final HashtagMapper hashtagMapper;
	private final TweetRepository tweetRepository;
	private final UserRepository userRepository;
	private final HashtagRepository hashtagRepository;
	private final ValidateServiceImpl validateServiceImpl;

	private User getUserEntity(String username) throws NotFoundException {

		if (validateServiceImpl.usernameExists(username) == false
				|| userRepository.findByCredentialsUsernameAndDeletedFalse(username).isEmpty()
				|| userRepository.findByCredentialsUsernameAndDeletedFalse(username).get().isDeleted() == true) {
			throw new NotFoundException("User not found with username: " + username);
		} else {
			Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
			return optionalUser.get();
		}
	}

	private User getUserByCredentials(CredentialsEmbeddable credentials) {
		Optional<User> optionalUser = userRepository.findByCredentialsAndDeletedFalse(credentials);
		if (optionalUser.isEmpty()) {
			throw new NotFoundException("No user found with credentials: ");// + credentials.toString());
		} else if (optionalUser.get().isDeleted()) {
			throw new BadRequestException("This user is inactive.");
		}
		return optionalUser.get();
	}

	private void validateTweetRequest(TweetRequestDto tweetRequestDto) {
		if (tweetRequestDto.getContent() == null) {
			throw new BadRequestException("Missing content on tweet request dto");
		} else if (tweetRequestDto.getCredentials().getUsername() == null
				|| tweetRequestDto.getCredentials().getPassword() == null) {
			throw new BadRequestException("Missing credentials on tweet request dto");
		}
	}

	private void validateAuthorRequest(CredentialsDto credentialsDto, User author) {
		if (!userMapper.entityToDto(author).getUsername().equals(credentialsDto.getUsername())) {
			throw new NotAuthorizedException("Credentials do not match author of tweet");
		}
	}

	private Tweet getTweetById(Long id) {
		Optional<Tweet> optionalTweet = tweetRepository.findByIdAndDeletedFalse(id);
		if (optionalTweet.isEmpty()) {
			throw new NotFoundException("No tweet found with id: " + id);
		} else if (optionalTweet.get().isDeleted()) {
			throw new BadRequestException("This tweet was deleted");
		}
		return optionalTweet.get();
	}

	private List<Tweet> getAfter(Tweet reply, List<Tweet> after) {
		if (!reply.isDeleted()) {
			after.add(reply);
		}
		if (reply.getReplies() != null) {
			for (Tweet replyToReply : reply.getReplies()) {
				getAfter(replyToReply, after);
			}
		}
		return after;
	}

	@Override
	public List<TweetResponseDto> getAllTweets() {
		List<TweetResponseDto> tweetResponseDtos = new ArrayList<>();
		for (Tweet tweet : tweetRepository.findAllByDeletedFalse()) {
			String username = tweet.getAuthor().getCredentials().getUsername();
			TweetResponseDto tweetResponseDto = tweetMapper.entityToDto(tweet);
			tweetResponseDto.getAuthor().setUsername(username);
			tweetResponseDtos.add(tweetResponseDto);
		}

		List<TweetResponseDto> reversedList = new ArrayList<>();
		for (int i = tweetResponseDtos.size() - 1; i >= 0; i--) {
			reversedList.add(tweetResponseDtos.get(i));
		}
		return reversedList;
	}

	@Override
	public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {
		validateTweetRequest(tweetRequestDto);
		Tweet tweet = tweetMapper.dtoToEntity(tweetRequestDto);
		tweet.setAuthor(getUserByCredentials(credentialsMapper.dtoToEntity(tweetRequestDto.getCredentials())));
		tweet.setPosted(Timestamp.from(Instant.now()));
		List<Hashtag> hashtagList = new ArrayList<>();
		List<User> usersMentionedList = new ArrayList<>();
		for (String str : tweet.getContent().split(" ")) {
			if (str.contains("#")) {
				if (validateServiceImpl.hashtagExists(str)) {
					Optional<Hashtag> hashtag = hashtagRepository.findByLabel(str);
					hashtag.get().setLastUsed(Timestamp.from(Instant.now()));
					hashtagList.add(hashtag.get());
					hashtagRepository.saveAndFlush(hashtag.get());
				} else {
					Hashtag hashtag = new Hashtag();
					hashtag.setLabel(str);
					hashtag.setFirstUsed(Timestamp.from(Instant.now()));
					hashtag.setLastUsed(Timestamp.from(Instant.now()));
					hashtagList.add(hashtag);
					hashtagRepository.saveAndFlush(hashtag);
				}
			}
			if (str.contains("@")) {
				String user = str.replace("@", "");
				usersMentionedList.add(getUserEntity(user));
			}
		}
		tweet.setHashtags(hashtagList);
		tweet.setUsersMentioned(usersMentionedList);
		tweetRepository.saveAndFlush(tweet);
		return tweetMapper.entityToDto(tweet);
	}

	@Override
	public TweetResponseDto getTweet(Long id) {
		Tweet tweet = getTweetById(id);
		String username = tweet.getAuthor().getCredentials().getUsername();
		TweetResponseDto tweetResponseDto = tweetMapper.entityToDto(tweet);
		tweetResponseDto.getAuthor().setUsername(username);
		return tweetResponseDto;
	}

	@Override
	public TweetResponseDto deleteTweet(CredentialsDto credentialsDto, Long id) {
		Tweet tweetToDelete = getTweetById(id);
		User author = tweetToDelete.getAuthor();
		validateAuthorRequest(credentialsDto, author);
		tweetToDelete.setDeleted(true);
		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(tweetToDelete));
	}

	@Override
	public void likeTweet(CredentialsDto credentialsDto, Long id) {

		User liker = getUserByCredentials(credentialsMapper.dtoToEntity(credentialsDto));

		Tweet tweetToLike = getTweetById(id);
		liker.getLikedTweets().add(tweetToLike);
		liker.setLikedTweets(liker.getLikedTweets());
		userRepository.saveAndFlush(liker);
	}

	@Override
	public TweetResponseDto replyToTweet(TweetRequestDto tweetRequestDto, Long id) {
		Tweet rootTweet = getTweetById(id);
		Tweet replyingTweet = tweetMapper.dtoToEntity(tweetRequestDto);
		User author = getUserByCredentials(credentialsMapper.dtoToEntity(tweetRequestDto.getCredentials()));
		validateTweetRequest(tweetRequestDto);
		replyingTweet.setInReplyTo(rootTweet);
		replyingTweet.setAuthor(author);
		replyingTweet.setPosted(Timestamp.from(Instant.now()));
		rootTweet.getReplies().add(replyingTweet);
		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(replyingTweet));
	}

	@Override
	public TweetResponseDto repostTweet(CredentialsDto credentialsDto, Long id) {

		Tweet target = getTweetById(id);

		User author = getUserByCredentials(credentialsMapper.dtoToEntity(credentialsDto));
		Tweet repost = new Tweet();
		repost.setAuthor(author);
		repost.setPosted(Timestamp.from(Instant.now()));
		repost.setDeleted(false);
		repost.setContent(null);
		repost.setRepostOf(target);
		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(repost));
	}

	@Override
	public List<HashtagResponseDto> getHashtags(Long id) {
		return hashtagMapper.entitiesToDtos(getTweetById(id).getHashtags());
	}

	@Override
	public List<UserResponseDto> getLikes(Long id) {
		List<User> tweetLikes = getTweetById(id).getLikes();
		return userMapper.entitiesToDtos(tweetLikes);
	}

	@Override
	public ContextDto getContext(Long id) {
		Tweet target = getTweetById(id);

		ContextDto context = new ContextDto();
		context.setTarget(tweetMapper.entityToDto(target));

		List<Tweet> after = new ArrayList<>();
		for (Tweet reply : target.getReplies()) {
			getAfter(reply, after);
		}
		context.setAfter(tweetMapper.entitiesToDtos(after));

		List<Tweet> before = new ArrayList<>();
		while (target.getInReplyTo() != null) {
			if (target.getInReplyTo().isDeleted() == false) {
				before.add(target.getInReplyTo());
			}
			target = target.getInReplyTo();
		}
		context.setBefore(tweetMapper.entitiesToDtos(before));

		return context;
	}

	@Override
	public List<TweetResponseDto> getReplies(Long id) {
		Tweet rootTweet = getTweetById(id);
		List<Tweet> result = new ArrayList<>();
		for (Tweet reply : rootTweet.getReplies()) {
			if (!reply.isDeleted()) {
				result.add(reply);
			}
		}
		return tweetMapper.entitiesToDtos(result);
	}

	@Override
	public List<TweetResponseDto> getReposts(Long id) {
		Tweet rootTweet = getTweetById(id);
		List<Tweet> repost = new ArrayList<>();
		for (Tweet tweet : rootTweet.getReposts()) {
			if (!tweet.isDeleted()) {
				repost.add(tweet);
			}
		}
		return tweetMapper.entitiesToDtos(repost);
	}

	@Override
	public List<UserResponseDto> getMentions(Long id) {
		Tweet tweet = getTweetById(id);
		List<UserResponseDto> allMentions = new ArrayList<>();
		for (User mentioned : tweet.getUsersMentioned()) {
			if (!mentioned.isDeleted()) {
				allMentions.add(userMapper.entityToDto(mentioned));
			}
		}
		return allMentions;
	}

}
