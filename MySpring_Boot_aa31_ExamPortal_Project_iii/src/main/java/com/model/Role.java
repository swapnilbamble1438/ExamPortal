package com.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int rid;
	private String rolename;
	
	@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.LAZY,mappedBy = "role")
	private Set<User_role> user_roles = new HashSet<>();

}
