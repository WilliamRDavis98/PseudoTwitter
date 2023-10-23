package com.team2.Assessment1.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

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
	  
	  // TODO: Determine if this needs to be a Date
	  private String firstUsed;
	  
	// TODO: Determine if this needs to be a Date
	  private String lastUsed;
	  
	  @ManyToMany
	  @JoinTable(
			  name = "tweet_hashtags",
			  joinColumns = @JoinColumn(name="hashtag_id"),
			  inverseJoinColumns = @JoinColumn(name="tweet_id"))
	  private Set<Tweet> taggedTweets;
}
