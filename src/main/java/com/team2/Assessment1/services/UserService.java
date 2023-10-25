package com.team2.Assessment1.services;

import java.util.List;

import com.team2.Assessment1.dtos.CredentialsDto;
import com.team2.Assessment1.dtos.TweetResponseDto;
import com.team2.Assessment1.dtos.UserRequestDto;
import com.team2.Assessment1.dtos.UserResponseDto;

public interface UserService {

	List<UserResponseDto> getAllUsers();

	UserResponseDto createUser(UserRequestDto userRequestDto);

	UserResponseDto getUser(String username);

	UserResponseDto deleteUser(String username, CredentialsDto credentialsDto);

	List<TweetResponseDto> getUserTweets(String username);

	void followUser(String username, CredentialsDto credentialsDto);

//
//	List<TweetResponseDto> getMentions(String username);
//
//	List<UserResponseDto> getFollowedUsers(String username);
//
//	List<UserResponseDto> getFollowers(String username);

	void unfollowUser(String username, CredentialsDto credentialsDto);
}
