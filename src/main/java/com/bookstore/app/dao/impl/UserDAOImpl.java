package com.bookstore.app.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.app.dao.UserDAO;
import com.bookstore.app.model.User;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private class UserRowMapper implements RowMapper<User> {

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			int index = 1;
			Long id = rs.getLong(index++);
			String username = rs.getString(index++);
			String password = rs.getString(index++);
			String email = rs.getString(index++);
			String firstname = rs.getString(index++);
			String lastname = rs.getString(index++);
			String address = rs.getString(index++);
			String telephoneNumber = rs.getString(index++);
			String role = rs.getString(index++);
			Date date = rs.getDate(index++);
			Timestamp timestamp = new Timestamp(date.getTime());
			LocalDateTime registrationTime = timestamp.toLocalDateTime();
			User user = new User();
			user.setId(id);
			user.setUsername(username);
			user.setPassword(password);
			user.setEmail(email);
			user.setFirstname(firstname);
			user.setLastname(lastname);
			user.setAddress(address);
			user.setTelephoneNumber(telephoneNumber);
			user.setRole(role);
			user.setRegistrationTime(registrationTime);
			return user;
		}

	}

	@Override
	public List<User> getAllUsers() {
		String sql = "SELECT * FROM user";
		return jdbcTemplate.query(sql, new UserRowMapper());
	}

	@Override
	public User findUserById(Long userId) {
		String sql = "SELECT * FROM user WHERE id=?";
		return jdbcTemplate.query(sql, new UserRowMapper(), userId).get(0);
	}

	@Override
	public User findUserByUsernameAndPassword(String username, String password) {
		String sql = "SELECT * FROM user WHERE username=? and user_password=?";
		User user = jdbcTemplate.query(sql, new UserRowMapper(), username, password).get(0);
		if (user != null) {
			return user;
		}
		return null;
	}

	@Transactional
	@Override
	public void createUser(User user) {

		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO user (username, user_password, email, firstname, lastname, "
						+ "address, telephone_number, user_role, registration_time)" + "VALUES (?,?,?,?,?,?,?,?,?)";
				int index = 1;
				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(index++, user.getUsername());
				preparedStatement.setString(index++, user.getPassword());
				preparedStatement.setString(index++, user.getEmail());
				preparedStatement.setString(index++, user.getFirstname());
				preparedStatement.setString(index++, user.getLastname());
				preparedStatement.setString(index++, user.getAddress());
				preparedStatement.setString(index++, user.getTelephoneNumber());
				preparedStatement.setString(index++, user.BUYER);
				LocalDateTime registrationTime = LocalDateTime.now();
				Timestamp timestamp = Timestamp.valueOf(registrationTime);
				preparedStatement.setDate(index++,new Date(timestamp.getTime()));

				return preparedStatement;
			}
		};
		
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(preparedStatementCreator, keyHolder);

	}

}
