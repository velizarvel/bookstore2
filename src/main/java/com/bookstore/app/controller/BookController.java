package com.bookstore.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookstore.app.model.Book;
import com.bookstore.app.model.Genre;
import com.bookstore.app.service.BookService;
import com.bookstore.app.service.GenreService;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private GenreService genreService;
	
	
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
		return "bookDetails";
	}
	
	@GetMapping("/book/create")
	public String prepareBookCreate(Model model) {
		Book book = new Book();
		List<Genre> allGenres = genreService.getAllGenres();
		model.addAttribute("book", book);
		model.addAttribute("allGenres", allGenres);
		return "bookCreate";
	}
	
	@PostMapping("/book/created")
	public String createBook(Book book, @RequestParam(name="genreId", required=false) Long[] genreIds, HttpServletRequest request) {
		for (Long id : genreIds) {
			book.getGenres().add(genreService.findGenreById(id));
		}
		double averageMarkBook = (Math.random() * 10 + 1);
		averageMarkBook = Math.round(averageMarkBook*100.0)/100.0;
		book.setAverageMarkBook(averageMarkBook);
		bookService.createBook(book);
		return "redirect:" + request.getScheme() + ":/allBooks";
	}
	
	@GetMapping("/book/update/{id}")
	public String prepareBookUpdate(Model model, @PathVariable Long id) {
		Book book = bookService.findBookById(id);
		List<Genre> allGenres = genreService.getAllGenres();
		model.addAttribute("book", book);
		model.addAttribute("allGenres", allGenres);
		return "bookUpdate";
	}
	
	@PostMapping("/book/update")
	public String updateBook(Book book, @RequestParam(name="genreId", required=false) Long[] genreIds, HttpServletRequest request) {
		for (Long id : genreIds) {
			book.getGenres().add(genreService.findGenreById(id));
		}
		bookService.updateBook(book);
		return "redirect:" + request.getScheme() + ":/book/"+book.getId();
	}
}
