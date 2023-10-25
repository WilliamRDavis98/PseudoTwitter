package com.team2.Assessment1.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team2.Assessment1.dtos.CredentialsDto;
import com.team2.Assessment1.dtos.TweetResponseDto;
import com.team2.Assessment1.dtos.UserRequestDto;
import com.team2.Assessment1.dtos.UserResponseDto;
import com.team2.Assessment1.services.UserService;

//Import Services

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;
	
	@GetMapping
	public List<UserResponseDto> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@PostMapping
	public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto) {
		return userService.createUser(userRequestDto);
	}
	
	
	@GetMapping("/@{username}/mentions")
	public ResponseEntity<List<TweetResponseDto>> getMentions(@PathVariable String username) {
		return null;
//		return userService.getMentions(username);
	}
	
	@GetMapping("/@{username}/following")
	public ResponseEntity<List<UserResponseDto>> getFollowedUsers(@PathVariable String username) {
		return null;
//		return userService.getFollowedUsers(username);
	}
	
	@GetMapping("/@{username}/followers")
	public ResponseEntity<List<UserResponseDto>> getFollowers(@PathVariable String username) {
		return null;
//		return userService.getFollowers(username);
	}

	@GetMapping("/@{username}")
	public UserResponseDto getUser(@PathVariable String username){
		return userService.getUser(username);
	}
	
	@DeleteMapping("/@{username}")
	public UserResponseDto deleteUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto){
		return userService.deleteUser(username, credentialsDto);
	} 
	
}
