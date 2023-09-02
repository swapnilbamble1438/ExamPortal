package com.service.impl;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.exam.Category;
import com.repo.CategoryRepo;
import com.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	
	@Autowired
	private CategoryRepo catRepo;
	
	@Override
	public Category addCategory(Category category) {
	
		return this.catRepo.save(category);
	}

	@Override
	public Category updateCategory(Category category) {
		
		return this.catRepo.save(category);
	}

	@Override
	public Set<Category> getCategories() {
		
		return  new LinkedHashSet<>(this.catRepo.findAll());
	}

	@Override
	public Category getCategory(int catid) {
		
//		Optional<Category> category = this.catRepo.findById(catid);
//		if(category.isPresent())
//		{
//			return category.get();
//		}
//		return null;
		
	//	return this.catRepo.getById(catid);
		
		return this.catRepo.findById(catid).get();
	}

	@Override
	public void deleteCategory(int catid) {
	
	this.catRepo.deleteById(catid);
	
		
	}

}
