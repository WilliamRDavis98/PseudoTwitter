package com.team2.Assessment1.dtos;

import java.sql.Timestamp;

import com.team2.Assessment1.entities.Profile;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserResponseDto {
	private String username;
	private Profile profile;
	private Timestamp joined;
}
