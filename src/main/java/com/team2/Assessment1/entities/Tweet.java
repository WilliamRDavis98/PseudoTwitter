package com.team2.Assessment1.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	private Long id;

	// Foreign Key
	@ManyToOne
	@JoinColumn(name="user_id")
	private User author;

	@Column(nullable = false)
	private Timestamp posted;

	private boolean deleted;

	private String content;
	
	@OneToMany(mappedBy = "inReplyTo")
	private List<Tweet> replies;

	// Foreign Key
	@ManyToOne
	@JoinColumn(name = "replied_tweet_id")
	private Tweet inReplyTo;

	
	@OneToMany(mappedBy = "repostOf")
	private List<Tweet> reposts;
	
	// Foreign Key
	@ManyToOne
	@JoinColumn(name = "reposted_tweet_id")
	private Tweet repostOf;

	@ManyToMany(mappedBy = "taggedTweets")
	private List<Hashtag> tags;

	@ManyToMany(mappedBy = "likedTweets")
	private List<User> likedBy;

	@ManyToMany(mappedBy = "mentionedBy")
	private List<User> mentions;
}
