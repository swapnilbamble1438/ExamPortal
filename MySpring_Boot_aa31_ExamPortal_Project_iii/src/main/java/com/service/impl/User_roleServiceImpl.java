package com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Role;
import com.model.User;
import com.model.User_role;
import com.repo.User_roleRepo;
import com.service.UserService;
import com.service.User_roleService;

@Service
public class User_roleServiceImpl implements User_roleService {

	@Autowired
	private User_roleRepo user_roleRepo;
	
	@Autowired
	private UserService userServ;
	
	
//================ get user_role ======================================================================================================================

	@Override
	public User_role getUser_roleFromUserAndRole(User user, Role role) throws Exception 
	{
		User local = userServ.getUserByUid(user.getUid());
		
		return this.user_roleRepo.getByUAndR(local, role);
	}

	
//=====================================================================================================================================
	
}
