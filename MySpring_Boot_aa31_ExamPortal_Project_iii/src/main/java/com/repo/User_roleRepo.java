package com.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.Role;
import com.model.User;
import com.model.User_role;

public interface User_roleRepo extends JpaRepository<User_role, Integer>{

	
	@Query("select u from User_role u where u.user = :user and u.role = :role")
	public User_role getByUAndR(@Param("user") User user,@Param("role") Role role);
	
	
	
}
