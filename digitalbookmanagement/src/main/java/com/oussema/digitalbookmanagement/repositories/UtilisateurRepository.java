package com.oussema.digitalbookmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oussema.digitalbookmanagement.models.Utilisateur;

@Repository("utilisateurRepository")
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

	Utilisateur findByUsername(String username);
	Utilisateur findByEmail(String email);

}
