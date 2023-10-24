package com.team2.Assessment1.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.team2.Assessment1.dtos.TweetRequestDto;
import com.team2.Assessment1.dtos.TweetResponseDto;
import com.team2.Assessment1.entities.Tweet;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface TweetMapper {
	TweetResponseDto entityToDto(Tweet entity);
	Tweet dtoToEntity(TweetRequestDto requestDto);
	
	List<TweetResponseDto> entitiesToDtos(List<Tweet> entities);
	List<Tweet> dtosToEntities(List<TweetRequestDto> requestDtos);
}
