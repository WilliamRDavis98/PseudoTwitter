package com.team2.Assessment1.services;

public interface ValidateService {

	/*************************************
	 * GET Methods
	 *************************************/
	Boolean validateUsernameAvailable(String username);

	Boolean validateUsernameExists(String username);
}
