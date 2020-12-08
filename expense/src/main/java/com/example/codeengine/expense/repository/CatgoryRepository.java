package com.example.codeengine.expense.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.codeengine.expense.model.Catgory;

public interface CatgoryRepository extends JpaRepository<Catgory, Long> {
	
	Catgory findByName(String name);
	
}
