package com.oussema.digitalbookmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oussema.digitalbookmanagement.models.Doc;

public interface  DocRepository extends JpaRepository<Doc, Integer>{

	
	
}
