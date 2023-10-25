package com.team2.Assessment1.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team2.Assessment1.dtos.TweetRequestDto;
import com.team2.Assessment1.dtos.TweetResponseDto;
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

}
