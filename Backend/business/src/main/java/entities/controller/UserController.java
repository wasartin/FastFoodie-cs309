package entities.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import entities.data.User;
import entities.repository.UserRepository;
import entities.service.UserService;

//@RequestMapping("users")
@RestController
public class UserController {
	private final UserRepository userRepo;
	
	private UserService userService;
	
	UserController(UserRepository userRepo){
		this.userRepo = userRepo;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createUser(@RequestBody User user){
		Map<String, Object> response = new HashMap<>();
		//Optional<User> temp = userRepo.findByEmail(user.getEmail());
		//TODO
		return response;
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
