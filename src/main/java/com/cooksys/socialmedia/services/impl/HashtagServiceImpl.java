package com.cooksys.socialmedia.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.socialmedia.dtos.HashtagResponseDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.mappers.HashtagMapper;
import com.cooksys.socialmedia.mappers.TweetMapper;
import com.cooksys.socialmedia.repositories.HashtagRepository;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.services.HashtagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

	private final HashtagMapper hashtagMapper;

	private final HashtagRepository hashtagRepository;

	private final TweetMapper tweetMapper;

	private final TweetRepository tweetRepository;

	@Override
	public List<HashtagResponseDto> getAllHashtags() {
		List<Hashtag> allHashtags = hashtagRepository.findAll();
		return hashtagMapper.entitiesToDtos(allHashtags);
	}

	@Override
	public List<TweetResponseDto> getAllTweetsByHashtag(String label) {
		Optional<Hashtag> hashtagByLabel = hashtagRepository.findHashtagByLabel(label);
		List<TweetResponseDto> tweetsWithHashtag = tweetMapper.entitiesToDtos(hashtagByLabel.get().getTweets());
		return tweetsWithHashtag;
	}

}
