package com.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.config.CustomUserDetails;
import com.config.UserDetailsServiceImpl;
import com.exception.CustomException;
import com.helper.JwtUtil;
import com.model.JwtRequest;
import com.model.JwtResponse;
import com.model.User;
import com.repo.UserRepo;

@RestController
@CrossOrigin(origins = "*")
public class AuthenticateController {
	
	@Autowired
	private UserRepo userRepo;


	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
//======== method ==========================================================================================================================	

	private void authenticate(String username, String password) throws Exception
	{
		try {
			
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			
		} catch (DisabledException e) {

			throw new CustomException("User Disabled "+ e.getMessage());
		}catch(Exception e)
		{
			throw new CustomException("Invalid Credentials " + e.getMessage());
		}
	}
//============ Generate Token ================================================================================	
	/*
	  In Postman App
	  Body->raw JSON
	  	
	  	{
	  		"username":"swapnil",
	  		"password":"1234"
	  	}
	 */	
	@PostMapping("/token") // when trying this url,select auth type: No Auth
	public ResponseEntity<?> generateToken(
			@RequestBody JwtRequest jwtRequest) throws Exception
	{
	
		try {
			
			authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
		
		} catch (UsernameNotFoundException e) {
			e.printStackTrace();
			throw new CustomException("Invalid Credentials " + e.getMessage());
		
		} 
		// here credentials are authenticated successfully
		
		
		// fine area..
		
		final UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(jwtRequest.getUsername());
		System.out.println("userDetails.getUsername: "   +userDetails.getUsername());
	
	final String token =	jwtUtil.generateToken(userDetails);
	
	System.out.println("token: " + token);
	
	
	// {"token":"value"}
	
	return ResponseEntity.ok(new JwtResponse(token));
	}
	
	
	
	
	
	
	
//====================returns the details of loggedin user from CustomUserDetails ===========  // you can get role from this
	
	@GetMapping("/currentuser")
	public UserDetails getCurrentUser(Principal principal)
	{
		// return this.userRepo.findByUsername(principal.getName());
		
		return this.userDetailsServiceImpl.loadUserByUsername(principal.getName());
	}
	
//====================returns the details of loggedin user from User ===========  
	
		@GetMapping("/loggedinuser")
		public User getLoggedInUser(Principal principal)
		{
			 return this.userRepo.findByUsername(principal.getName());
			
			
		}	

//====================================================================================	
	
}
