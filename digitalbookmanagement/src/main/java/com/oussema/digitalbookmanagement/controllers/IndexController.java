package com.oussema.digitalbookmanagement.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.oussema.digitalbookmanagement.models.Role;
import com.oussema.digitalbookmanagement.models.Utilisateur;
import com.oussema.digitalbookmanagement.repositories.UtilisateurRepository;
import com.oussema.digitalbookmanagement.services.UserService;

@Controller
public class IndexController {

	@Autowired
	private UserService userService;

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/inscription")
	public String register(Model model) {
		model.addAttribute("utilisateur", new Utilisateur());
		model.addAttribute("role", new com.oussema.digitalbookmanagement.models.Role());
		return "inscription";
	}

	@PostMapping("/inscription")
	public ModelAndView registerUser(@Valid Utilisateur utilisateur, BindingResult bindingResult,
			ModelAndView modelAndView) {

		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("inscription");
		}

		if (userService.isPresent(utilisateur.getEmail())) {
			modelAndView.addObject("present", true);

			modelAndView.setViewName("inscription");

		}
		
		
		if (userService.isPresentt(utilisateur.getUsername())) {
			modelAndView.addObject("present", true);

			modelAndView.setViewName("inscription");

		}
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		utilisateur.setPassword(encoder.encode(utilisateur.getPassword()));
		Role userRole = new Role("USER");
		List<Role> roles = new ArrayList<>();
		roles.add(userRole);
		utilisateur.setRoles(roles);
		utilisateurRepository.save(utilisateur);
		return modelAndView;

	}

}
