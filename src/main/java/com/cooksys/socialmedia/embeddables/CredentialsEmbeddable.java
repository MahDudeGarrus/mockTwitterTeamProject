package com.cooksys.socialmedia.embeddables;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
public class CredentialsEmbeddable {
	

	@Column(nullable=false, unique = true)
	private String username;
	
	@Column(nullable=false)
	private String password;
	
	@Override
	public int hashCode() {
		return Objects.hash(password, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CredentialsEmbeddable other = (CredentialsEmbeddable) obj;
		return Objects.equals(password, other.password) && Objects.equals(username, other.username);
	}
	
}
