package com.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.exception.CustomException;
import com.helper.JwtUtil;

@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		// get jwt
		//check if it starting from Bearer
		//validate
		
	String requestTokenHeader = request.getHeader("Authorization");
	
	String username = null;
	String jwtToken = null;
	if(requestTokenHeader!= null && requestTokenHeader.startsWith("Bearer "))
	{
		jwtToken = requestTokenHeader.substring(7); // it will remove Bearer from
		System.out.println("jwtToken:" + jwtToken);
		try {
			username = jwtUtil.extractUsername(jwtToken);
			System.out.println("username: " + username);
		}catch(Exception e)
		{
			 System.out.println("\n=================================================================================================================\n"
			           + "         Message: Token is not valid  \n"
			           + "==========================================================================================================================");
			
			 response.setContentType("application/json");
		        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		        response.getOutputStream().println("{\"a\":\"=================================================================================================\","
		        		+ " \"Exception\": \"Token is not valid\" ,"
		        		+ "\"b\":\"==============================================================================================================\"}");

			
			 		  
		}
	}
	
	
	
		 // validate token
		if(username!= null && SecurityContextHolder.getContext().getAuthentication() == null) 
		{
			 UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
 
			 System.out.println("name: "+ userDetails.getUsername());
			 System.out.println("password: "+ userDetails.getPassword());
			 
			 if(jwtUtil.validateToken(jwtToken, userDetails))
			 {
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =	new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			
			}
			else {
				System.out.println("Token is not validate");
			}
		
		
	}
	
	filterChain.doFilter(request, response);
		
		
	}

}
