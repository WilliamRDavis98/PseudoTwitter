package com.team2.Assessment1.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.team2.Assessment1.dtos.HashtagDto;
import com.team2.Assessment1.mappers.HashtagMapper;
import com.team2.Assessment1.repositories.HashtagRepository;
import com.team2.Assessment1.services.HashtagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {
	private final HashtagMapper hashtagMapper;
	private final HashtagRepository hashtagRepository;
	
	@Override
	public List<HashtagDto> getAllHashtags() {
		return hashtagMapper.entitiesToDtos(hashtagRepository.findAll());
	}

}
