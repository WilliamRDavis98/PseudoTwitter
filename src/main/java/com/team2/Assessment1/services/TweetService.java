package com.team2.Assessment1.services;

import java.util.List;

import com.team2.Assessment1.dtos.ContextDto;
import com.team2.Assessment1.dtos.CredentialsDto;
import com.team2.Assessment1.dtos.HashtagDto;
import com.team2.Assessment1.dtos.TweetRequestDto;
import com.team2.Assessment1.dtos.TweetResponseDto;
import com.team2.Assessment1.dtos.UserResponseDto;

public interface TweetService {

	List<TweetResponseDto> getAllTweets();

	TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

	TweetResponseDto getTweet(Long id);

	void likeTweet(Long id, CredentialsDto credentialsDto);

	TweetResponseDto deleteTweet(Long id, CredentialsDto credentialsDto);

	List<HashtagDto> getTweetTags(Long id);

	List<UserResponseDto> getTweetLikes(Long id);

	List<TweetResponseDto> getTweetReplies(Long id);

	ContextDto getTweetContext(Long id);
}
