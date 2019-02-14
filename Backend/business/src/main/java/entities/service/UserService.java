package entities.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import entities.data.User;
import entities.repository.UserRepository;


@Service
@ComponentScan("entities")
public class UserService {
	@Autowired
	UserRepository userRepo;
	
	//TODO
	public Map<String, Object> create(User newUser) {
		Map<String, Object> response = new HashMap<>();
		try {
			//check if user is in DB?
			userRepo.save(newUser);
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

	public Iterable<User> getAllUsers() {
		return userRepo.findAll();
	}

}