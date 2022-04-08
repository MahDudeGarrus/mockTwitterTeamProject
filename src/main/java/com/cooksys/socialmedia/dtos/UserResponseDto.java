package com.cooksys.socialmedia.dtos;

import java.sql.Timestamp;



import lombok.Data;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@Data
public class UserResponseDto {
	
	private String username;
	
	private ProfileDto profile;
	
	private Timestamp joined;
	
}
