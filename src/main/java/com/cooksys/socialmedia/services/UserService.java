package com.cooksys.socialmedia.services;

import java.util.List;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.ProfileDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.exceptions.BadRequestException;
import com.cooksys.socialmedia.exceptions.NotAuthorizedException;

public interface UserService {

//	UserResponseDto getUserById(Long id);
	
	List<UserResponseDto> getAllUsers();

	UserResponseDto createUser(UserRequestDto userRequestDto);

	UserResponseDto getUser(String username);

	UserResponseDto updateUser(UserRequestDto userDto, String username);

	UserResponseDto deleteUser(CredentialsDto credentialsDto, String username);

	void followUser(CredentialsDto credentialsDto, String username) throws NotAuthorizedException, BadRequestException;

	void unfollowUser(CredentialsDto credentialsDto, String username);

	List<TweetResponseDto> getFeed(String username);

	List<TweetResponseDto> getTweets(String username);

	List<TweetResponseDto> getMentions(String username);

	List<UserResponseDto> getFollowers(String username);

	List<UserResponseDto> getFollowing(String username);

}
