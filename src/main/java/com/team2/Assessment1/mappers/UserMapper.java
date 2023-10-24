package com.team2.Assessment1.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.team2.Assessment1.dtos.UserRequestDto;
import com.team2.Assessment1.dtos.UserResponseDto;
import com.team2.Assessment1.entities.User;

@Mapper(componentModel = "spring", uses = { CredentialsMapper.class, ProfileMapper.class })
public interface UserMapper {
	
	
	@Mapping(target = "username", source = "credentials.username")
	UserResponseDto entityToDto(User entity);
	User dtoToEntity(UserRequestDto requestDto);
//	List<UserResponseDto> entitiesToDtos(List<User> followedUsers);
}
