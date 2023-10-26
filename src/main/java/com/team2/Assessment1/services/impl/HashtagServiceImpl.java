package com.team2.Assessment1.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.team2.Assessment1.dtos.HashtagDto;
import com.team2.Assessment1.dtos.TweetResponseDto;
import com.team2.Assessment1.entities.Hashtag;
import com.team2.Assessment1.exceptions.NotFoundException;
import com.team2.Assessment1.mappers.HashtagMapper;
import com.team2.Assessment1.mappers.TweetMapper;
import com.team2.Assessment1.repositories.HashtagRepository;
import com.team2.Assessment1.repositories.TweetRepository;
import com.team2.Assessment1.services.HashtagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {
	private final HashtagMapper hashtagMapper;
	private final HashtagRepository hashtagRepository;
	private final TweetMapper tweetMapper;
	private final TweetRepository tweetRepository;
	
	@Override
	public List<HashtagDto> getAllHashtags() {
		return hashtagMapper.entitiesToDtos(hashtagRepository.findAll());
	}

	@Override
	public List<TweetResponseDto> getTaggedTweets(String label) {
		return null;
	}
	
	public boolean doesTagExist(String label) {
		return hashtagRepository.findByLabel(label) != null;
	}

}
