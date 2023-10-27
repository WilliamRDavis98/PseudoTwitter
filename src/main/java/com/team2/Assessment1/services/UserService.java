package com.team2.Assessment1.services;

import java.util.List;

import com.team2.Assessment1.dtos.CredentialsDto;
import com.team2.Assessment1.dtos.TweetResponseDto;
import com.team2.Assessment1.dtos.UserRequestDto;
import com.team2.Assessment1.dtos.UserResponseDto;

public interface UserService {
	
	/************************************
	 * GET Methods
	 ************************************/
	List<UserResponseDto> getAllUsers();
	
	UserResponseDto getUser(String username);
	
	List<UserResponseDto> getFollowedUsers(String username);
	
	List<UserResponseDto> getFollowers(String username);
	
	List<TweetResponseDto> getMentions(String username);
	
	List<TweetResponseDto> getUserTweets(String username);
	
	/************************************
	 * POST Methods
	 ************************************/
	UserResponseDto createUser(UserRequestDto userRequestDto);
	
	void followUser(String username, CredentialsDto credentialsDto);
	
	void unfollowUser(String username, CredentialsDto credentialsDto);
	
	/************************************
	 * DELETE Methods
	 ************************************/
	UserResponseDto deleteUser(String username, CredentialsDto credentialsDto);

	List<TweetResponseDto> getFeed(String username);

	UserResponseDto patchUser(String username, UserRequestDto userRequestDto);
}
