package com.team2.Assessment1.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Hashtag {

	@Id
	@GeneratedValue
	private Long id;

	private String label;

	@CreationTimestamp
	private Timestamp firstUsed;

	@UpdateTimestamp // anytime its reposted to the DB, it will update so you know when its used last
	private Timestamp lastUsed;

	@ManyToMany
	@JoinTable(name = "tweet_hashtags", joinColumns = @JoinColumn(name = "hashtag_id"), inverseJoinColumns = @JoinColumn(name = "tweet_id"))
	private List<Tweet> taggedTweets;
}
