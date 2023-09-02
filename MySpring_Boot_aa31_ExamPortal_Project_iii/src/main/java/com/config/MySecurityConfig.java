package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	private AuthenticationEntryPoint entryPoint;
	
	@Autowired
	private JwtAuthenticationFilter filter;
	
	
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(this.userDetailsServiceImpl).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.csrf()
			.disable()
			.cors()
			.disable()
			.authorizeRequests()
			.antMatchers("/token","/user/create").permitAll()
			.antMatchers(HttpMethod.OPTIONS).permitAll()
			.anyRequest().authenticated()
		//	.anyRequest().permitAll()
			.and()
			.exceptionHandling().authenticationEntryPoint(entryPoint)
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http
		.csrf().disable()
		.cors().disable().
		addFilterBefore(filter,UsernamePasswordAuthenticationFilter.class);
			
		
	}
	
	
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
		return super.authenticationManagerBean();
	}

	
	
	
	
	
	
	
	
	
	

}
