package com.cooksys.socialmedia.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.socialmedia.dtos.ContextDto;
import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.HashtagResponseDto;
import com.cooksys.socialmedia.dtos.TweetRequestDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.services.HashtagService;
import com.cooksys.socialmedia.services.TweetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {

	private final TweetService tweetService;

	@GetMapping
	public List<TweetResponseDto> getAllTweets() {
		return tweetService.getAllTweets();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TweetResponseDto createTweet(@RequestBody TweetRequestDto tweetRequestDto) {
		return tweetService.createTweet(tweetRequestDto);
	}

	@GetMapping("/{id}")
	public TweetResponseDto getTweet(@PathVariable Long id) {
		return tweetService.getTweet(id);
	}

	@DeleteMapping("/{id}")
	public TweetResponseDto deleteTweet(@RequestBody CredentialsDto credentialsDto, @PathVariable Long id) {
		return tweetService.deleteTweet(credentialsDto, id);
	}

	@PostMapping("/{id}/like")
	public void likeTweet(@RequestBody CredentialsDto credentialsDto, @PathVariable Long id) {
		tweetService.likeTweet(credentialsDto, id);
	}

	@PostMapping("/{id}/reply")
	public TweetResponseDto replyToTweet(@RequestBody TweetRequestDto tweetRequestDto,
			 @PathVariable Long id) {
		return tweetService.replyToTweet(tweetRequestDto, id);		
	}
	
	@PostMapping("/{id}/repost")
	public TweetResponseDto repostTweet(@RequestBody CredentialsDto credentialsDto, @PathVariable Long id) {
		return tweetService.repostTweet(credentialsDto, id);		
	}
	
	@GetMapping("/{id}/tags")
	public List<HashtagResponseDto> getHashtags(@PathVariable Long id) {
		return tweetService.getHashtags(id);
	}
	
	@GetMapping("/{id}/likes")
	public List<UserResponseDto> getLikes(@PathVariable Long id) {
		return tweetService.getLikes(id);
	}
	
	@GetMapping("/{id}/context")
	public ContextDto getContext(@PathVariable Long id) {
		return tweetService.getContext(id);
	}
	
	@GetMapping("/{id}/replies")
	public List<TweetResponseDto> getReplies(@PathVariable Long id) {
		return tweetService.getReplies(id);
	}
	
	@GetMapping("/{id}/reposts")
	public List<TweetResponseDto> getReposts(@PathVariable Long id) {
		return tweetService.getReposts(id);
	}
	
	@GetMapping("/{id}/mentions")
	public List<UserResponseDto> getMentions(@PathVariable Long id) {
		return tweetService.getMentions(id);
	}
	
}
