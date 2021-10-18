package com.oussema.digitalbookmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oussema.digitalbookmanagement.models.Author;

public interface AuthorRepository extends JpaRepository<Author,Long> {

}
