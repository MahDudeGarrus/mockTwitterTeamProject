package com.cooksys.socialmedia.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.cooksys.socialmedia.dtos.UserRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.mappers.ProfileMapper;
import com.cooksys.socialmedia.mappers.CredentialsMapper;

@Mapper(componentModel = "spring", uses = { ProfileMapper.class, CredentialsMapper.class })
public interface UserMapper {
	
	User dtoToEntity(UserRequestDto userRequestDto);

	@Mapping(target = "username", source = "credentials.username")
	UserResponseDto entityToDto(User user);

	List<UserResponseDto> entitiesToDtos(List<User> users);
}
