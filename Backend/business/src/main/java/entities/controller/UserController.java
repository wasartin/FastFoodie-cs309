package entities.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import entities.data.User;
import entities.service.UserService;

@RestController
@RequestMapping(path="/users")
public class UserController {
	@Autowired
	private final UserService userService;
	
	UserController(UserService userService){
		this.userService = userService;
	}
	
	@GetMapping(path="/all")
	public List<User> getAllUsers(){
		Iterable<User> iter = userService.getAllUsers();
		List<User> userList = new ArrayList<>();
		iter.forEach(userList::add);//first argument is the target, second is the action
		return userList;
	}
		
	@RequestMapping(method = RequestMethod.POST, value="/create")
	public void addUser(@RequestBody User user) {
		Map<String, Object> response = userService.create(user);
	}
}
