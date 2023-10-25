package com.team2.Assessment1.services;

import java.util.List;

import com.team2.Assessment1.dtos.CredentialsDto;
import com.team2.Assessment1.dtos.TweetRequestDto;
import com.team2.Assessment1.dtos.TweetResponseDto;

public interface TweetService {

	List<TweetResponseDto> getAllTweets();

	TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

	TweetResponseDto getTweet(Long id);

	void likeTweet(Long id, CredentialsDto credentialsDto);

	TweetResponseDto deleteTweet(Long id, CredentialsDto credentialsDto);
}
