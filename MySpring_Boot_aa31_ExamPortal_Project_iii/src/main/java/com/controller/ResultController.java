package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exception.CustomException;
import com.model.exam.Quiz;
import com.model.outcome.Result;
import com.service.QuestionService;
import com.service.QuizService;
import com.service.ResultService;

@RestController
@RequestMapping("/result")
@CrossOrigin(origins="*")
public class ResultController {

	@Autowired
	private ResultService resultServ;
	
	@Autowired
	private QuizService quizServ;
	
	@PostMapping("/create")
	public ResponseEntity<?> addResult(@RequestBody Result result) throws CustomException
	{
		System.out.println("============================================================================================================================================");
		System.out.println("uid: " + result.getUid());
		System.out.println("quizid: " + result.getQuizid());
		System.out.println("numofquestions: " + result.getNumofquestions());
		System.out.println("quesid: " + result.getUserqnas().get(0).getQuesid());
		System.out.println("answer: " + result.getUserqnas().get(0).getAnswer());
		System.out.println("============================================================================================================================================");

		return ResponseEntity.ok(this.resultServ.addResult(result));
	}
	
	
	@GetMapping("/current")
	public ResponseEntity<?> getCurrentResult() throws CustomException
	{
		return ResponseEntity.ok(this.resultServ.getCurrentResult());
	}
	
	
	@GetMapping("/all/{uid}")
	public ResponseEntity<List<Result>> getAllResultsofUser(@PathVariable("uid") int uid) throws CustomException
	{
		
		return ResponseEntity.ok(this.resultServ.getResultsofUser(uid));
	}
	
	@DeleteMapping("/deleteall/{uid}")
	public void deleteAllResultsofUser(@PathVariable("uid")int uid)
	{
		this.resultServ.deleteAllResultOfUser(uid);
	}
	
	@DeleteMapping("/delete/{resultid}")
	public void deleteResult(@PathVariable("resultid")int resultid)
	{
		this.resultServ.deleteResult(resultid);
	}
	
	
	
}
