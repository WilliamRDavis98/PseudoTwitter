package com.team2.Assessment1.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.team2.Assessment1.dtos.CredentialsDto;
import com.team2.Assessment1.dtos.TweetRequestDto;
import com.team2.Assessment1.dtos.TweetResponseDto;
import com.team2.Assessment1.entities.Tweet;
import com.team2.Assessment1.entities.User;
import com.team2.Assessment1.exceptions.BadRequestException;
import com.team2.Assessment1.exceptions.NotFoundException;
import com.team2.Assessment1.mappers.TweetMapper;
import com.team2.Assessment1.repositories.TweetRepository;
import com.team2.Assessment1.repositories.UserRepository;
import com.team2.Assessment1.services.TweetService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService{
	
	// Mapper Declarations
	private final TweetMapper tweetMapper;
	
	// Repository Declarations
	private final TweetRepository tweetRepository;
	private final UserRepository userRepository;
	
	@Override
	public List<TweetResponseDto> getAllTweets() {		
		return tweetMapper.entitiesToDtos(tweetRepository.findAllByDeletedFalse());
	}

	@Override
	public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {	
		CredentialsDto userCredentials = tweetRequestDto.getCredentials();
		
		// Error Handling for BadRequestException
		if(tweetRequestDto.getContent() == null) {
			throw new BadRequestException("Bad Request: No Content");
		}
		
		if(userCredentials == null ) {
			throw new BadRequestException("Bad Request: No Credentials");
		}
		
		if(userCredentials.getPassword() == null || userCredentials.getUsername() == null) {
			throw new BadRequestException("Bad Request: Missing username/password in Credentials");
		}
		
		
		Tweet newTweet = tweetMapper.dtoToEntity(tweetRequestDto);
		User user = userRepository.findByCredentialsUsername(userCredentials.getUsername());
		
		// Check if User exists
		if(user == null || user.isDeleted()) {
			throw new NotFoundException("User not found or not active" + user);
		}
		
		newTweet.setAuthor(user);
		
		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(newTweet));
	}

}
