package com.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exception.CustomException;
import com.model.Role;
import com.model.User;
import com.model.User_role;
import com.service.UserService;
import com.service.User_roleService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
	
	@Autowired
	private UserService userServ;
	
	@Autowired
	private User_roleService user_roleServ;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
	
//================ create user ======================================================================================================================
	
	/*
	 	{      
		   "username":"yashmore",
           "firstname":"Yash",
           "lastname":"More",
           "password":"1234",
           "email":"yash@gmail.com",
           "phone":"1234567891",
           "profile":"default.png"   
		}
	*/
	@PostMapping("/create")
	public User createUser(@RequestBody User user) throws Exception	
	{
		user.setProfile("profile.png");
		
		// encoding password with PasswordEncoder
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		Role role = new Role();
		role.setRid(2);
		role.setRolename("ROLE_NORMAL");
		
		User_role user_role = new User_role();
		user_role.setUser(user);
		user_role.setRole(role);
		
			
		Set<User_role> user_roles = new HashSet<>();
		user_roles.add(user_role);
		
	
		return this.userServ.createUser(user, user_roles);
	}
	
//========== get user by uid ======================================================================================================================
		
		@GetMapping("/uid/{uid}")
		public User getUser(@PathVariable("uid") int uid) throws Exception
		{
			return this.userServ.getUserByUid(uid);
		}
	
	
//========== get user by username ======================================================================================================================
	
	@GetMapping("/username/{username}")
	public User getUser(@PathVariable("username") String username) throws Exception
	{
		return this.userServ.getUser(username);
	}
	
	
//========== get all users ======================================================================================================================
	
	@GetMapping("/all")
	public List<User> getAllUsers()
	{
		
		return this.userServ.getAllUsers();
	}
	
//=========== delete user by uid ======================================================================================================================
	
	@DeleteMapping("/delete/{uid}")
	public void deleteUser(@PathVariable("uid") int uid) throws Exception
	{
		
		this.userServ.deleteUser(uid);
		
	}
	
	
//========= update user ======================================================================================================================

	/*
	 	{    
	 	   "uid":"2",  
		   "username":"yashmore",
           "firstname":"Yash",
           "lastname":"More",
           "password":"1234",
           "email":"yash@gmail.com",
           "phone":"1234567891",
           "profile":"default.png"   
		}
	*/
	
	@PutMapping("/update")
	public User updateUser(@RequestBody User user) throws Exception
	{
		
		// encoding password with PasswordEncoder
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		
		Role roleAdmin = new Role();
		roleAdmin.setRid(1);
		roleAdmin.setRolename("ROLE_Admin");
		
		
		Role roleNormal = new Role();
		roleNormal.setRid(2);
		roleNormal.setRolename("ROLE_NORMAL");
		
		User_role user_role = new User_role();
		
		Role role = new Role();
		if(user.getUsername() == "admin" || user.getUsername().equals("admin"))
		{
			role = roleAdmin;
		}
		else {
			role = roleNormal;
		}
	
			User_role local = this.user_roleServ.getUser_roleFromUserAndRole(user,role);
		
		user_role.setUrid(local.getUrid());
		user_role.setUser(user);
		user_role.setRole(role);
		
		Set<User_role> user_roles = new HashSet<>();
		user_roles.add(user_role);
		
	
		return this.userServ.updateUser(user, user_roles);
		
	}
	
//======================================================================================================================================
	
	
	

}
