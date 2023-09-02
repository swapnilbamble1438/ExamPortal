package com.service.impl;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exception.CustomException;
import com.model.exam.Category;
import com.model.exam.Quiz;
import com.repo.CategoryRepo;
import com.repo.QuizRepo;
import com.service.QuizService;

@Service
public class QuizServiceImpl implements QuizService{

	@Autowired
	private QuizRepo quizRepo;
	
	@Autowired
	private CategoryRepo catRepo;

//========= create quiz ==========================================	
	@Override
	public Quiz addQuiz(Quiz quiz) {
		
		return this.quizRepo.save(quiz);
	}
//========== update quiz ====================================================	
	@Override
	public Quiz updateQuiz(Quiz quiz) {
		
		return this.quizRepo.save(quiz);

	}
//============ get all quizzes ===============================================
	@Override
	public Set<Quiz> getQuizzes() {
		
		return  new HashSet<>(this.quizRepo.findAll());
	}
//========== get single quiz ==================================================
	@Override
	public Quiz getQuiz(int quizid) {
		
		return this.quizRepo.findById(quizid).get();
	}
//========== delete quiz =====================================================
	@Override
	public void deleteQuiz(int quizid) throws Exception {
		
		Optional<Quiz> quiz = this.quizRepo.findById(quizid);
		if(quiz.isEmpty())
		{
			System.out.println("\n=================================================================================================================\n"
			           + "          Message: There is no Quiz with quizId: "+quizid+" exists...  \n"
			           + "==========================================================================================================================");
			
			 throw new CustomException(" Message: There is no Quiz with quizId: "+quizid+" exists... ");
		}
		this.quizRepo.deleteById(quizid);
		
	}

//========== get quizzes by category ======================================================	
	@Override
	public Set<Quiz> getQuizzesOfCategory(int categoryId)
	{
		Category category = new Category();
		category = this.catRepo.getById(categoryId);
		
		Set<Quiz> quizzes = this.quizRepo.findBycategory(category);
		return quizzes;
	}
	
//========== get active quizzes=============================================================================
	
	@Override
	public Set<Quiz> getActiveQuizzes(Boolean active) {
		
		return  new HashSet<>(this.quizRepo.findByActive(active));
	}
	
//=========== get active quizzes by category ============================================================================
	
	@Override
	public Set<Quiz> getActiveQuizzesOfCategory(int categoryId,Boolean active) {

		Category category = new Category();
		category = this.catRepo.getById(categoryId);
		
		Set<Quiz> quizzes = this.quizRepo.findByCategoryAndActive(category, active);
		return quizzes;
	}
	
//=======================================================================================
	
	
}
