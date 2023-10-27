package com.team2.Assessment1.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.team2.Assessment1.services.HashtagService;
import com.team2.Assessment1.services.ValidateService;

//Import Services

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/validate")
public class ValidateController {

	private final ValidateService validateService;
	private final HashtagService hashtagService;

	/*************************************
	 * GET Mappings
	 *************************************/
	@GetMapping("/tag/exists/{label}")
	@ResponseStatus(HttpStatus.OK)
	public boolean doesTagExist(@PathVariable String label) {
		return hashtagService.doesTagExist(label);
	}
	
	@GetMapping("/username/available/@{username}")
	@ResponseStatus(HttpStatus.OK)
	public Boolean validateUsernameAvailable(@PathVariable String username) {
		return validateService.validateUsernameAvailable(username);

	}
	
	@GetMapping("/username/exists/@{username}")
	@ResponseStatus(HttpStatus.OK)
	public Boolean validateUsernameExists(@PathVariable String username) {
		return validateService.validateUsernameExists(username);
	}

}
