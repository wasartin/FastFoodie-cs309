package entities.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import entities.data.User;
import entities.repository.UserRepository;

@RequestMapping("users")
@RestController
public class UserController {
	@Autowired
	private final UserRepository userRepo;
	
	UserController(UserRepository userRepo){
		this.userRepo = userRepo;
	}
	
	//@GetMapping("users")
	//List<User> get(){
	//	return userService.getAll();
	//}
	
		
	

		
}
