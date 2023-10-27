package com.team2.Assessment1.services.impl;

import org.springframework.stereotype.Service;

import com.team2.Assessment1.repositories.UserRepository;
import com.team2.Assessment1.services.ValidateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {

	private final UserRepository userRepository;

	/*************************************
	 * GET Methods
	 *************************************/
	@Override
	public Boolean validateUsernameAvailable(String username) {
		return !userRepository.existsByCredentialsUsernameAndDeletedFalse(username);
	}

	@Override
	public Boolean validateUsernameExists(String username) {

		return userRepository.existsByCredentialsUsername(username);
	}
}
