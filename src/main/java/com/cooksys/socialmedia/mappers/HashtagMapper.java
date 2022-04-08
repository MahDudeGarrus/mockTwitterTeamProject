package com.cooksys.socialmedia.mappers;

import java.util.List;
import java.util.Optional;

import org.mapstruct.Mapper;

import com.cooksys.socialmedia.dtos.HashtagResponseDto;
import com.cooksys.socialmedia.entities.Hashtag;

@Mapper(componentModel = "spring")
public interface HashtagMapper {

	HashtagResponseDto entityToDto(Optional<Hashtag> optionalHashtag);

	List<HashtagResponseDto> entitiesToDtos(List<Hashtag> hashtag);
}
