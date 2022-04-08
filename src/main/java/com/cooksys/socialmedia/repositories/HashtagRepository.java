package com.cooksys.socialmedia.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.entities.Tweet;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

	Optional<Hashtag> findTweetById(Long id);
	
	Optional<Hashtag> findUserById(Long id);
	
	Optional<Hashtag> findByLabel(String label);
	
	Optional<Hashtag> findHashtagByLabel(String label);
	
}
