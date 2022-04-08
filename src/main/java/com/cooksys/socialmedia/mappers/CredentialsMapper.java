package com.cooksys.socialmedia.mappers;

import org.mapstruct.Mapper;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.embeddables.CredentialsEmbeddable;

@Mapper(componentModel = "spring")
public interface CredentialsMapper {
	
	CredentialsEmbeddable dtoToEntity(CredentialsDto credentialsDto);

	CredentialsDto entityToDto(CredentialsEmbeddable credentials);

}
