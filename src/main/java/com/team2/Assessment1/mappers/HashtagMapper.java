package com.team2.Assessment1.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.team2.Assessment1.dtos.HashtagDto;
import com.team2.Assessment1.entities.Hashtag;

@Mapper(componentModel = "spring")
public interface HashtagMapper {
	
	HashtagDto entityToDto(Hashtag entity);
	Hashtag dtoToEntity(HashtagDto dto);
	List<HashtagDto> entitiesToDtos(List<Hashtag> findAll);

}
