package com.team2.Assessment1.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.team2.Assessment1.dtos.TweetResponseDto;
import com.team2.Assessment1.dtos.UserRequestDto;
import com.team2.Assessment1.dtos.UserResponseDto;

public interface UserService {

	List<UserResponseDto> getAllUsers();

	UserResponseDto createUser(UserRequestDto userRequestDto);

//	ResponseEntity<List<TweetResponseDto>> getMentions(String username);
//
//	ResponseEntity<List<UserResponseDto>> getFollowedUsers(String username);
//
//	ResponseEntity<List<UserResponseDto>> getFollowers(String username);

}
