package com.smartcityapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartcityapp.model.User;
import com.smartcityapp.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public boolean userExists(User user) {
		// Check if user already exists in the repository by email
		return userRepository.findByEmail(user.getEmail()).isPresent();
	}

	public String registerUser(User user) {
		// Save the new user to the repository
		try {
			userRepository.save(user);
			return "Registration successful!";
		} catch (Exception e) {
			// Handle exceptions and return appropriate message
			e.printStackTrace();
			return "Error during registration.";
		}
	}

	public Optional<User> authenticateUser(String email, String password) {
		return userRepository.findByEmail(email).filter(user -> user.getPassword().equals(password));
	}
}
