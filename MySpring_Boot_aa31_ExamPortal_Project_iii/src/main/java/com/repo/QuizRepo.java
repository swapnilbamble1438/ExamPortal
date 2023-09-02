package com.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.exam.Category;
import com.model.exam.Quiz;


public interface QuizRepo extends JpaRepository<Quiz, Integer>{

	public Set<Quiz> findBycategory(Category category);
	
//	@Query("delete q from Quiz q where q.quizid = :quizid")
//	public void deleteByQuizIdd(@Param("quizid")int quizid);

//	@Query("select q from Quiz q where q.category = :category")
//	Set<Quiz> findByCategory(@Param("category")Category category);

	// Get quizzes whose active status are true
	public Set<Quiz> findByActive(Boolean active);
	
	// get quizzes by category whose active status are true
	public Set<Quiz> findByCategoryAndActive(Category c,Boolean active);

}
