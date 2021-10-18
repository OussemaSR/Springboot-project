package com.oussema.digitalbookmanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.oussema.digitalbookmanagement.models.Utilisateur;
import com.oussema.digitalbookmanagement.repositories.UtilisateurRepository;
import com.oussema.digitalbookmanagement.security.UserPrincipal;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private UtilisateurRepository utilisateurRepository ;
	
		
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Utilisateur utilisateur = utilisateurRepository.findByEmail(email);
		    if (utilisateur==null) {
			throw new  UsernameNotFoundException("utilisateur introuvable");
			
			
		                }
		
		return new UserPrincipal(utilisateur);
	}

	
        
	}


