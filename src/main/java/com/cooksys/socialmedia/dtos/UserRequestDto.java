package com.cooksys.socialmedia.dtos;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.ProfileDto;
@NoArgsConstructor
@Data
public class UserRequestDto {
	
	private CredentialsDto credentials;
	
	private ProfileDto profile;

}
