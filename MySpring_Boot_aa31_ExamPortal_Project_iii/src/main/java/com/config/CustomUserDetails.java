package com.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.model.Role;
import com.model.User;
import com.model.User_role;



public class CustomUserDetails implements UserDetails {


	private User user;
	
	
	public CustomUserDetails(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Set<Authority> set = new HashSet<>();
		
		this.user.getUser_roles().forEach(user_roles -> {
			set.add(new Authority(user_roles.getRole().getRolename()));
		});
		
					System.out.println("==============================================================================================================================================================================================");
					
					this.user.getUser_roles().forEach(user_roles -> {
						System.out.println("Role:" +user_roles.getRole().getRolename());
					});
					
					System.out.println("==============================================================================================================================================================================================");
			
					
		
		return set;
	}

	@Override
	public String getPassword() {

		return user.getPassword();
	}

	@Override
	public String getUsername() {

		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {

		return user.isEnabled();
	}
	
	
	public int getUid()
	{
		return user.getUid();
	}
	
	public String getFirstname() 
	{
		return user.getFirstname();
	}
	
	public String getLastname()
	{
		return user.getLastname();
	}
	
	public String getEmail()
	{
		return user.getEmail();
	}
	
	public String getPhone()
	{
		return user.getPhone();
	}
	
	public String getProfile()
	{
		return user.getProfile();
	}
	
//	public Set<User_role> getUser_roles() {
//		return user.getUser_roles();
//	}
//	
	
	
	
	
	
	

}
