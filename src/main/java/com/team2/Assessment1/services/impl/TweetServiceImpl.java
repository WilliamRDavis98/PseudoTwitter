package com.team2.Assessment1.services.impl;

import java.util.List;
import java.util.Optional;

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
public class TweetServiceImpl implements TweetService {

	// Mapper Declarations
	private final TweetMapper tweetMapper;

	// Repository Declarations
	private final TweetRepository tweetRepository;
	private final UserRepository userRepository;

	@Override
	public TweetResponseDto getTweet(Long id) {
		Optional<Tweet> requestedTweet = tweetRepository.findById(id);

		if (requestedTweet.isEmpty()) {
			throw new NotFoundException("Tweet with id '" + id + "' not found");
		}

		if (requestedTweet.get().isDeleted()) {
			throw new NotFoundException("Tweet with id '" + id + "' has been deleted");
		}

		return tweetMapper.entityToDto(requestedTweet.get());
	}

	@Override
	public List<TweetResponseDto> getAllTweets() {
		return tweetMapper.entitiesToDtos(tweetRepository.findAllByDeletedFalse());
	}

	@Override
	public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {
		CredentialsDto userCredentials = tweetRequestDto.getCredentials();

		// Error Handling for BadRequestException
		if (tweetRequestDto.getContent() == null) {
			throw new BadRequestException("Bad Request: No Content");
		}

		if (userCredentials == null) {
			throw new BadRequestException("Bad Request: No Credentials");
		}

		if (userCredentials.getPassword() == null || userCredentials.getUsername() == null) {
			throw new BadRequestException("Bad Request: Missing username/password in Credentials");
		}

		Tweet newTweet = tweetMapper.dtoToEntity(tweetRequestDto);
		User user = userRepository.findByCredentialsUsername(userCredentials.getUsername()).get();

		// Check if User exists
		if (user == null || user.isDeleted()) {
			throw new NotFoundException("User not found or not active" + user);
		}

		newTweet.setAuthor(user);

		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(newTweet));
	}

	@Override
	public void likeTweet(Long id, CredentialsDto credentialsDto) {
		Tweet tweetToLike = tweetRepository.getById(id);
		User user = userRepository.findByCredentialsUsername(credentialsDto.getUsername()).get();

		if (tweetToLike == null || tweetToLike.isDeleted()) {
			throw new NotFoundException("Tweet with id '" + id + "' has been deleted or doesn't exist");
		}

		if (user == null || user.isDeleted()) {
			throw new NotFoundException("User matching given credentials not found");
		}

		user.getLikedTweets().add(tweetToLike);
		userRepository.saveAndFlush(user);
	}

	@Override
	public TweetResponseDto deleteTweet(Long id, CredentialsDto credentialsDto) {
		Optional<Tweet> findTweet = tweetRepository.findById(id);

		if (findTweet.isEmpty()) {
			throw new NotFoundException("Tweet with id '" + id + "' not found");
		}

		Tweet deletedTweet = findTweet.get();
		User user = userRepository.findByCredentialsUsername(credentialsDto.getUsername()).get();

		if (!deletedTweet.getAuthor().equals(user)) {
			throw new NotFoundException("User requesting deletion is not author of the tweet");
		}

		if (deletedTweet.isDeleted()) {
			throw new BadRequestException("Tweet is already deleted with id " + id);
		}

		deletedTweet.setDeleted(true);

		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(deletedTweet));
	}
}
