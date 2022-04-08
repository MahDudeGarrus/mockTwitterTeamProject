package com.cooksys.socialmedia.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.socialmedia.dtos.HashtagResponseDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.services.HashtagService;
import com.cooksys.socialmedia.services.TweetService;
import com.cooksys.socialmedia.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class HashtagController {

	private final HashtagService hashtagService;
	
	@GetMapping
	public List<HashtagResponseDto> getAllHashtags() {
		return hashtagService.getAllHashtags();
	}
	
	@GetMapping("/{label}")
	public List<TweetResponseDto> getAllTweetsByHashtag(@PathVariable String label) {
		return hashtagService.getAllTweetsByHashtag(label);
	}	
	
}
