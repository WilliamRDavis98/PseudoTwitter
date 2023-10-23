package com.team2.Assessment1.mappers;

import org.mapstruct.Mapper;

import com.team2.Assessment1.dtos.CredentialsDto;
import com.team2.Assessment1.entities.Credentials;

@Mapper(componentModel = "spring")
public interface CredentialsMapper {
	CredentialsDto entityToDto(Credentials entity); //TODO: Do we just need this?
	Credentials dtoToEntity(CredentialsDto dto);
}
