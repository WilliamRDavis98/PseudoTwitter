package com.team2.Assessment1.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.team2.Assessment1.entities.Tweet;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

//	@Query("SELECT t FROM Tweet t WHERE t.content LIKE %:username% AND t.isDeleted = false ORDER BY t.createdAt DESC")
//    List<Tweet> findMentionsByUsername(@Param("username") String username);

}
