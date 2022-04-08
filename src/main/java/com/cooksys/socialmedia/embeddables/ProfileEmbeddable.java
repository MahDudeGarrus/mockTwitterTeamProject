package com.cooksys.socialmedia.embeddables;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
public class ProfileEmbeddable {
		
	private String firstName;
		
	private String lastName;
	
	@Column(nullable=false)
	private String email;
	
	private String phone;

}
