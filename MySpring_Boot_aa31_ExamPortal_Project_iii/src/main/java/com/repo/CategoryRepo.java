package com.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.exam.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{
	
	
}
