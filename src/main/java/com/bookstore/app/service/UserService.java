package com.bookstore.app.service;

import java.util.List;

import com.bookstore.app.model.Genre;
import com.bookstore.app.model.User;

public interface UserService {
	
	public List<User> getAllUsers();
	
	public User findUserById(Long userId);
	
	public User findUserByUsernameAndPassword(String username, String password);
	
	public void createUser(User user);

}
