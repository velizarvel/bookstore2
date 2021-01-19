package com.bookstore.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.app.dao.GenreDAO;
import com.bookstore.app.dao.impl.UserDAOImpl;
import com.bookstore.app.model.Genre;
import com.bookstore.app.model.User;
import com.bookstore.app.service.GenreService;
import com.bookstore.app.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAOImpl userDAO;

	@Override
	public List<User> getAllUsers() {
		return userDAO.getAllUsers();
	}

	@Override
	public User findUserById(Long userId) {
		return userDAO.findUserById(userId);
	}

	@Override
	public User findUserByUsernameAndPassword(String username, String password) {
		return userDAO.findUserByUsernameAndPassword(username, password);
	}

	@Override
	public void createUser(User user) {
		userDAO.createUser(user);

	}

}
