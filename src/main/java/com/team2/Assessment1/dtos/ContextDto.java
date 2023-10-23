package com.team2.Assessment1.dtos;

import java.util.List;

import com.team2.Assessment1.entities.Tweet;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ContextDto {
	private Tweet target;
	private List<Tweet> before;	
	private List<Tweet> after;
}
