package com.oussema.digitalbookmanagement.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.oussema.digitalbookmanagement.models.Role;
import com.oussema.digitalbookmanagement.models.Utilisateur;
import com.oussema.digitalbookmanagement.repositories.UtilisateurRepository;

@Service
public class UserService {
	
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	

	public void createUser(Utilisateur utilisateur) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		utilisateur.setPassword(encoder.encode(utilisateur.getPassword()));
		Role userRole = new Role("USER");
		List<Role> roles = new ArrayList<>();
		roles.add(userRole);
		utilisateur.setRoles(roles);
		utilisateurRepository.save(utilisateur);
	}

	public Boolean isPresent(String email) {
		Utilisateur u = utilisateurRepository.findByEmail(email);
		if (u != null)
			return true;
		return false;
	}

	public Boolean isPresentt(String username) {
		Utilisateur u = utilisateurRepository.findByUsername(username);
		if (u != null)
			return true;
		return false;
	}
	public Utilisateur findByUsername(String username) {
		return utilisateurRepository.findByUsername(username);
	}

	public Object findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
