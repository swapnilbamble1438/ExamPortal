package com.service;

import java.util.List;
import java.util.Set;


import com.model.User;
import com.model.User_role;

public interface UserService {
	


	// create user 
	public User createUser(User user, Set<User_role> user_roles) throws Exception;

	// get user by username
	public User getUser(String username) throws Exception;
	
	// get user by uid
	public User getUserByUid(int uid) throws Exception;

	// delete user by uid
	public void deleteUser(int uid) throws Exception;
	
	// update user
	public User updateUser(User user,Set<User_role> user_roles) throws Exception;

	// get all users
	public List<User> getAllUsers();


}
