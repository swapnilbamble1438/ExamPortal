package com.model.outcome;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Result {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private int resultid;
	private int uid;
	private int quizid;
	private int numofquestions;
	private int correctanswers;
	private int marksgot;
	private int attempted;
	private String date;
	
	@Transient
	private String title;
	@Transient
	private String imagefile;
	
	@OneToMany(mappedBy ="result",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Userqna> userqnas;

}
