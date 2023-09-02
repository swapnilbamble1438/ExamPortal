package com.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.User;

public interface UserRepo extends JpaRepository<User, Integer>{

	
	
	User findByUsername(String username);
	
	@Query("select u from User u where u.username = :username and u.uid != :uid")
	public User getByUnameAndNouid(@Param("username") String username,@Param("uid") int uid);
	
	

	
}
