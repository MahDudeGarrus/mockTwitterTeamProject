package com.cooksys.socialmedia.services;

import java.util.List;

import com.cooksys.socialmedia.dtos.HashtagResponseDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;

public interface HashtagService {

	List<HashtagResponseDto> getAllHashtags();

	List<TweetResponseDto> getAllTweetsByHashtag(String label);

}
