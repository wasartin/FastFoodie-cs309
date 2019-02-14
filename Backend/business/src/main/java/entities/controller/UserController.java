package entities.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import entities.data.User;
import entities.repository.UserRepository;

@RestController
@RequestMapping(path="/users")
public class UserController {
	@Autowired
	private final UserRepository userRepo;
	
	UserController(UserRepository userRepo){
		this.userRepo = userRepo;
	}
	
	@GetMapping(path="/all")
	public List<User> getAllUsers(){
		Iterable<User> iter = getAllUsersHelp();
		List<User> userList = new ArrayList<>();
		iter.forEach(userList::add);//first argument is the target, second is the action
		return userList;
	}
		
	@RequestMapping(method = RequestMethod.POST, value="/create")
	public Map addUser(@RequestBody User user) {
		Map<String, Object> response = new HashMap<>();
		try {
			//check if user is in DB?
			userRepo.save(user);
			response.put("status", 200);
			response.put("message", HttpStatus.OK);
		}catch (IllegalArgumentException e) {
			response.put("status", 400);
			response.put("error", HttpStatus.BAD_REQUEST);
			response.put("message", "User might already exist, or your fields are incorrect, double check your request");
		}catch (Exception e) {
			response.put("status", 500);
			response.put("error", HttpStatus.INTERNAL_SERVER_ERROR);
			response.put("message", "Server might be down now. Try again");
		}
		return response;//auto fill?
	}
	


	private Iterable<User> getAllUsersHelp() {
		return userRepo.findAll();
	}

}
