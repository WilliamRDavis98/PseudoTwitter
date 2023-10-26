package com.team2.Assessment1.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Tweet {

	@Id
	@GeneratedValue
	private Long id;

	// Foreign Key
	@ManyToOne
	private User author;

	@CreationTimestamp
	@Column(nullable = false)
	private Timestamp posted;

	private boolean deleted = false;

	private String content;
	
	@OneToMany(mappedBy = "inReplyTo")
	private List<Tweet> replies;

	@ManyToOne
	private Tweet inReplyTo;

	
	@OneToMany(mappedBy = "repostOf")
	private List<Tweet> reposts;
	
	@ManyToOne
	private Tweet repostOf;

	@ManyToMany
	@JoinTable(
			name = "tweet_hashtags", 
			joinColumns = @JoinColumn(name = "tweet_id"), 
			inverseJoinColumns = @JoinColumn(name = "hashtag_id")
			)
	private List<Hashtag> tags;

	@ManyToMany(mappedBy = "likedTweets")
	private List<User> likedBy;

	@ManyToMany
	@JoinTable(name = "user_mentions", joinColumns = @JoinColumn(name = "tweet_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> mentions;
}
