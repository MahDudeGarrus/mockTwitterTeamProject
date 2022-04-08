package com.cooksys.socialmedia.mappers;

import org.mapstruct.Mapper;

import com.cooksys.socialmedia.dtos.ProfileDto;
import com.cooksys.socialmedia.embeddables.ProfileEmbeddable;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
	
	ProfileEmbeddable dtoToEntity(ProfileDto credentialsDto);

	ProfileDto entityToDto(ProfileEmbeddable credentials);

}
