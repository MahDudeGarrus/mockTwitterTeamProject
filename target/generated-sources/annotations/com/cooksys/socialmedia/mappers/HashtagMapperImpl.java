package com.cooksys.socialmedia.mappers;

import com.cooksys.socialmedia.dtos.HashtagResponseDto;
import com.cooksys.socialmedia.entities.Hashtag;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-27T19:45:47-0500",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.12 (Oracle Corporation)"
)
@Component
public class HashtagMapperImpl implements HashtagMapper {

    @Override
    public HashtagResponseDto entityToDto(Optional<Hashtag> optionalHashtag) {
        if ( optionalHashtag == null ) {
            return null;
        }

        HashtagResponseDto hashtagResponseDto = new HashtagResponseDto();

        return hashtagResponseDto;
    }

    @Override
    public List<HashtagResponseDto> entitiesToDtos(List<Hashtag> hashtag) {
        if ( hashtag == null ) {
            return null;
        }

        List<HashtagResponseDto> list = new ArrayList<HashtagResponseDto>( hashtag.size() );
        for ( Hashtag hashtag1 : hashtag ) {
            list.add( hashtagToHashtagResponseDto( hashtag1 ) );
        }

        return list;
    }

    protected HashtagResponseDto hashtagToHashtagResponseDto(Hashtag hashtag) {
        if ( hashtag == null ) {
            return null;
        }

        HashtagResponseDto hashtagResponseDto = new HashtagResponseDto();

        hashtagResponseDto.setLabel( hashtag.getLabel() );
        hashtagResponseDto.setFirstUsed( hashtag.getFirstUsed() );
        hashtagResponseDto.setLastUsed( hashtag.getLastUsed() );

        return hashtagResponseDto;
    }
}
