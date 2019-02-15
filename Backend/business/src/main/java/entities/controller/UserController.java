package entities.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import entities.data.User;
import entities.repository.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	private final UserRepository userRepository;
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	public UserController(UserRepository userRepoistory){
		this.userRepository = userRepoistory;
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/users")
	public List<User> getAllUsers(){
		return (List<User>) userRepository.findAll();
	}
	
	@GetMapping("/users-all")
	public Iterable<User> quickLook() {
		return userRepository.findAll();
	}
}