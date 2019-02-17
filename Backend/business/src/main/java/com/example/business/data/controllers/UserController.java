package com.example.business.data.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.business.data.entities.User;
import com.example.business.data.repositories.UserRepository;

@RestController
@RequestMapping(value="/users")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping(method = RequestMethod.GET, path = "/{user_email}")
	@ResponseBody
	public Optional<User> getUser(@PathVariable String user_email){
		return userRepository.findById(user_email);
	}
	
	@GetMapping("/all")
	public Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/create")
	@ResponseBody
	private Map<String,Object> createUser(@RequestBody User newUser) {
		HashMap<String,Object> response = new HashMap<>();
		try {
			if(userRepository.findById(newUser.getEmail()) == null)//Checks to see if User is already in DB
				throw new IllegalArgumentException();
			userRepository.save(newUser);
			response.put("status", 200);
			response.put("message", HttpStatus.OK);
		}catch (IllegalArgumentException e) {
			response.put("status", 400);
			response.put("error", HttpStatus.BAD_REQUEST);
			response.put("message", "User might already exists, or your fields are incorrect, double check your request");
		}catch (Exception e) {
			response.put("status", 500);
			response.put("error", HttpStatus.INTERNAL_SERVER_ERROR);
			response.put("message", "Server might be down now. Try again");
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/delete")
	@ResponseBody
	private Map<String,Object> deleteUser(@RequestBody User userSelectedToPerish) {
		HashMap<String,Object> response = new HashMap<>();
		try {
			if(userRepository.findById(userSelectedToPerish.getEmail()) != null)//Checks to see if User is even in the DB
				throw new IllegalArgumentException();
			userRepository.deleteById(userSelectedToPerish.getEmail());
			response.put("status", 200);
			response.put("message", HttpStatus.OK);
		}catch (IllegalArgumentException e) {
			response.put("status", 400);
			response.put("error", HttpStatus.BAD_REQUEST);
			response.put("message", "Could not find that user in the database, or your fields are incorrect, double check your request");
		}catch (Exception e) {
			response.put("status", 500);
			response.put("error", HttpStatus.INTERNAL_SERVER_ERROR);
			response.put("message", "Server might be down now. Try again");
		}
		return response;
	}
}