package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dtos.HashtagResponseDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;

public interface ValidateService {

	Boolean hashtagExists(String label);

	Boolean usernameExists(String username);

	Boolean usernameAvailable(String username);

}
