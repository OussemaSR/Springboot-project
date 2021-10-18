package com.oussema.digitalbookmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oussema.digitalbookmanagement.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
