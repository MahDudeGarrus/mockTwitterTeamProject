package com.cooksys.socialmedia.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.socialmedia.embeddables.CredentialsEmbeddable;
import com.cooksys.socialmedia.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByIdAndDeletedFalse(Long id);
	
	List<User> findAllByDeletedFalse();

	Optional<User> findByCredentialsAndDeletedFalse(CredentialsEmbeddable credentials);
	
	Optional<User> findByCredentialsUsername(String username);
	
	Optional<User> findByCredentialsUsernameAndDeletedFalse(String username);
	
	Optional<User> findById(Long id);
	
}