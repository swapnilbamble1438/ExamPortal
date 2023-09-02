package com.service;

import java.util.Set;

import com.model.exam.Question;
import com.model.exam.Quiz;

public interface QuestionService {

	public Question addQuestion(Question question);
	
	public Question updateQuestion(Question question);
	
	public Set<Question> getQuestions();
	
	public Question getQuestion(int quesId);
	
	public void deleteQuestion(int quesId);
	
	public Set<Question> getQuestionsofQuiz(int quizId);
	
	
}
