package com.oussema.digitalbookmanagement.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.oussema.digitalbookmanagement.models.Doc;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.oussema.digitalbookmanagement.models.Book;
import com.oussema.digitalbookmanagement.models.Utilisateur;
import com.oussema.digitalbookmanagement.repositories.BookRepository;
import com.oussema.digitalbookmanagement.repositories.DocRepository;
import com.oussema.digitalbookmanagement.repositories.UtilisateurRepository;
import com.oussema.digitalbookmanagement.services.UserService;

@Controller
public class BookController {
	
	@Autowired
	private UtilisateurRepository  utilisateurRepository;

	@Autowired
	private UserService userService;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private DocRepository docRepository ;
	
	
	//ajout d'un live
	
	@GetMapping("/addbook/{userId}")

	public String addAnnonce(@PathVariable Long userId, Model model, Utilisateur utilisateur) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String currentPrincipalName = authentication.getName();

		model.addAttribute("principal", utilisateurRepository.findByUsername(currentPrincipalName));

		
		
		model.addAttribute("user", utilisateurRepository.findById(userService.findByUsername(currentPrincipalName).getId()));

		model.addAttribute("add", new Book());

		return "addbook";
	}

	@PostMapping("/addbook/{userId}")

	public String addAnnonce(@PathVariable Long userId, @Valid Book book,BindingResult result, 
			 @RequestParam("files") MultipartFile files, Doc doc,
			
			Model model) throws NotFoundException {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String currentPrincipalName = authentication.getName();

		model.addAttribute("principal", utilisateurRepository.findByUsername(currentPrincipalName));

		
	
		// model.addAttribute("user", userService.findById(userId).get());
		return utilisateurRepository.findById(userId).map(user -> {
			String docname = files.getOriginalFilename();
			Doc docum;
			try {
				
			      
			docum = new Doc(docname, files.getContentType(), files.getBytes());

			book.setDoc(docum);
			
			book.setUtilisateur(user);
			bookRepository.save(book);
			
			} catch (Exception e) {
				// TODO: handle exception
			}
			return "redirect:/index";
		}).orElseThrow(() -> new NotFoundException());
	}
	
	
	
	//mise a jour d'un livre 
	
	

	@GetMapping("/edit/{userId}/{liveId}")
	public String showUpdateForm(@PathVariable Long userId, @PathVariable Long liveId, Model model) {
		
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String currentPrincipalName = authentication.getName();

		model.addAttribute("principal", utilisateurRepository.findByUsername(currentPrincipalName));
		
		
		model.addAttribute("add", bookRepository.findByUtilisateurId(userId));
		model.addAttribute("user", utilisateurRepository.findById(userId).get());
		model.addAttribute("item", bookRepository.findById(liveId).get());

		return "update";
	}
	
	

	@RequestMapping(value = "/update/{userId}/annonce/{livreId}", method = { RequestMethod.GET, RequestMethod.PUT })
	public String updateLive(@PathVariable Long userId, @PathVariable Long livreId, Book bookUpdated,
			Model model) throws NotFoundException {

		model.addAttribute("add", bookRepository.findByUtilisateurId(userId));
		model.addAttribute("item", bookRepository.findById(livreId));
		return bookRepository.findById(livreId).map(boook -> {
			boook.setTitre(bookUpdated.getTitre());
			boook.setIsbn(bookUpdated.getIsbn());
		
			bookRepository.save(boook);
			return "redirect:/index";
		}).orElseThrow(() -> new NotFoundException());
	}
	
	
	
	//duppression d'un livre 

	@RequestMapping(value = "/supprimer/{userId}/{livreId}", method = { RequestMethod.GET,
			RequestMethod.DELETE })
	@ResponseBody
	public String deleteAssignment(@PathVariable Long userId,

			@PathVariable Long livreId,Model model) throws NotFoundException {
		
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String currentPrincipalName = authentication.getName();

		model.addAttribute("principal", utilisateurRepository.findByUsername(currentPrincipalName));
		

		if (!utilisateurRepository.existsById(userId)) {
			throw new NotFoundException();
		}

		return bookRepository.findById(livreId).map(annonce -> {
			bookRepository.delete(annonce);
			return "redirect:/index";
		}).orElseThrow(() -> new NotFoundException());
	}

	
	//consulter les livres ;
	

	  @GetMapping("livre/{id}/pdf")
	  public void renderImageFromDB(@PathVariable Integer id, HttpServletResponse response)
	      throws IOException {
	    Doc doc = docRepository.findById(id).get();

	    if (doc.getData() != null) {
	      byte[] byteArray = new byte[doc.getData().length];
	      int i = 0;

	      for (Byte wrappedByte : doc.getData()) {
	        byteArray[i++] = wrappedByte; // auto unboxing
	      }

	      response.setContentType("image/jpeg");
	      InputStream is = new ByteArrayInputStream(byteArray);
	      IOUtils.copy(is, response.getOutputStream());
	    }
	  }
	
	//afficher tous les livres 
	
	
	@GetMapping(value={"","/index","/"})
	public String showAllBook(Model model) {
		

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String currentPrincipalName = authentication.getName();

		model.addAttribute("principal", utilisateurRepository.findByUsername(currentPrincipalName));

		
		model.addAttribute("books",bookRepository.findAll());
		
		return "index";
	}
	
	@GetMapping("livre/{livreId}")
	public String findbyid(@PathVariable Long livreId ,Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String currentPrincipalName = authentication.getName();

		model.addAttribute("principal", utilisateurRepository.findByUsername(currentPrincipalName));

		
		
		model.addAttribute("livre", bookRepository.findById(livreId).get());
		
		return "consulter";
	}
	
	
	
}

