package com.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.exam.Question;
import com.model.exam.Quiz;

public interface QuestionRepo extends JpaRepository<Question, Integer> {

	Set<Question> findByQuiz(Quiz quiz);
}
