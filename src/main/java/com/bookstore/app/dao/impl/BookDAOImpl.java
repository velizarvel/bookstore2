package com.bookstore.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.app.dao.BookDAO;
import com.bookstore.app.dao.GenreDAO;
import com.bookstore.app.model.Book;
import com.bookstore.app.model.Genre;

@Repository
public class BookDAOImpl implements BookDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private GenreDAO genreDAO; 

	private class BookZanrRowCallBackHandler implements RowCallbackHandler {

		private Map<Long, Book> books = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet rs) throws SQLException {
			int index = 1;
			
			Long bookId = rs.getLong(index++);
			Book book = books.get(bookId);
			if(book == null) {
				book = new Book();
				book.setId(bookId);
				book.setIsbn(rs.getString(index++));
				book.setNameOfBook(rs.getString(index++));
				book.setYearOfPublication(rs.getInt(index++));
				book.setShortDescription(rs.getString(index++));
				book.setPrice(rs.getDouble(index++));
				book.setPrintingHouse(rs.getString(index++));
				book.setAuthor(rs.getString(index++));
				book.setNumberPages(rs.getInt(index++));
				book.setBookCover(rs.getString(index++));
				book.setLetter(rs.getString(index++));
				book.setLanguage(rs.getString(index++));
				book.setAverageMarkBook(rs.getDouble(index++));

				book.setQuantity(rs.getInt(index++));
				books.put(bookId, book);
			} else {
				index = 15;
			}
			
			Long genreId = rs.getLong(index++);
			String genreName = rs.getString(index++);
			String genreDescription = rs.getString(index++);
			Genre genre = new Genre();
			genre.setId(genreId);
			genre.setName(genreName);
			genre.setDescription(genreDescription);
			book.getGenres().add(genre);
		}

		public List<Book> getAllBooks() {
			return new ArrayList<>(books.values());
		}
	
	}

	@Override
	public List<Book> getBooks() {
		String sql = 
				"SELECT b.*, g.* FROM book b " + 
				"LEFT JOIN bookgenre bg ON bg.bookid = b.id " + 
				"LEFT JOIN genre g ON bg.genreid = g.id " + 
				"ORDER BY b.id";

		BookZanrRowCallBackHandler rowCallbackHandler = new BookZanrRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getAllBooks();
	}

	@Override
	public Book findBookById(Long id) {
		String sql = "SELECT b.*, g.* FROM book b " + "LEFT JOIN bookgenre bg ON bg.bookid = b.id "
				+ "LEFT JOIN genre g ON bg.genreid = g.id " + "WHERE b.id = ? " + "ORDER BY b.id";

		BookZanrRowCallBackHandler rowCallbackHandler = new BookZanrRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		Book book =  rowCallbackHandler.getAllBooks().get(0);

		return book;
	}
	
	@Transactional
	@Override
	public void createBook(Book book) {
		
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO book (isbn, name_of_book, printing_house, author, year_of_publication, "
						+ "short_description, price, number_pages, bookCover, letter, languages, quantity, average_mark_book)"
						+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";	
				int index=1;
				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(index++,book.getIsbn());
				preparedStatement.setString(index++,book.getNameOfBook());
				preparedStatement.setString(index++,book.getPrintingHouse());
				preparedStatement.setString(index++,book.getAuthor());
				preparedStatement.setInt(index++,book.getYearOfPublication());
				preparedStatement.setString(index++,book.getShortDescription());
				preparedStatement.setDouble(index++,book.getPrice());
				preparedStatement.setInt(index++,book.getNumberPages());
				preparedStatement.setString(index++,book.getBookCover());
				preparedStatement.setString(index++,book.getLetter());
				preparedStatement.setString(index++,book.getLanguage());
				preparedStatement.setInt(index++,book.getQuantity());
				preparedStatement.setDouble(index++,book.getAverageMarkBook());
				return preparedStatement;
			}
		};
		
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(preparedStatementCreator, keyHolder);

		
//		jdbcTemplate.update(book.getIsbn(), book.getNameOfBook(), book.getPrintingHouse(), book.getAuthor(),
//				book.getYearOfPublication(), book.getShortDescription(), book.getPrice(), book.getNumberPages(),
//				book.getBookCover(), book.getLetter(), book.getLanguage(), book.getQuantity(),book.getAverageMarkBook());
		
		
		
		for (Genre genre : book.getGenres()) {
			String sql = "INSERT INTO bookgenre(bookid, genreid) "
					+ "VALUES('" + keyHolder.getKey() +"','"+ genre.getId() +"')";
			jdbcTemplate.update(sql);
		}
	}

	@Transactional
	@Override
	public void updateBook(Book book) {

		String sql = "UPDATE book SET isbn = ?, name_of_book = ?, printing_house = ?,author = ?, year_of_publication = ?, "
				+ "short_description = ?, price=?, number_pages = ?, bookCover = ?, letter=?, languages =?, quantity =? WHERE id = ?";	
		jdbcTemplate.update(sql, book.getIsbn(), book.getNameOfBook(), book.getPrintingHouse(), book.getAuthor(),
				book.getYearOfPublication(), book.getShortDescription(), book.getPrice(), book.getNumberPages(),
				book.getBookCover(), book.getLetter(), book.getLanguage(), book.getQuantity(), book.getId());
		
		sql = "DELETE from bookgenre WHERE bookId=?";
		jdbcTemplate.update(sql,book.getId());
		
		for (Genre genre : book.getGenres()) {
			sql = "INSERT INTO bookgenre(bookid, genreid) "
					+ "VALUES('" + book.getId() +"','"+ genre.getId() +"')";
			jdbcTemplate.update(sql);
		}
	}
}
