package com.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.exception.CustomException;


@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint
	{
		@Override
		public void commence(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException authException) throws IOException, ServletException {
	
		
	//	response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"401 Unauthorized : Server");
			
			 System.out.println("\n=================================================================================================================\n"
			           + "         Message: 401 Unauthorized Request  \n"
			           + "==========================================================================================================================");
			

	        response.setContentType("application/json");
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        response.getOutputStream().println("{\"a\":\"=================================================================================================\","
	        		+ " \"Exception\": \"" + new CustomException("401 Unauthorized Request").getMessage() + "\" ,"
	        		+ "\"b\":\"==============================================================================================================\"}");

	}

}
