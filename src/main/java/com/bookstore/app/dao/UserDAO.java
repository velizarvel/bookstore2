package com.bookstore.app.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.app.dao.impl.GenreDAOImpl;
import com.bookstore.app.model.Genre;
import com.bookstore.app.model.User;


public interface UserDAO {

	public List<User> getAllUsers();
	
	public User findUserById(Long userId);
	
	public User findUserByUsernameAndPassword(String username, String password);
	
	public void createUser(User user);
}
