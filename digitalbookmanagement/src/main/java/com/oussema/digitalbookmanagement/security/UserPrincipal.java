package com.oussema.digitalbookmanagement.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.oussema.digitalbookmanagement.models.Role;
import com.oussema.digitalbookmanagement.models.Utilisateur;
import com.oussema.digitalbookmanagement.repositories.UtilisateurRepository;


public class UserPrincipal implements UserDetails {

	@Autowired
	UtilisateurRepository utilisateurRepository;

	private Utilisateur utilisateur;
	
	public UserPrincipal(Utilisateur utilisateur) {

		this.utilisateur = utilisateur;
		
	}
	

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Role> roles = utilisateur.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
         
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
         
        return authorities;
    }
	

	@Override
	public String getPassword() {
		return utilisateur.getPassword();
	}

	@Override
	public String getUsername() {
		return utilisateur.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
