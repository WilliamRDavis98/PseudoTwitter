package com.team2.Assessment1.mappers;

import org.mapstruct.Mapper;

import com.team2.Assessment1.dtos.ProfileDto;
import com.team2.Assessment1.entities.Profile;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
	ProfileDto entityToDto(Profile entity);
	Profile dtoToEntity(ProfileDto dto);
}
