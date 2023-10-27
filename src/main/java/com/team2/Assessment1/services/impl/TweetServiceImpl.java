package com.team2.Assessment1.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.team2.Assessment1.dtos.ContextDto;
import com.team2.Assessment1.dtos.CredentialsDto;
import com.team2.Assessment1.dtos.HashtagDto;
import com.team2.Assessment1.dtos.TweetRequestDto;
import com.team2.Assessment1.dtos.TweetResponseDto;
import com.team2.Assessment1.dtos.UserResponseDto;
import com.team2.Assessment1.entities.Hashtag;
import com.team2.Assessment1.entities.Credentials;
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
import com.team2.Assessment1.services.TweetService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

	// Mapper Declarations
	private final TweetMapper tweetMapper;
	private final HashtagMapper hashtagMapper;
	private final UserMapper userMapper;
	private final CredentialsMapper credentialsMapper;
	
	// Repository Declarations
	private final TweetRepository tweetRepository;
	private final UserRepository userRepository;
	private final HashtagRepository hashtagRepository;

	/************************************
	 * GET Methods
	 ************************************/
	@Override
	public List<TweetResponseDto> getAllTweets() {
		return tweetMapper.entitiesToDtos(tweetRepository.findAllByDeletedFalse());
	}

	@Override
	public TweetResponseDto getTweet(Long id) {
		Optional<Tweet> requestedTweet = tweetRepository.findById(id);

		if (requestedTweet.isEmpty()) {
			throw new NotFoundException("Tweet with id '" + id + "' not found");
		}

		if (requestedTweet.get().isDeleted()) {
			throw new NotFoundException("Tweet with id '" + id + "' has been deleted");
		}

		return tweetMapper.entityToDto(requestedTweet.get());
	}

	@Override
	public ContextDto getTweetContext(Long id) {
		Optional<Tweet> requestedTweet = tweetRepository.findById(id);

		if (requestedTweet.isEmpty()) {
			throw new NotFoundException("Tweet with id '" + id + "' not found");
		}

		if (requestedTweet.get().isDeleted()) {
			throw new NotFoundException("Tweet with id '" + id + "' has been deleted");
		}
		ContextDto contextDto = new ContextDto();
		List<Tweet> before = new ArrayList<>();
		List<Tweet> after = new ArrayList<>();
		Tweet currentTweet = requestedTweet.get();
		while (currentTweet.getInReplyTo() != null) {
			before.add(0, currentTweet.getInReplyTo());
			currentTweet = currentTweet.getInReplyTo();
		}
		System.out.println("Getting Reply Chain");
		after = getReplyChain(requestedTweet.get(), after);
		after = sortTweetsInChronologicalOrder(after);
		contextDto.setTarget(tweetMapper.entityToDto(requestedTweet.get()));
		contextDto.setBefore(tweetMapper.entitiesToDtos(before));
		contextDto.setAfter(tweetMapper.entitiesToDtos(after));
		return contextDto;
	}

	@Override
	public List<UserResponseDto> getTweetLikes(Long id) {
		Optional<Tweet> requestedTweet = tweetRepository.findById(id);

		if (requestedTweet.isEmpty()) {
			throw new NotFoundException("Tweet with id '" + id + "' not found");
		}

		if (requestedTweet.get().isDeleted()) {
			throw new NotFoundException("Tweet with id '" + id + "' has been deleted");
		}

		return userMapper.entitiesToDtos(requestedTweet.get().getLikedBy());
	}
	

	@Override
	public List<UserResponseDto> getMentionedUsers(Long id) {
		//Check if tweet exists
		Optional<Tweet> tweetMentionsUsers = tweetRepository.findByIdAndDeletedFalse(id);
		if(tweetMentionsUsers.isEmpty()) {
			throw new NotFoundException("The tweet with the id: " + id + " is not found");
		}
		//Retrieves the users mentioned in the tweet
		List<User> usersMentioned = tweetMentionsUsers.get().getMentions();
		
		//Removed deleted users mentioned in the tweet
		for (User u:usersMentioned) {
			if(u.isDeleted() == true) {
				usersMentioned.remove(u);
			}
		}
		
		return userMapper.entitiesToDtos(usersMentioned);
	}

	@Override
	public List<TweetResponseDto> getTweetReplies(Long id) {
		Optional<Tweet> requestedTweet = tweetRepository.findById(id);

		if (requestedTweet.isEmpty()) {
			throw new NotFoundException("Tweet with id '" + id + "' not found");
		}

		if (requestedTweet.get().isDeleted()) {
			throw new NotFoundException("Tweet with id '" + id + "' has been deleted");
		}

		return tweetMapper.entitiesToDtos(requestedTweet.get().getReplies());
	}
	
	@Override
	public List<TweetResponseDto> getTweetReposts(Long id) {
		//Check if tweet exists (created and not deleted)
		Optional<Tweet> originalRepost = tweetRepository.findByIdAndDeletedFalse(id);
		if(originalRepost.isEmpty()) {
			throw new NotFoundException("The tweet with the id: " + id + " is not found");
		}		
		//Retrieves the direct posts of the tweet with the given id, deleted reposts of the tweet should be excluded		
		List<Tweet> directReposts = tweetRepository.findAllByRepostOfAndDeletedFalse(originalRepost.get());		
		return tweetMapper.entitiesToDtos(directReposts);
	}

	@Override
	public List<HashtagDto> getTweetTags(Long id) {
		Optional<Tweet> requestedTweet = tweetRepository.findById(id);

		if (requestedTweet.isEmpty()) {
			throw new NotFoundException("Tweet with id '" + id + "' not found");
		}

		if (requestedTweet.get().isDeleted()) {
			throw new NotFoundException("Tweet with id '" + id + "' has been deleted");
		}

		return hashtagMapper.entitiesToDtos(requestedTweet.get().getTags());
	}

	/************************************
	 * POST Methods
	 ************************************/
	@Override
	public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {
		CredentialsDto userCredentials = tweetRequestDto.getCredentials();

		// Error Handling for BadRequestException
		if (tweetRequestDto.getContent() == null) {
			throw new BadRequestException("Bad Request: No Content");
		}

		if (userCredentials == null) {
			throw new BadRequestException("Bad Request: No Credentials");
		}

		if (userCredentials.getPassword() == null || userCredentials.getUsername() == null) {
			throw new BadRequestException("Bad Request: Missing username/password in Credentials");
		}

		Tweet newTweet = tweetMapper.dtoToEntity(tweetRequestDto);
		User user = userRepository.findByCredentialsUsernameAndDeletedFalse(userCredentials.getUsername()).get();

		// Check if User exists
		if (user == null || user.isDeleted()) {
			throw new NotFoundException("User not found or not active" + user);
		}

		newTweet.setAuthor(user);

		// Take the #hashTags and @User mentions and store those appropriately
		String[] contentSplitOnSpace = newTweet.getContent().split("\\s");

		for (String word : contentSplitOnSpace) {

			// Addresses the #tags used in the Tweets content
			if (word.startsWith("#")) {
				String label = word.substring(1, word.length());
				Hashtag tag = hashtagRepository.findByLabel(word);
				if (tag == null) {
					tag = new Hashtag();
					tag.setLabel(label);

					tag.setTaggedTweets(Collections.singletonList(newTweet));
					newTweet.setTags(Collections.singletonList(tag));
				} else {
					tag.getTaggedTweets().add(newTweet);
				}

				hashtagRepository.saveAndFlush(tag);
			}

			// Adds users to the Tweets List<User> mentions
			if (word.startsWith("@")) {
				String userName = word.substring(1, word.length());
				Optional<User> mentionedUser = userRepository.findByCredentialsUsernameAndDeletedFalse(userName);

				newTweet.setMentions(Collections.singletonList(mentionedUser.get()));
				mentionedUser.get().getMentionedBy().add(newTweet);
			}
		}

		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(newTweet));
	}

	@Override
	public void likeTweet(Long id, CredentialsDto credentialsDto) {
		Tweet tweetToLike = tweetRepository.getById(id);
		User user = userRepository.findByCredentialsUsernameAndDeletedFalse(credentialsDto.getUsername()).get();

		if (tweetToLike == null || tweetToLike.isDeleted()) {
			throw new NotFoundException("Tweet with id '" + id + "' has been deleted or doesn't exist");
		}

		if (user == null || user.isDeleted()) {
			throw new NotFoundException("User matching given credentials not found");
		}
		
		if(!tweetToLike.getLikedBy().contains(user)) {
			user.getLikedTweets().add(tweetToLike);
			userRepository.saveAndFlush(user);
		}

	}
	
	@Override
	public TweetResponseDto replyTweet(Long id, TweetRequestDto tweetRequestDto) {
		CredentialsDto userCredentials = tweetRequestDto.getCredentials();
		Optional<Tweet> originalTweet = tweetRepository.findByIdAndDeletedFalse(id);

		// Error Handling for BadRequestException
		if (tweetRequestDto.getContent() == null) {
			throw new BadRequestException("Bad Request: No Content");
		}
		if (userCredentials == null) {
			throw new BadRequestException("Bad Request: No Credentials");
		}
		if (userCredentials.getPassword() == null || userCredentials.getUsername() == null) {
			throw new BadRequestException("Bad Request: Missing username/password in Credentials");
		}
		if (originalTweet.isEmpty()) {
			throw new NotFoundException("Tweet with id: " + id + " does not exist");
		}

		Tweet replyTweet = tweetMapper.dtoToEntity(tweetRequestDto);
		User user = userRepository.findByCredentialsUsernameAndDeletedFalse(userCredentials.getUsername()).get();

		// Check if User exists
		if (user == null || user.isDeleted()) {
			throw new NotFoundException("User not found or not active" + user);
		}

		replyTweet.setAuthor(user);
		replyTweet.setInReplyTo(originalTweet.get());
		List<Tweet> newRepliesList = originalTweet.get().getReplies();
		newRepliesList.add(replyTweet);
		originalTweet.get().setReplies(newRepliesList);
		
		//Take the #hashTags and @User mentions and store those appropriately
		String[] contentSplitOnSpace = replyTweet.getContent().split("\\s");		
		
		
		for(String word: contentSplitOnSpace) {
			
			// Addresses the #tags used in the Tweets content
			if(word.startsWith("#")) {
				String label = word.substring(1, word.length());
				Hashtag tag = hashtagRepository.findByLabel(word);
				if(tag == null) {
					tag = new Hashtag();
					tag.setLabel(label);
					
					tag.setTaggedTweets(Collections.singletonList(replyTweet));
					replyTweet.setTags(Collections.singletonList(tag));
				} else {
					tag.getTaggedTweets().add(replyTweet);
				}
				
				hashtagRepository.saveAndFlush(tag);
			}
			
			// Adds users to the Tweets List<User> mentions
			if(word.startsWith("@")) {
				String userName = word.substring(1, word.length());
				Optional<User> mentionedUser = userRepository.findByCredentialsUsernameAndDeletedFalse(userName);
				
				replyTweet.setMentions(Collections.singletonList(mentionedUser.get()));
				mentionedUser.get().getMentionedBy().add(replyTweet);
			}
		}
		tweetRepository.saveAndFlush(originalTweet.get());
		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(replyTweet));
	}

	public TweetResponseDto repostTweet(Long id, CredentialsDto credentialsDto) {
		// Error Handling for BadRequestException
		Tweet originalTweet = tweetMapper.responseDtoToEntity(getTweet(id));
		if (originalTweet.isDeleted() || originalTweet == null) {
			throw new BadRequestException("Tweet was deleted or doesn't exist");
		}
		Credentials submittedCredentials = credentialsMapper.dtoToEntity(credentialsDto);
		boolean matchesUser = false;
		for (User user : userRepository.findAllByDeletedFalse()) {
			if (user.getCredentials().equals(submittedCredentials)) {
				matchesUser = true;
				break;
			}
		}
		if (!matchesUser) {
			throw new NotAuthorizedException("Username/Password is incorrect");
		}

		Tweet repost = new Tweet();
		repost.setAuthor(userRepository.findByCredentialsUsernameAndDeletedFalse(submittedCredentials.getUsername()).get());
		repost.setRepostOf(originalTweet);
		

		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(repost));

	}

	/************************************
	 * DELETE Methods
	 ************************************/
	@Override
	public TweetResponseDto deleteTweet(Long id, CredentialsDto credentialsDto) {
		Optional<Tweet> findTweet = tweetRepository.findById(id);

		if (findTweet.isEmpty()) {
			throw new NotFoundException("Tweet with id '" + id + "' not found");
		}

		Tweet deletedTweet = findTweet.get();
		User user = userRepository.findByCredentialsUsernameAndDeletedFalse(credentialsDto.getUsername()).get();

		if (!deletedTweet.getAuthor().equals(user)) {
			throw new NotFoundException("User requesting deletion is not author of the tweet");
		}

		if (deletedTweet.isDeleted()) {
			throw new BadRequestException("Tweet is already deleted with id " + id);
		}

		deletedTweet.setDeleted(true);

		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(deletedTweet));
	}

	/************************************
	 * Helper Methods
	 ************************************/
	private List<Tweet> getReplyChain(Tweet tweet, List<Tweet> after) {
		if (after.isEmpty()) {
			after = new ArrayList<>();
		}
		if (tweet.getReplies().isEmpty()) {
			return after;
		} else {
			for (Tweet reply : tweet.getReplies()) {
				System.out.println(reply.getId());
				after.add(reply);
				after = getReplyChain(reply, after);
			}
		}

		return after;
	}

	private List<Tweet> sortTweetsInChronologicalOrder(List<Tweet> tweets) {
		List<Tweet> sortedList = new ArrayList<>();
		System.out.println("Sorting");
		for (Tweet tweet : tweets) {
			System.out.println(tweet.getId());
			if (sortedList.isEmpty()) {
				sortedList.add(tweet);
			} else {
				for (Tweet sortedTweet : sortedList) {
					// if tweet being sorted is older than the already sorted tweet, insert at this position
					if (tweet.getPosted().compareTo(sortedTweet.getPosted()) <= 0) {
						sortedList.add(sortedList.indexOf(sortedTweet), tweet);
						break;
					}
				}
				sortedList.add(tweet);
			}
		}
		System.out.println("Sorted List");
		for (Tweet tweet : sortedList) {
			System.out.println(tweet.getId());
		}
		return sortedList;
	}
}

