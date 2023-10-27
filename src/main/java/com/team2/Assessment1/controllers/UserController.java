package com.team2.Assessment1.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.team2.Assessment1.dtos.CredentialsDto;
import com.team2.Assessment1.dtos.TweetResponseDto;
import com.team2.Assessment1.dtos.UserRequestDto;
import com.team2.Assessment1.dtos.UserResponseDto;
import com.team2.Assessment1.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	/************************************
	 * GET Mappings
	 ************************************/
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<UserResponseDto> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@GetMapping("/@{username}")
	@ResponseStatus(HttpStatus.OK)
	public UserResponseDto getUser(@PathVariable String username) {
		return userService.getUser(username);
	}
	
	@GetMapping("/@{username}/feed")
	@ResponseStatus(HttpStatus.OK)
	public List<TweetResponseDto> getFeed(@PathVariable String username){
		return userService.getFeed(username);
	}
	
	@GetMapping("/@{username}/followers")
	@ResponseStatus(HttpStatus.OK)
	public List<UserResponseDto> getFollowers(@PathVariable String username) {
		return userService.getFollowers(username);
	}
	
	@GetMapping("/@{username}/following")
	@ResponseStatus(HttpStatus.OK)
	public List<UserResponseDto> getFollowedUsers(@PathVariable String username) {
		return userService.getFollowedUsers(username);
	}

	@GetMapping("/@{username}/mentions")
	@ResponseStatus(HttpStatus.OK)
	public List<TweetResponseDto> getMentions(@PathVariable String username) {
		return userService.getMentions(username);
	}

	@GetMapping("/@{username}/tweets")
	@ResponseStatus(HttpStatus.OK)
	public List<TweetResponseDto> getUserTweets(@PathVariable String username) {
		return userService.getUserTweets(username);
	}	

	
	/************************************
	 * POST Mappings
	 ************************************/
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto) {
		return userService.createUser(userRequestDto);
	}
	
	@PatchMapping("/@{username}")
	@ResponseStatus(HttpStatus.OK)
	public UserResponseDto patchUser(@PathVariable String username, @RequestBody UserRequestDto userRequestDto) {
		return userService.patchUser(username, userRequestDto);
	}

	@PostMapping("/@{username}/follow")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void followUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto) {
		userService.followUser(username, credentialsDto);
	}

	@PostMapping("/@{username}/unfollow")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void unfollowUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto) {
		userService.unfollowUser(username, credentialsDto);
	}
	

	/************************************
	 * DELETE Mappings
	 ************************************/
	@DeleteMapping("/@{username}")
	@ResponseStatus(HttpStatus.OK)
	public UserResponseDto deleteUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto) {
		return userService.deleteUser(username, credentialsDto);
	}
}
