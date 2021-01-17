package com.bookstore.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bookstore.app.model.Book;
import com.bookstore.app.service.BookService;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;
	
	
	@GetMapping("/allBooks")
	public String showAllBooks(Model model) {
		List<Book> listOfBooks = bookService.getAllBooks();
		model.addAttribute("listOfBooks", listOfBooks);
		return "books";
	}
	
	@GetMapping("/book/{id}")
	public String showBookDetails(Model model, @PathVariable Long id) {
		Book book = bookService.findBookById(id);
		model.addAttribute("book", book);
		return "book_details";
	}
}
