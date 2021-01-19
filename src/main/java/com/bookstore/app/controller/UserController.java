package com.bookstore.app.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bookstore.app.model.User;
import com.bookstore.app.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ServletContext servletContext;
	private String baseURL; 

	@PostConstruct
	public void init() {	
		baseURL = servletContext.getContextPath() + "/allBooks";			
	}

	@GetMapping("/")
	public String homePage(HttpServletRequest request) {
		return "redirect:" + request.getScheme() + ":/login";
	}
	
	@GetMapping("/allUsers")
	public String showAllUsers(Model model) {
		List<User> allUsers = userService.getAllUsers();
		model.addAttribute("allUsers", allUsers);
		return "users";
	}
	
	@GetMapping("/registration")
	public String prepareRegistrationForm(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "registration";
	}
	
	@PostMapping("/registration")
	public String registerUser(User user, HttpServletRequest request) {
		userService.createUser(user);
		return "redirect:" + request.getScheme() + ":/login";
	}
	
	@GetMapping("/login")
	public String prepareLogin(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "login";
	}
	
	@PostMapping(value="/login")
	public ModelAndView checkLogin(@RequestParam String username, @RequestParam String password, 
			HttpSession session, HttpServletResponse response) throws IOException {
		try {
			User currentUser = userService.findUserByUsernameAndPassword(username, password);
			if (currentUser == null) {
				throw new Exception("Incorrect username or password");
			}			
			session.setAttribute("currentUser", currentUser);
			
			response.sendRedirect(baseURL);
			return null;
		} catch (Exception ex) {
			
			String message = "Bad credentials!";

			ModelAndView loginPage = new ModelAndView("login");
			loginPage.addObject("message", message);

			return loginPage;
		}
	}
	
}
