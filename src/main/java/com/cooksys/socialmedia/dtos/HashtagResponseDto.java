package com.cooksys.socialmedia.dtos;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.cooksys.socialmedia.entities.Tweet;


import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class HashtagResponseDto {
	
	private String label;
	
	private Timestamp firstUsed;
	
	private Timestamp lastUsed;
	
}
