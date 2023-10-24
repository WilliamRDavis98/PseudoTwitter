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

	@Column(nullable = false)
	private String label;

	@CreationTimestamp
	@Column(nullable = false)
	private Timestamp firstUsed;

	@UpdateTimestamp
	private Timestamp lastUsed;

	@ManyToMany(mappedBy = "tags")
	private List<Tweet> taggedTweets;
}
