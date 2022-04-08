package com.cooksys.socialmedia.mappers;

import com.cooksys.socialmedia.dtos.TweetRequestDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.entities.Tweet;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-27T19:45:47-0500",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.12 (Oracle Corporation)"
)
@Component
public class TweetMapperImpl implements TweetMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Tweet dtoToEntity(TweetRequestDto tweetRequestDto) {
        if ( tweetRequestDto == null ) {
            return null;
        }

        Tweet tweet = new Tweet();

        tweet.setContent( tweetRequestDto.getContent() );

        return tweet;
    }

    @Override
    public TweetResponseDto entityToDto(Tweet tweet) {
        if ( tweet == null ) {
            return null;
        }

        TweetResponseDto tweetResponseDto = new TweetResponseDto();

        tweetResponseDto.setId( tweet.getId() );
        tweetResponseDto.setAuthor( userMapper.entityToDto( tweet.getAuthor() ) );
        tweetResponseDto.setPosted( tweet.getPosted() );
        tweetResponseDto.setContent( tweet.getContent() );
        tweetResponseDto.setInReplyTo( entityToDto( tweet.getInReplyTo() ) );
        tweetResponseDto.setRepostOf( entityToDto( tweet.getRepostOf() ) );

        return tweetResponseDto;
    }

    @Override
    public List<TweetResponseDto> entitiesToDtos(List<Tweet> tweet) {
        if ( tweet == null ) {
            return null;
        }

        List<TweetResponseDto> list = new ArrayList<TweetResponseDto>( tweet.size() );
        for ( Tweet tweet1 : tweet ) {
            list.add( entityToDto( tweet1 ) );
        }

        return list;
    }
}
