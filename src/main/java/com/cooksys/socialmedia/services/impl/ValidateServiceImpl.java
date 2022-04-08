package com.cooksys.socialmedia.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.HashtagResponseDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.embeddables.CredentialsEmbeddable;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.mappers.CredentialsMapper;
import com.cooksys.socialmedia.mappers.HashtagMapper;
import com.cooksys.socialmedia.mappers.UserMapper;
import com.cooksys.socialmedia.repositories.HashtagRepository;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.ValidateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {
	
	private final HashtagRepository hashtagRepository;
	
	private final HashtagMapper hashtagMapper;
	
	private final UserRepository userRepository;
	
	private final UserMapper userMapper;
	
	private final CredentialsMapper credentialsMapper;

	@Override
	public Boolean hashtagExists(String label) {
		Optional<Hashtag> existingHashtag = hashtagRepository.findHashtagByLabel(label);
		return existingHashtag.isPresent();
	}

	@Override
	public Boolean usernameExists(String username) {
		Optional<User> findByUsername = userRepository.findByCredentialsUsername(username);
		return findByUsername.isPresent();
	}

	@Override
	public Boolean usernameAvailable(String username) {
		Optional<User> findByUsername = userRepository.findByCredentialsUsername(username);
		if(findByUsername.isPresent()) {
			return false;
		} else {
			return true;
		}
	}

}
