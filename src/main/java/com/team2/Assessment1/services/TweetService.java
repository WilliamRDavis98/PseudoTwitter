package com.team2.Assessment1.services;

import java.util.List;

import com.team2.Assessment1.dtos.TweetRequestDto;
import com.team2.Assessment1.dtos.TweetResponseDto;

public interface TweetService {

	List<TweetResponseDto> getAllTweets();

	TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

	TweetResponseDto getTweet(Long id);
}
