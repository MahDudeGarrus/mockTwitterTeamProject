package com.cooksys.socialmedia.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Tweet {

	@Id
	@GeneratedValue
	@Column(name = "tweet_id", unique = true, nullable = false)
	private Long id;
	

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User author;
	
	private Timestamp posted;
	
	private boolean deleted;
	
	private String content;

	@ManyToOne
	@JoinColumn(name = "reply")
	private Tweet inReplyTo;
	
	@OneToMany(mappedBy = "inReplyTo", cascade = CascadeType.PERSIST)
	private List<Tweet> replies;
	
	
	@ManyToOne
	@JoinColumn(name = "repost")
	private Tweet repostOf;
	
	@OneToMany(mappedBy = "repostOf", cascade = CascadeType.ALL)
	private List<Tweet> reposts;
	
	
	@ManyToMany(mappedBy = "likedTweets", cascade = CascadeType.ALL)
	private List<User> likes;
	
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "Tweet_Hashtag")
	private List<Hashtag> hashtags;
	
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "UsersMentioned_Mentions")
	private List<User> usersMentioned;
	
	

	
}
