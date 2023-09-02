package com.service;

import com.model.Role;
import com.model.User;
import com.model.User_role;

public interface User_roleService {
	
	
	
	// get User_role
	public User_role getUser_roleFromUserAndRole(User user,Role role) throws Exception;

}
