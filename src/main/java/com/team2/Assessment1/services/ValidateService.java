package com.team2.Assessment1.services;

public interface ValidateService {

	/*************************************
	 * GET Methods
	 *************************************/
	Boolean validateUsernameExists(String username);

	Boolean validateUsernameAvailable(String username);

}
