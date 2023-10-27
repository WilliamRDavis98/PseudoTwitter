package com.team2.Assessment1.services;

import java.util.List;

import com.team2.Assessment1.dtos.HashtagDto;
import com.team2.Assessment1.dtos.TweetResponseDto;

public interface HashtagService {
	
	boolean doesTagExist(String label);

	List<HashtagDto> getAllHashtags();

	List<TweetResponseDto> getTaggedTweets(String label);

}
