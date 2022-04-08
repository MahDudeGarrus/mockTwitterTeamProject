package com.cooksys.socialmedia.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.ProfileDto;
import com.cooksys.socialmedia.dtos.UserRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.services.TweetService;
import com.cooksys.socialmedia.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

	private final UserService userService;


	@GetMapping
	public List<UserResponseDto> getAllUsers() {
		return userService.getAllUsers();
	}

	@PostMapping
	public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto) {
		return userService.createUser(userRequestDto);
	}

	@GetMapping("/@{username}")
	public UserResponseDto getUser(@PathVariable String username) {
		return userService.getUser(username);
	}

	@PatchMapping("/@{username}")
	public UserResponseDto updateUser(@RequestBody UserRequestDto userDto, @PathVariable String username) {
		return userService.updateUser(userDto, username);
	}

	@DeleteMapping("/@{username}")
	public UserResponseDto deleteUser(@RequestBody CredentialsDto credentialsDto, @PathVariable String username) {
		return userService.deleteUser(credentialsDto, username);
	}

	@PostMapping("/@{username}/follow")
	public void followUser(@RequestBody CredentialsDto credentialsDto, @PathVariable String username) {
		userService.followUser(credentialsDto, username);
	}

	@PostMapping("/@{username}/unfollow")
	public void unfollowUser(@RequestBody CredentialsDto credentialsDto, @PathVariable String username) {
		userService.unfollowUser(credentialsDto, username);
	}

	@GetMapping("/@{username}/feed")
	public List<TweetResponseDto> getFeed(@PathVariable String username) {
		return userService.getFeed(username);
	}

	@GetMapping("/@{username}/tweets")
	public List<TweetResponseDto> getTweets(@PathVariable String username) {
		return userService.getTweets(username);
	}

	@GetMapping("/@{username}/mentions")
	public List<TweetResponseDto> getMentions(@PathVariable String username) {
		return userService.getMentions(username);
	}

	@GetMapping("/@{username}/followers")
	public List<UserResponseDto> getFollowers(@PathVariable String username) {
		return userService.getFollowers(username);
	}

	@GetMapping("/@{username}/following")
	public List<UserResponseDto> getFollowing(@PathVariable String username) {
		return userService.getFollowing(username);
	}

}
