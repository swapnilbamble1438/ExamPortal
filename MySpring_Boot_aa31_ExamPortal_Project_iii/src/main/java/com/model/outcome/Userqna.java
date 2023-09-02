package com.model.outcome;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Userqna {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private int uqna;
	private int quesid;
	private String answer;
	
	
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JsonBackReference
	private Result result;

}
