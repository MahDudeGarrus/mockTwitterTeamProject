package com.cooksys.socialmedia.dtos;

import java.util.List;

import com.cooksys.socialmedia.entities.Tweet;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContextDto {

	private TweetResponseDto target;
	
	private List<TweetResponseDto> before;
	
	private List<TweetResponseDto> after;
	
}
