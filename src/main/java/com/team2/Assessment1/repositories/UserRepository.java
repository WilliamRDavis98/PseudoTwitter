package com.team2.Assessment1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team2.Assessment1.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

//	User findByUsername(String username);

}
