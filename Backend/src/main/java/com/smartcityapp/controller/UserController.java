package com.smartcityapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartcityapp.model.User;
import com.smartcityapp.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")

public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<Map<String, String>> signup(@Valid @RequestBody User user) {
		Map<String, String> response = new HashMap<>();
		try {
			if (userService.userExists(user)) {
				response.put("message", "User already exists!");
				return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
			}

			String result = userService.registerUser(user);
			response.put("message", result);

			if (result.equals("Registration successful!")) {
				return ResponseEntity.ok(response);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("message", "Error during registration. Please try again later.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PostMapping("/signin")
	public ResponseEntity<Map<String, String>> signin(@RequestBody Map<String, String> credentials) {
		String email = credentials.get("email");
		String password = credentials.get("password");
		return userService.authenticateUser(email, password).map(user -> {
			Map<String, String> response = new HashMap<>();
			response.put("message", "Login successful!");
			return ResponseEntity.ok(response);
		}).orElseGet(() -> {
			Map<String, String> response = new HashMap<>();
			response.put("message", "Invalid email or password!");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		});
	}

}
