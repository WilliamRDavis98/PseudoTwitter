package com.team2.Assessment1.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.team2.Assessment1.dtos.TweetResponseDto;
import com.team2.Assessment1.mappers.TweetMapper;
import com.team2.Assessment1.repositories.TweetRepository;
import com.team2.Assessment1.services.TweetService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService{
	
	// Mapper Declarations
	private final TweetMapper tweetMapper;
	
	// Repository Declarations
	private final TweetRepository tweetRepository;
	
	@Override
	public List<TweetResponseDto> getAllTweets() {		
		return tweetMapper.entitiesToDtos(tweetRepository.findAllByDeletedFalse());
	}

}
