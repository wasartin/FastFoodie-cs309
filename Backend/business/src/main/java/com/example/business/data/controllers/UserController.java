package com.example.business.data.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
	
	/*
	@RequestMapping(method = RequestMethod.GET, path = "/{user_email}")
	@ResponseBody
	public Optional<User> getUser(@PathVariable String user_email){
		return userRepository.findById(user_email);
	}
	*/
	@RequestMapping(method = RequestMethod.GET, path = "/{user_email}")
	@ResponseBody
	public JSONObject getUserJSONObject(@PathVariable String user_email) {
		Optional<User> temp = userRepository.findById(user_email);
		JSONObject toReturn = new JSONObject();
		JSONObject userInfo = new JSONObject();
		userInfo.put("role", temp.get().getUserType());
		userInfo.put("email", temp.get().getEmail());
		toReturn.put("User", userInfo);
		return toReturn;
	}
	

	private List<User> getUsers(){
		Iterable<User> uIters = userRepository.findAll();
		List<User> uList = new ArrayList<User>();
		uIters.forEach(uList::add);
		return uList;
	}
	
	//returning a JSON Obect of key = users and value = JSON array of the results
	@SuppressWarnings("unchecked")
	@RequestMapping(value ="/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllUsersJson()  {
		JSONObject toReturn = new JSONObject();
		String label = "Users";
		JSONArray listOfUsers = new JSONArray();
		List<User> uList = getUsers();
		for(int i = 0; i < uList.size(); i++) {
			JSONObject temp = new JSONObject();
			temp.put("email", uList.get(i).getEmail());
			temp.put("role", uList.get(i).getUserType());
			listOfUsers.add(temp);
		}
		toReturn.put(label, listOfUsers);
		return toReturn;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/create")
	@ResponseBody
	private Map<String,Object> createUser(@RequestBody User newUser) {
		HashMap<String,Object> response = new HashMap<>();
		try {
			if(userRepository.findById(newUser.getEmail()) != null) {//Checks to see if User is already in DB
				throw new IllegalArgumentException();
			}
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