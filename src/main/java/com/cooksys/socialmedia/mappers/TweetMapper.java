package com.cooksys.socialmedia.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.socialmedia.dtos.TweetRequestDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.mappers.UserMapper;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface TweetMapper {
	
	Tweet dtoToEntity(TweetRequestDto tweetRequestDto);

	TweetResponseDto entityToDto(Tweet tweet);

	List<TweetResponseDto> entitiesToDtos(List<Tweet> tweet);

}
