package com.demo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.modal.Book;
import com.demo.service.BookService;

import junit.framework.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringBootHibernateDemoApplicationTests {

	@Autowired
	private BookService bookService;
	
	@Test
	void contextLoads() {
		List<Book> list = bookService.findAll();
		Assert.assertEquals(list.size(), 3);
		for(Book book:list) {
			System.out.println(book.getName());
		}
	}

}
