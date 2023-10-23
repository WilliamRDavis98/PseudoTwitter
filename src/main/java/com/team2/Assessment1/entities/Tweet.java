package com.team2.Assessment1.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

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

	private Timestamp posted;

	private boolean deleted;

	private String content;

	// Foreign Key
	@ManyToOne
	@JoinColumn(name = "tweet_id") // TODO: If it doesn't work, try "id"
	private Tweet inReplyTo;

	// Foreign Key
	@ManyToOne
	@JoinColumn(name = "tweet_id") // TODO: Same ^
	private Tweet repostOf;

	@ManyToMany(mappedBy = "taggedTweets")
	private List<Hashtag> tags;

	@ManyToMany(mappedBy = "likedTweets")
	private List<User> likedBy;

	@ManyToMany(mappedBy = "mentionedBy")
	private List<User> mentions;
}
