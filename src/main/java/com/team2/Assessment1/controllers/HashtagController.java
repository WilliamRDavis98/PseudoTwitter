package com.team2.Assessment1.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team2.Assessment1.dtos.HashtagDto;
import com.team2.Assessment1.dtos.TweetResponseDto;
import com.team2.Assessment1.services.HashtagService;

//Import Services

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class HashtagController {
	private final HashtagService hashtagService;

	@GetMapping
	public List<HashtagDto> getAllHashtags() {
		return hashtagService.getAllHashtags();
	}


	  @GetMapping("/{label}") 
	  public List<TweetResponseDto> getTaggedTweets(@PathVariable String label){
		  return hashtagService.getTaggedTweets(label);
	  }
	
	 
	  
	  
	  
	  
	  
}
