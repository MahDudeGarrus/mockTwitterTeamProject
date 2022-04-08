package com.cooksys.socialmedia.entities;

import java.sql.Timestamp;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cooksys.socialmedia.embeddables.CredentialsEmbeddable;
import com.cooksys.socialmedia.embeddables.ProfileEmbeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "user_table")
@Entity
@NoArgsConstructor
@Data
public class User {

	@Id
	@GeneratedValue
	@Column(name = "user_id", unique = true, nullable = false)
	private Long id;
	
	@Embedded
	private CredentialsEmbeddable credentials;
	
	private Timestamp joined;
	
	private boolean deleted;
	
	@Embedded
	private ProfileEmbeddable profile;
	
	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
	private List<Tweet> tweets;	
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "LikedTweets_LikesFromUsers") 
	private List<Tweet> likedTweets;
	
	@ManyToMany(mappedBy = "following")
	private List<User> followers;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "UsersFollowers_UsersFollowing")
	private List<User> following;
	
	@ManyToMany(mappedBy = "usersMentioned")
	private List<Tweet> mentions;

}
