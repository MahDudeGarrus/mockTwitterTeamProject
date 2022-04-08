package com.cooksys.socialmedia.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Hashtag {
	
	@Id
	@GeneratedValue
	@Column(name = "hashtag_id", unique = true, nullable = false)
	private Long id;
	
	private String label;
	
	private Timestamp firstUsed;
	
	private Timestamp lastUsed;
	
	@ManyToMany(mappedBy = "hashtags")
	private List<Tweet> tweets;

}
