package com.cooksys.socialmedia.services;

import java.util.List;

import com.cooksys.socialmedia.dtos.ContextDto;
import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.HashtagResponseDto;
import com.cooksys.socialmedia.dtos.TweetRequestDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;

public interface TweetService {

	List<TweetResponseDto> getAllTweets();

	TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

	TweetResponseDto getTweet(Long id);

	TweetResponseDto deleteTweet(CredentialsDto credentialsDto, Long id);

	void likeTweet(CredentialsDto credentialsDto, Long id);

	TweetResponseDto replyToTweet(TweetRequestDto tweetRequestDto, Long id);

	TweetResponseDto repostTweet(CredentialsDto credentialsDto, Long id);

	List<HashtagResponseDto> getHashtags(Long id);

	List<UserResponseDto> getLikes(Long id);

	ContextDto getContext(Long id);

	List<TweetResponseDto> getReplies(Long id);

	List<TweetResponseDto> getReposts(Long id);

	List<UserResponseDto> getMentions(Long id);

}
