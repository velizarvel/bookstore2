package com.bookstore.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.app.dao.BookDAO;
import com.bookstore.app.model.Book;
import com.bookstore.app.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookDAO bookDAO;
	
	@Override
	public List<Book> getAllBooks() {
		return bookDAO.getBooks();
	}

	@Override
	public Book findBookById(Long id) {
		return bookDAO.findBookById(id);
	}
	
	@Override
	public void createBook(Book book) {
		bookDAO.createBook(book);		
	}

	@Override
	public void updateBook(Book book) {
		bookDAO.updateBook(book);	
	}
	
	

}
