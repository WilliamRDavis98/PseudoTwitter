package com.team2.Assessment1.mappers;

import org.mapstruct.Mapper;

import com.team2.Assessment1.dtos.UserRequestDto;
import com.team2.Assessment1.dtos.UserResponseDto;
import com.team2.Assessment1.entities.User;

@Mapper(componentModel = "spring")//, uses = { CredentialsMapper.class, ProfileMapper.class })
public interface UserMapper {
	UserResponseDto entityToDto(User entity);
	User dtoToEntity(UserRequestDto requestDto);
}
