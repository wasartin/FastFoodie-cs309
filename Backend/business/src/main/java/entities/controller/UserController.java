package entities.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import entities.data.User;
import entities.repository.UserRepository;
import entities.service.UserService;

@RequestMapping("users")
@RestController
public class UserController {
	@SuppressWarnings("unused")
	@Autowired
	private final UserRepository userRepo;
	
	private UserService userService;
	
	UserController(UserRepository userRepo){
		this.userRepo = userRepo;
	}
	
	@RequestMapping("/users")
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}
		
	@RequestMapping(method = RequestMethod.POST, value="/users")
	public void addUser(@RequestBody User user) {
		userService.addUser(user);
	}
	

		
}
