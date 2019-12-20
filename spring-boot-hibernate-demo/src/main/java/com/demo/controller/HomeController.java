package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.modal.Book;
import com.demo.service.BookService;

@RestController
@RequestMapping("/")
public class HomeController {

	@Autowired
	private BookService bookService;
	
	@GetMapping
	public List<Book> showBook(Model model){
		return bookService.findAll();
	}
}
