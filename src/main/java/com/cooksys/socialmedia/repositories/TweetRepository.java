package com.cooksys.socialmedia.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.socialmedia.dtos.HashtagResponseDto;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

	Optional<Tweet> findByIdAndDeletedFalse(Long id);
	
	List<Tweet> findAllByDeletedFalse();
	
	List<Tweet> findByOrderByPostedDesc();
  
	Optional<Tweet> findByInReplyTo(Tweet tweet);

	List<Tweet> findAll();
  
	List<Tweet> findAllByDeletedFalseOrderByPostedAsc();
}
