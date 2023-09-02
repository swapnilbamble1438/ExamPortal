package com.service;

import java.util.Set;

import com.model.exam.Category;
import com.model.exam.Quiz;

public interface QuizService {
	
	public Quiz addQuiz(Quiz quiz);
	
	public Quiz updateQuiz(Quiz quiz);
	
	public Set<Quiz> getQuizzes();
	
	public Quiz getQuiz(int quizid);
	
	public void deleteQuiz(int quizid) throws Exception;
	
	public Set<Quiz> getQuizzesOfCategory(int categoryId);
	
	
	public Set<Quiz> getActiveQuizzes(Boolean active);
	
	public Set<Quiz> getActiveQuizzesOfCategory(int categoryId,Boolean active);

}
