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
	
	@RequestMapping(method = RequestMethod.GET, path = "/{user_email}", produces = MediaType.APPLICATION_JSON_VALUE)
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
	
	/**
	 * 
	 * @return JSONObject that has key1-> "Users": value1->JSONArray of users in System
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value ="/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllUsersJSONObject()  {
		JSONObject toReturn = new JSONObject();
		String key1 = "Users";//May become a final
		JSONArray listOfUsers = new JSONArray();
		List<User> uList = getUsers();
		for(int i = 0; i < uList.size(); i++) {
			JSONObject temp = new JSONObject();
			temp.put("email", uList.get(i).getEmail());
			temp.put("role", uList.get(i).getUserType());
			listOfUsers.add(temp);
		}
		toReturn.put(key1, listOfUsers);
		return toReturn;
	}
	
	/**TODO finish
	 * 
	 * @return JSONObject that has key1-> "Users": value1->JSONArray of users in System
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value ="/all/registered", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllRegisteredUsers()  {
		JSONObject toReturn = new JSONObject();
		String key1 = "Users";//May become a final
		JSONArray listOfUsers = new JSONArray();
		List<User> uList = getUsers();
		for(int i = 0; i < uList.size(); i++) {
			JSONObject temp = new JSONObject();
			temp.put("email", uList.get(i).getEmail());
			temp.put("role", uList.get(i).getUserType());
			if(temp.get("role").equals("registered")) {
				listOfUsers.add(temp);
			}
		}
		toReturn.put(key1, listOfUsers);
		return toReturn;
	}
	
	/**TODO finish
	 * 
	 * @return JSONObject that has key1-> "Users": value1->JSONArray of users in System
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value ="/all/admin", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllAdminUsers()  {
		JSONObject toReturn = new JSONObject();
		String key1 = "Users";//May become a final
		JSONArray listOfUsers = new JSONArray();
		List<User> uList = getUsers();
		for(int i = 0; i < uList.size(); i++) {
			JSONObject temp = new JSONObject();
			temp.put("email", uList.get(i).getEmail());
			temp.put("role", uList.get(i).getUserType());
			if(temp.get("role").equals("admin")) {
				listOfUsers.add(temp);
			}
		}
		toReturn.put(key1, listOfUsers);
		return toReturn;
	}

	/**
	 * Currently just takes user Object. Will might need to be a JSONObject I parse if more info is required.
	 * @param newUser
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	private Map<String,Object> createUser(@RequestBody User newUser) {
		HashMap<String,Object> response = new HashMap<>();
		try {
			if(userRepository.existsById(newUser.getEmail())) {//User already exists
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
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE) 
	@ResponseBody
	private Map<String,Object> deleteUser(@RequestBody JSONObject sentJSONObj) {
		HashMap<String,Object> response = new HashMap<>();
		User selectedToPerish = new User((String)sentJSONObj.get("email"), (String)sentJSONObj.get("userType"));
		try {
			if(!userRepository.existsById(selectedToPerish.getEmail())) {//Checks to see if User is even in the DB
				throw new IllegalArgumentException();
			}
			userRepository.deleteById(selectedToPerish.getEmail());
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
	
	/**TODO finish
	 * The argument given will simply be the way the new user wants to be changed.
	 * Might need to implement userId****
	 * PUT is for updating
	 * @param userToEdit
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "/edit", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	private Map<String,Object> editUser(@RequestBody JSONObject userToEdit) {
		HashMap<String,Object> response = new HashMap<>();
		User toEdit = new User((String)userToEdit.get("email"), (String)userToEdit.get("userType"));
		try {
			if(!userRepository.existsById(toEdit.getEmail())) {//pretty sure that is how I want to do this.
				throw new IllegalArgumentException();
			}
			
			userRepository.save(toEdit);//this will edit the user
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