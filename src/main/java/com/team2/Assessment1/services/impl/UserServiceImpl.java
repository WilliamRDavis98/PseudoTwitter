package com.team2.Assessment1.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.team2.Assessment1.dtos.CredentialsDto;
import com.team2.Assessment1.dtos.TweetResponseDto;
import com.team2.Assessment1.dtos.UserRequestDto;
import com.team2.Assessment1.dtos.UserResponseDto;
import com.team2.Assessment1.entities.Credentials;
import com.team2.Assessment1.entities.Profile;
import com.team2.Assessment1.entities.User;
import com.team2.Assessment1.exceptions.BadRequestException;
import com.team2.Assessment1.exceptions.NotAuthorizedException;
import com.team2.Assessment1.exceptions.NotFoundException;
import com.team2.Assessment1.mappers.CredentialsMapper;
import com.team2.Assessment1.mappers.HashtagMapper;
import com.team2.Assessment1.mappers.TweetMapper;
import com.team2.Assessment1.mappers.UserMapper;
import com.team2.Assessment1.repositories.HashtagRepository;
import com.team2.Assessment1.repositories.TweetRepository;
import com.team2.Assessment1.repositories.UserRepository;
import com.team2.Assessment1.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	// Mapper declarations
	private final UserMapper userMapper;
	private final TweetMapper tweetMapper;
	private final HashtagMapper hashtagMapper;
	private final CredentialsMapper credentialsMapper;

	
	// Repository declarations
	private final UserRepository userRepository;
	private final TweetRepository tweetRepository;
	private final HashtagRepository hashtagRepository;

	@Override
	public UserResponseDto getUser(String username) {
		Optional<User> userToReturn = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
		if(userToReturn.isEmpty()) {
			throw new NotFoundException("No user with username: " + username);
		}
		return userMapper.entityToDto(userToReturn.get());
	}

	@Override
	public UserResponseDto deleteUser(String username, CredentialsDto credentialsDto) {
		Optional<User> userToDelete = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
		if (userToDelete.isEmpty()) {
			throw new NotFoundException("No user with username: " + username);
		}
		Credentials submittedCredentials = credentialsMapper.dtoToEntity(credentialsDto);
		if (!userToDelete.get().getCredentials().getPassword().equals(submittedCredentials.getPassword())) {
			throw new NotAuthorizedException("Incorrect Password");
		}
		
		userToDelete.get().setDeleted(true);
		userRepository.saveAndFlush(userToDelete.get());
		return userMapper.entityToDto(userToDelete.get());
		
	}

	@Override
	public List<TweetResponseDto> getUserTweets(String username) {
		Optional<User> userToReturn = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
		if(userToReturn.isEmpty()) {
			throw new NotFoundException("No user with username: " + username);
		}
		return tweetMapper.entitiesToDtos(userToReturn.get().getTweets());
	}
	
	
	
	
	@Override
	public List<UserResponseDto> getAllUsers() {
		// TODO Auto-generated method stub
		return userMapper.entitiesToDtos(userRepository.findAllByDeletedFalse());
	}
	
	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) {
		User user = userMapper.dtoToEntity(userRequestDto);
		
		Credentials userCredentials = user.getCredentials();
		Profile userProfile = user.getProfile();

		
		// Error Handling for BadRequestException	
		if(userProfile.getEmail() == null) {
			throw new BadRequestException("Bad Request: No email in Profile");
		}
		
		if(userCredentials.getPassword() == null || userCredentials.getUsername() == null) {
			throw new BadRequestException("Bad Request: Missing username/password in Credentials");
		}
		
		if(userRepository.findByCredentialsUsernameAndDeletedFalse(userCredentials.getUsername()).isEmpty() && !user.isDeleted()) {
			throw new BadRequestException("Bad Request: Username is already taken");
		}
		
		if(user.isDeleted()) {
			user.setDeleted(false);
		}
		
		return userMapper.entityToDto(userRepository.saveAndFlush(user));
	}
	

	
//	@Override
//	public ResponseEntity<List<TweetResponseDto>> getMentions(String username) {
//		return null;
//		return new ResponseEntity<>(tweetMapper.entitiesToDtos(tweetRepository.findMentionsByUsername(username)), HttpStatus.OK);
//	}
//
//	@Override
//	public ResponseEntity<List<UserResponseDto>> getFollowedUsers(String username) {
//		User user = userRepository.findByUsername(username);
//		if(user == null) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//		
//		List<User> followedUsers = user.getFollowing().stream().filter(User::isActive).collect(Collectors.toList());
//		
//		List<UserResponseDto> dtos = userMapper.entitiesToDtos(followedUsers);
//				
//		
//		return new ResponseEntity<>(dtos, HttpStatus.OK);
//		
//		return null;
//	}
//
//	@Override
//	public ResponseEntity<List<UserResponseDto>> getFollowers(String username) {
//		User user = userRepository.findByUsername(username);
//		if(user == null) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//		
//		List<User> followers = user.getFollowers().stream().filter(User::isActive).collect(Collectors.toList());
//		
//		List<UserResponseDto> dtos = userMapper.entitiesToDtos(followers);
//				
//		
//		return new ResponseEntity<>(dtos, HttpStatus.OK);
//		return null;
//	}

}
