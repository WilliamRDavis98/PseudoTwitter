package com.team2.Assessment1.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
	  
	  //Foreign Key
	  @ManyToOne
	  private Long author;
	  
	// TODO: Determine if this needs to be a Date
	  private String posted;
	  
	  private boolean deleted;
	  
	  private String content;
	  
	  //Foreign Key
	  @ManyToOne
	  @JoinColumn(name="id")	//TODO: If it doesn't work, try "tweet_id"
	  private Long inReplyTo;
	  
	  //Foreign Key
	  @ManyToOne
	  @JoinColumn(name="id")	//TODO: Same ^
	  private Long repostOf;
	  
	  @ManyToMany(mappedBy = "taggedTweets")
	  private Set<Hashtag> tags;
	  
	  @ManyToMany(mappedBy = "likedTweets")
	  private Set<User> likedBy;
	  
	  @ManyToMany(mappedBy = "mentionedBy")
	  private Set<User> mentions;
}
