package com.model.exam;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int catid;
	
	private String title;
	
	private String description;
	
	@OneToMany(mappedBy = "category",
			orphanRemoval = true,cascade = CascadeType.REMOVE)
	@JsonIgnore
	private Set<Quiz> quizzes = new LinkedHashSet<>();
	
	
	

}
