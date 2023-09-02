package com.model.exam;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Question {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private int quesid;
	@Column(length = 5000)
	private String content;
	
	private String image;
	
	private String option1;
	
	private String option2;
	
	private String option3;
	
	private String option4;
	
//	@JsonIgnore // this we have given, so that this field will not be load in client/browser side.
	private String answer;  //  you also will not able to see this field in admin page also
							// but if you want to see this field you have to
							// remove this @JsonIgnore field from here.
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Quiz quiz;

	
	

}
