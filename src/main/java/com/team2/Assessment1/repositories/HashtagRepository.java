package com.team2.Assessment1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team2.Assessment1.entities.Hashtag;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

	Hashtag findByLabel(String label);

}
