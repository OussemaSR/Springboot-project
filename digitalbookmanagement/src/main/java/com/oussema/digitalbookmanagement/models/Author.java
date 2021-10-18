package com.oussema.digitalbookmanagement.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Author {

	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String FirstName;
	private String LastName;
	/*
	 * 
	 * @ManyToMany(mappedBy = "authors") private Set<Book> book = new HashSet<>();
	 */


	public Author() {
		super();
	}



	public Author(String firstName, String lastName) {
		super();
		FirstName = firstName;
		LastName = lastName;
	}



	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getFirstName() {
		return FirstName;
	}


	public void setFirstName(String firstName) {
		FirstName = firstName;
	}


	public String getLastName() {
		return LastName;
	}


	public void setLastName(String lastName) {
		LastName = lastName;
	}


	
}
