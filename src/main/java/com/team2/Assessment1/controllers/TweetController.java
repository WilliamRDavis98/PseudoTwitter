package com.team2.Assessment1.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team2.Assessment1.dtos.ContextDto;
import com.team2.Assessment1.dtos.CredentialsDto;
import com.team2.Assessment1.dtos.HashtagDto;
import com.team2.Assessment1.dtos.TweetRequestDto;
import com.team2.Assessment1.dtos.TweetResponseDto;
import com.team2.Assessment1.dtos.UserResponseDto;
import com.team2.Assessment1.repositories.HashtagRepository;
import com.team2.Assessment1.services.TweetService;

//Import Services

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {
	
	private final TweetService tweetService;

	@GetMapping
	public List<TweetResponseDto> getAllTweets() {
		return tweetService.getAllTweets();
	}
	
	@GetMapping("/{id}")
	public TweetResponseDto getTweet(@PathVariable Long id) {
		return tweetService.getTweet(id);
	}
	
	@PostMapping
	public TweetResponseDto createTweet(@RequestBody TweetRequestDto tweetRequestDto) {
		return tweetService.createTweet(tweetRequestDto);
	}
	
	@PostMapping("/{id}/like")
	public void likeTweet(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto) {
		tweetService.likeTweet(id, credentialsDto);
	}
	
	@DeleteMapping("/{id}")
	public TweetResponseDto deleteTweet(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto) {
		return tweetService.deleteTweet(id, credentialsDto);
	}

	@GetMapping("/{id}/tags")
	public List<HashtagDto> getTweetTags(@PathVariable Long id) {
		return tweetService.getTweetTags(id);
	}
	
	@GetMapping("/{id}/likes")
	public List<UserResponseDto> getTweetLikes(@PathVariable Long id) {
		return tweetService.getTweetLikes(id);
	}
	
	@GetMapping("/{id}/replies")
	public List<TweetResponseDto> getTweetReplies(@PathVariable Long id) {
		return tweetService.getTweetReplies(id);
	}
	
	@GetMapping("/{id}/context")
	public ContextDto getTweetContext(@PathVariable Long id) {
		return tweetService.getTweetContext(id);
	}
}
