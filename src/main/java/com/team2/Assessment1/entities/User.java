package com.team2.Assessment1.entities;

import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name="UserTable")
public class User {

	  @Id
	  @GeneratedValue
	  private Long id;
	  
	  @Embedded
	  private Credentials credentials;
	  
	  @Embedded
	  private Profile profile;
	  
	  private Set<Tweet> tweets;
	  
	  @ManyToMany
	  @JoinTable(
			  name = "user_likes",
			  joinColumns = @JoinColumn(name="user_id"),
			  inverseJoinColumns = @JoinColumn(name="tweet_id"))
	  private Set<Tweet> likedTweets;
	  
	  @ManyToMany
	  @JoinTable(
			  name = "user_mentions",
			  joinColumns = @JoinColumn(name="user_id"),
			  inverseJoinColumns = @JoinColumn(name="tweet_id"))
	  private Set<Tweet> mentionedBy;
	  
	  @ManyToMany
	  @JoinTable(
			  name = "followers_following",
			  joinColumns = @JoinColumn(name="following_id"),
			  inverseJoinColumns = @JoinColumn(name="follower_id"))
	  private Set<User> following;
	  
	  @ManyToMany(mappedBy = "following")
	  private Set<User> followers;
}
