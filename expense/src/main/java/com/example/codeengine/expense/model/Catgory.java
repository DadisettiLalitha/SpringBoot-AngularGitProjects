package com.example.codeengine.expense.model;


import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor 
@Entity
@Data
@Table(name = "catgory")
public class Catgory {

	@Id
	 private Long id;
	 
	@NonNull
	 private String name;

	public Catgory() {
		super();
	}
	 
}
