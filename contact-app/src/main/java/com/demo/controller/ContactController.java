package com.demo.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.domain.Contact;
import com.demo.repository.ContactRepository;

@Controller
@RequestMapping("/")
public class ContactController {

	private ContactRepository contactRepository;
	
	public ContactController(ContactRepository contactRepository) {
		// TODO Auto-generated constructor stub
		this.contactRepository = contactRepository; 
	}
	
	@GetMapping
	public String home(Model model) {
		model.addAttribute("contacts", contactRepository.findAll());
		return "homeView";
	}
	
	@GetMapping("add")
	public String showAddContact(Contact contact) {
		return "addView";
	}
	
	@PostMapping("add")
	public String addContact(@Valid Contact contact, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "addView";
		} 
		contactRepository.save(contact);
		return "redirect:/";
	}
	
	@GetMapping("edit/{id}")
	public String editContact(@PathVariable("id") long id, Model model) throws IllegalAccessException {
		Contact contact = contactRepository.findById(id).orElseThrow( () -> new IllegalAccessException("Invalid id: " + id));
		model.addAttribute("contact", contact);
		return "editView";
	}
	
	@PostMapping("update/{id}")
	public String updateContact(@PathVariable("id") long id, @Valid Contact contact, BindingResult bindingResult, Errors errors, Model model) {
		if(bindingResult.hasErrors()) {
			return "editView";
		}
		contactRepository.save(contact);
		model.addAttribute("contacts", contactRepository.findAll());
		return "homeView";
	}
	
	@GetMapping("delete/{id}")
	public String deleteContact(@PathVariable("id") long id, Model model) throws IllegalAccessException {
		Contact contact = contactRepository.findById(id).orElseThrow(() -> new IllegalAccessException("Invalid id: " + id));
		contactRepository.delete(contact);
		model.addAttribute("contacts", contactRepository.findAll());
		return "homeView";
	}
}
