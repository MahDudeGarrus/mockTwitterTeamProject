package com.cooksys.socialmedia.mappers;

import com.cooksys.socialmedia.dtos.ProfileDto;
import com.cooksys.socialmedia.embeddables.ProfileEmbeddable;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-27T19:45:47-0500",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.12 (Oracle Corporation)"
)
@Component
public class ProfileMapperImpl implements ProfileMapper {

    @Override
    public ProfileEmbeddable dtoToEntity(ProfileDto credentialsDto) {
        if ( credentialsDto == null ) {
            return null;
        }

        ProfileEmbeddable profileEmbeddable = new ProfileEmbeddable();

        profileEmbeddable.setFirstName( credentialsDto.getFirstName() );
        profileEmbeddable.setLastName( credentialsDto.getLastName() );
        profileEmbeddable.setEmail( credentialsDto.getEmail() );
        profileEmbeddable.setPhone( credentialsDto.getPhone() );

        return profileEmbeddable;
    }

    @Override
    public ProfileDto entityToDto(ProfileEmbeddable credentials) {
        if ( credentials == null ) {
            return null;
        }

        ProfileDto profileDto = new ProfileDto();

        profileDto.setFirstName( credentials.getFirstName() );
        profileDto.setLastName( credentials.getLastName() );
        profileDto.setEmail( credentials.getEmail() );
        profileDto.setPhone( credentials.getPhone() );

        return profileDto;
    }
}
