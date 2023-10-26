package com.team2.Assessment1.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team2.Assessment1.entities.Credentials;
import com.team2.Assessment1.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	List<User> findAllByDeletedFalse();

	Optional<User> findByCredentialsUsernameAndDeletedFalse(String username);



	Boolean existsByCredentialsUsername(String username);

	Optional<User> findByCredentialsAndDeletedFalse(Credentials credentialsFollowingOther);

}
