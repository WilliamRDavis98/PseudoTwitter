package com.team2.Assessment1.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.team2.Assessment1.dtos.UserRequestDto;
import com.team2.Assessment1.dtos.UserResponseDto;
import com.team2.Assessment1.entities.Credentials;
import com.team2.Assessment1.entities.Profile;
import com.team2.Assessment1.entities.User;
import com.team2.Assessment1.exceptions.BadRequestException;
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
	
	// Repository declarations
	private final UserRepository userRepository;
	private final TweetRepository tweetRepository;
	private final HashtagRepository hashtagRepository;

	
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
		
		if(userRepository.findByCredentialsUsername(userCredentials.getUsername()) != null && !user.isDeleted()) {
			throw new BadRequestException("Bad Request: Username is already taken");
		}
		
		if(user.isDeleted()) {
			user.setDeleted(false);
		}
		
		return userMapper.entityToDto(userRepository.saveAndFlush(user));
	}

}
