package com.team2.Assessment1.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.team2.Assessment1.dtos.HashtagDto;
import com.team2.Assessment1.dtos.TweetResponseDto;
import com.team2.Assessment1.entities.Hashtag;
import com.team2.Assessment1.entities.Tweet;
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
	
	// Mapper Declarations
	private final HashtagMapper hashtagMapper;
	private final TweetMapper tweetMapper;
	
	// Repository Declarations
	private final HashtagRepository hashtagRepository;
	private final TweetRepository tweetRepository;

	
	/************************************
	 * GET Methods
	 ************************************/
	@Override
	public boolean doesTagExist(String label) {
		return hashtagRepository.findByLabel(label) != null;
	}

	@Override
	public List<HashtagDto> getAllHashtags() {
		return hashtagMapper.entitiesToDtos(hashtagRepository.findAll());
	}

	@Override
	public List<TweetResponseDto> getTaggedTweets(String label) {
		Hashtag searchedTag = hashtagRepository.findByLabel(label);
		Optional<Hashtag> checkTag = Optional.ofNullable(searchedTag);

		if (checkTag.isEmpty()) {
			throw new NotFoundException("Hashtag " + label + " not found");
		}

		List<Tweet> allTweets = tweetRepository.findAllByDeletedFalse();
		List<Tweet> taggedTweets = new ArrayList<>(); // Implement without new keyword?

		for (Tweet t : allTweets) {
			if (t.getTags().contains(searchedTag)) {
				taggedTweets.add(t);
			}
		}

		Collections.sort(taggedTweets, (y, x) -> x.getPosted().compareTo(y.getPosted()));
		return tweetMapper.entitiesToDtos(taggedTweets);
	}

}
