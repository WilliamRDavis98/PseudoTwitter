package com.team2.Assessment1.dtos;

import java.sql.Timestamp;

import com.team2.Assessment1.entities.Tweet;
import com.team2.Assessment1.entities.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TweetResponseDto {
	private Long id;
	private User author;
	private Timestamp posted;
	private String content;		// TODO: Optional fields, how do we implement that, if necessary
	private Tweet inReplyTo;	// TODO: Optional ...
	private Tweet repostOf;		// TODO: Optional ...
}
