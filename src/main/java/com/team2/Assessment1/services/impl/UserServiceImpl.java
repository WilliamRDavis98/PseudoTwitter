package com.team2.Assessment1.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.team2.Assessment1.dtos.CredentialsDto;
import com.team2.Assessment1.dtos.TweetResponseDto;
import com.team2.Assessment1.dtos.UserRequestDto;
import com.team2.Assessment1.dtos.UserResponseDto;
import com.team2.Assessment1.entities.Credentials;
import com.team2.Assessment1.entities.Profile;
import com.team2.Assessment1.entities.Tweet;
import com.team2.Assessment1.entities.User;
import com.team2.Assessment1.exceptions.BadRequestException;
import com.team2.Assessment1.exceptions.NotAuthorizedException;
import com.team2.Assessment1.exceptions.NotFoundException;
import com.team2.Assessment1.mappers.CredentialsMapper;
import com.team2.Assessment1.mappers.HashtagMapper;
import com.team2.Assessment1.mappers.TweetMapper;
import com.team2.Assessment1.mappers.UserMapper;
import com.team2.Assessment1.repositories.HashtagRepository;
import com.team2.Assessment1.repositories.TweetRepository;
import com.team2.Assessment1.repositories.UserRepository;
import com.team2.Assessment1.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	// Mapper declarations
	private final UserMapper userMapper;
	private final TweetMapper tweetMapper;
	private final HashtagMapper hashtagMapper;
	private final CredentialsMapper credentialsMapper;

	// Repository declarations
	private final UserRepository userRepository;
	private final TweetRepository tweetRepository;
	private final HashtagRepository hashtagRepository;
	
	/************************************
	 * GET Methods
	 ************************************/
	@Override
	public List<UserResponseDto> getAllUsers() {
		// TODO Auto-generated method stub
		return userMapper.entitiesToDtos(userRepository.findAllByDeletedFalse());
	}
	
	@Override
	public UserResponseDto getUser(String username) {
		Optional<User> userToReturn = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
		if (userToReturn.isEmpty()) {
			throw new NotFoundException("No user with username: " + username);
		}
		return userMapper.entityToDto(userToReturn.get());
	}
	
	@Override
	public List<UserResponseDto> getFollowedUsers(String username) {
		Optional<User> findUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);

		if (findUser.isEmpty() || findUser.get().isDeleted()) {
			throw new BadRequestException("User not found or is deleted");
		}

		User user = findUser.get();

		List<User> followedUsers = user.getFollowing();

		List<UserResponseDto> dtos = userMapper.entitiesToDtos(followedUsers);

		return dtos;
	}

	@Override
	public List<UserResponseDto> getFollowers(String username) {
		Optional<User> findUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);

		if (findUser.isEmpty() || findUser.get().isDeleted()) {
			throw new BadRequestException("User not found or is deleted");
		}

		User user = findUser.get();

		List<User> followers = user.getFollowers();

		List<UserResponseDto> dtos = userMapper.entitiesToDtos(followers);

		return dtos;
	}
	
	public List<TweetResponseDto> getMentions(String username) {
		Optional<User> findUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);

		if (findUser.isEmpty() || findUser.get().isDeleted()) {
			throw new BadRequestException("User not found or is deleted");
		}

		return tweetMapper.entitiesToDtos(findUser.get().getMentionedBy());
	}
	
	@Override
	public List<TweetResponseDto> getUserTweets(String username) {
		Optional<User> userToReturn = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
		if (userToReturn.isEmpty()) {
			throw new NotFoundException("No user with username: " + username);
		}
		return tweetMapper.entitiesToDtos(userToReturn.get().getTweets());
	}
	
	/************************************
	 * POST Methods
	 ************************************/
	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) {
		// Error Handling for BadRequestException
		if(userRequestDto.getCredentials() == null && userRequestDto.getProfile() == null) {
			throw new BadRequestException("Bad Request: Request Body is empty");
		} else if(userRequestDto.getCredentials() == null) {
			throw new BadRequestException("Bad Request: No User Credentials");
		} else if(userRequestDto.getProfile() == null) {
			throw new BadRequestException("Bad Request: No User Profile");
		}
		
		User user = userMapper.dtoToEntity(userRequestDto);
		Credentials userCredentials = user.getCredentials();
		
		if (user.getProfile().getEmail() == null) {
			throw new BadRequestException("Bad Request: No email in Profile");
		}

		if (userCredentials.getPassword() == null || userCredentials.getUsername() == null) {
			throw new BadRequestException("Bad Request: Missing username/password in Credentials");
		}

		if (!userRepository.findByCredentialsUsernameAndDeletedFalse(userCredentials.getUsername()).isEmpty()
				&& !user.isDeleted()) {
			throw new BadRequestException("Bad Request: Username is already taken");
		}

		Optional<User> findUser = userRepository.findByCredentialsUsername(userCredentials.getUsername());
		
		if(!findUser.isEmpty()) {
			if (findUser.get().isDeleted()) {
				user = findUser.get();
				user.setDeleted(false);
			}
		}

		return userMapper.entityToDto(userRepository.saveAndFlush(user));
	}

	@Override
	public void followUser(String username, CredentialsDto credentialsDto) {
		Credentials credentialsFollowingOther = credentialsMapper.dtoToEntity(credentialsDto);
		Optional<User> userFollowingOther = userRepository.findByCredentialsAndDeletedFalse(credentialsFollowingOther);
		Optional<User> userBeingFollowed = userRepository.findByCredentialsUsernameAndDeletedFalse(username);

		// Empty Username or Password
		if (credentialsFollowingOther.getUsername() == null || credentialsFollowingOther.getPassword() == null
				|| credentialsFollowingOther.getUsername().isEmpty()
				|| credentialsFollowingOther.getPassword().isEmpty()) {
			throw new BadRequestException("Bad Request: Missing username/password in Credentials");
		}

		// Credentails provided do not match an active user in database throw an error
		if (userFollowingOther.isEmpty()) {
			throw new NotFoundException("No user found with the username: " + credentialsFollowingOther.getUsername()
					+ ", with the password: " + credentialsFollowingOther.getPassword());

			// No followable user exists throw an error (deleted or never created)
		} else if (userBeingFollowed.isEmpty()) {

			throw new NotFoundException("No followable user found by the username: " + username);

		}

		// If User is Already following throw an error
		if (userFollowingOther.get().getFollowing().contains(userBeingFollowed.get())
				|| userBeingFollowed.get().getFollowers().contains(userFollowingOther.get())) {
			throw new BadRequestException("Bad Request: User is already followed");
		}

		userFollowingOther.get().getFollowing().add(userBeingFollowed.get());
		userBeingFollowed.get().getFollowers().add(userFollowingOther.get());

		userRepository.saveAndFlush(userBeingFollowed.get());
		userRepository.saveAndFlush(userFollowingOther.get());

	}

	@Override
	public void unfollowUser(String username, CredentialsDto credentialsDto) {
		Credentials credentialsUnfollowingOther = credentialsMapper.dtoToEntity(credentialsDto);
		Optional<User> userUnfollowingOther = userRepository
				.findByCredentialsAndDeletedFalse(credentialsUnfollowingOther);
		Optional<User> userBeingUnfollowed = userRepository.findByCredentialsUsernameAndDeletedFalse(username);

		// Empty Username or Password
		if (credentialsUnfollowingOther.getUsername() == null || credentialsUnfollowingOther.getPassword() == null
				|| credentialsUnfollowingOther.getUsername().isEmpty()
				|| credentialsUnfollowingOther.getPassword().isEmpty()) {
			throw new BadRequestException("Bad Request: Missing username/password in Credentials");
		}

		// Credentails provided do not match an active user in database throw an error
		if (userUnfollowingOther.isEmpty()) {
			throw new NotFoundException("No user found with the username: " + credentialsUnfollowingOther.getUsername()
					+ ", with the password: " + credentialsUnfollowingOther.getPassword());

			// No unfollowable user exists throw an error (deleted or never created)
		} else if (userBeingUnfollowed.isEmpty()) {

			throw new NotFoundException("Not following any user by the username: " + username);

		}

		// If User is Already following throw an error
		if (!userUnfollowingOther.get().getFollowing().contains(userBeingUnfollowed.get())
				|| !userBeingUnfollowed.get().getFollowers().contains(userUnfollowingOther.get())) {
			throw new BadRequestException("Bad Request: User is not being followed");
		}

		userUnfollowingOther.get().getFollowing().remove(userBeingUnfollowed.get());
		userBeingUnfollowed.get().getFollowers().remove(userUnfollowingOther.get());

		userRepository.saveAndFlush(userBeingUnfollowed.get());
		userRepository.saveAndFlush(userUnfollowingOther.get());

	}
	
	/************************************
	 * DELETE Methods
	 ************************************/
	@Override
	public UserResponseDto deleteUser(String username, CredentialsDto credentialsDto) {
		Optional<User> userToDelete = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
		if (userToDelete.isEmpty()) {
			throw new NotFoundException("No user with username: " + username);
		}
		Credentials submittedCredentials = credentialsMapper.dtoToEntity(credentialsDto);
		if (!userToDelete.get().getCredentials().getPassword().equals(submittedCredentials.getPassword())) {
			throw new NotAuthorizedException("Incorrect Password");
		}

		userToDelete.get().setDeleted(true);
		userRepository.saveAndFlush(userToDelete.get());
		return userMapper.entityToDto(userToDelete.get());

	}
	
	/************************************
	 * Helper Methods
	 ************************************/

	@Override
	public List<TweetResponseDto> getFeed(String username) {		
		// Check if username is valid
		Optional<User> userToGetFeed = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
		if (userToGetFeed.isEmpty()) {
			throw new NotFoundException("No user with username: " + username);
		}		
		
		// Retrieve all users that the user is following
		List<User>usersBeingFollowed = userToGetFeed.get().getFollowing();
		
		// Retrieve all tweets from user
		List<Tweet>userFeed = userToGetFeed.get().getTweets();
		
		// Retrieve all tweets from those being followed
		for(User followee: usersBeingFollowed) {
			userFeed.addAll(followee.getTweets());
		}
		
		// Sort
		Collections.sort(userFeed, (y,x) -> x.getPosted().compareTo(y.getPosted()));		
		return tweetMapper.entitiesToDtos(userFeed);
	}
	@Override
	public UserResponseDto patchUser(String username, UserRequestDto userRequestDto) {
		Optional<User> optionalUserToPatch = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
		if (optionalUserToPatch.isEmpty()) {
			throw new NotFoundException("No user with username: " + username);
		}
		User userWithUpdates = userMapper.dtoToEntity(userRequestDto);
		if(!optionalUserToPatch.get().getCredentials().getPassword().equals(userWithUpdates.getCredentials().getPassword())) {
			throw new NotAuthorizedException("Incorrect Password");
		}
		User userToPatch = optionalUserToPatch.get();
		userToPatch.setProfile(userWithUpdates.getProfile());
		return userMapper.entityToDto(userRepository.saveAndFlush(userToPatch));
	}

}
