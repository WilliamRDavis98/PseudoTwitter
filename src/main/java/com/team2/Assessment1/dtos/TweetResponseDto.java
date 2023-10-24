package com.team2.Assessment1.dtos;

import java.sql.Timestamp;

import com.team2.Assessment1.entities.Tweet;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TweetResponseDto {
	private Long id;
	private UserResponseDto author;
	private Timestamp posted;
	private String content;		
	private TweetResponseDto inReplyTo;	
	private TweetResponseDto repostOf;	
}
