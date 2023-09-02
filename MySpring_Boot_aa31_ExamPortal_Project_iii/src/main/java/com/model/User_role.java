package com.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class User_role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int urid;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	
	@ManyToOne
	private Role role;

	
	
	

}
