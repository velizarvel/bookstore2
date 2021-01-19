package com.bookstore.app.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User {
	
	public final String BUYER = "buyer";
	public final String ADMIN = "admin";
	
    private Long id;

    private String username;

    private String password;

    private String email;
    
    private String firstname;
    
    private String lastname;

    private String address;
    
    private String telephoneNumber;
    
    private String role;
    
    private LocalDateTime registrationTime;
    
	private List<Book> shoppingCartBooks = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Book> getShoppingCartBooks() {
		return shoppingCartBooks;
	}

	public void setShoppingCartBooks(List<Book> shoppingCartBooks) {
		this.shoppingCartBooks = shoppingCartBooks;
	}

	public LocalDateTime getRegistrationTime() {
		return registrationTime;
	}

	public void setRegistrationTime(LocalDateTime registrationTime) {
		this.registrationTime = registrationTime;
	}
	
	

}
