package com.example.codeengine.expense.controller;

import java.net.URI;
import java.net.URISyntaxException;t
import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.codeengine.expense.model.Catgory;
import com.example.codeengine.expense.repository.CatgoryRepository;

@RestController
@RequestMapping("/api")
public class CatgoryController {
	private CatgoryRepository catgoryRepository;
	

	public CatgoryController(CatgoryRepository catgoryRepository) {
		super();
		this.catgoryRepository = catgoryRepository;
	}
	
	//Get All Category
	@GetMapping("/categories")
	Collection<Catgory> categories(){
		return catgoryRepository.findAll();
	}
	
	//Get specific category by Id
	@GetMapping("/category/{id}")
	ResponseEntity<?> getCategory(@PathVariable Long id){
		 Optional<Catgory> category = catgoryRepository.findById(id);
		 return category.map(response -> ResponseEntity.ok().body(response))
				 .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	//Add new category 
	@PostMapping("/category")
	ResponseEntity<Catgory> createCategory(@Valid @RequestBody Catgory category)throws URISyntaxException{
		Catgory result = catgoryRepository.save(category);
		return ResponseEntity.created(new URI("/api/category"+ result.getId())).body(result);
	}

    //Update category
	@PutMapping("/category/{id}")
	ResponseEntity<Catgory> updateCategory(@Valid @RequestBody Catgory category){
		Catgory result = catgoryRepository.save(category);
		return ResponseEntity.ok().body(result);
	}

	//Delete category
	@DeleteMapping("/category/{id}")
	ResponseEntity<?> deleteCategory(@PathVariable Long id){
		catgoryRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}

}







