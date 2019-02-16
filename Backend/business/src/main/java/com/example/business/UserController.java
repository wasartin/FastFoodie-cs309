package com.example.business;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.business.data.entities.User;
import com.example.business.data.repositories.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping(method = RequestMethod.GET, path = "/users")
	public List<User> getAllUsers(){
		return (List<User>) userRepository.findAll();
	}
	
	@GetMapping("/users-all")
	public Iterable<User> quickLook() {
		return userRepository.findAll();
	}
}