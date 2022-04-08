package com.cooksys.socialmedia.mappers;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.embeddables.CredentialsEmbeddable;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-27T19:45:47-0500",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.12 (Oracle Corporation)"
)
@Component
public class CredentialsMapperImpl implements CredentialsMapper {

    @Override
    public CredentialsEmbeddable dtoToEntity(CredentialsDto credentialsDto) {
        if ( credentialsDto == null ) {
            return null;
        }

        CredentialsEmbeddable credentialsEmbeddable = new CredentialsEmbeddable();

        credentialsEmbeddable.setUsername( credentialsDto.getUsername() );
        credentialsEmbeddable.setPassword( credentialsDto.getPassword() );

        return credentialsEmbeddable;
    }

    @Override
    public CredentialsDto entityToDto(CredentialsEmbeddable credentials) {
        if ( credentials == null ) {
            return null;
        }

        CredentialsDto credentialsDto = new CredentialsDto();

        credentialsDto.setUsername( credentials.getUsername() );
        credentialsDto.setPassword( credentials.getPassword() );

        return credentialsDto;
    }
}
