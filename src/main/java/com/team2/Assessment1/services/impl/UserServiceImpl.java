package com.team2.Assessment1.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.team2.Assessment1.dtos.TweetResponseDto;
import com.team2.Assessment1.dtos.UserResponseDto;
import com.team2.Assessment1.entities.Tweet;
import com.team2.Assessment1.entities.User;
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
	
	// Repository declarations
	private final UserRepository userRepository;
	private final TweetRepository tweetRepository;
	private final HashtagRepository hashtagRepository;

	// Mapper declarations
	private final UserMapper userMapper;
	private final TweetMapper tweetMapper;
	private final HashtagMapper hashtagMapper;
	@Override
	public List<UserResponseDto> getAllUsers() {
		// TODO Auto-generated method stub
		return userMapper.entitiesToDtos(userRepository.findAllByDeletedFalse());
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
