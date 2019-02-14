package com.example.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entities.data.User;
import entities.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepo;
	
	//TODO
	public User create(User newUser) {
		return newUser;
		
	}
}
