package com.example.business.data.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.business.data.entities.User;
import com.example.business.data.repositories.UserRepository;

/**
 * Verbs are bad for api, but I am only doing it temporarily
 * @author watis
 *
 */
@RestController
@RequestMapping(value="/users")
public class UserController {
	
	private final String JSON_OBJECT_RESPONSE_KEY1 = "data";
	@SuppressWarnings("unused")
	private final String JSON_OBJECT_RESPONSE_KEY2 = "info";
	@SuppressWarnings("unused")
	private final String JSON_OBJECT_RESPONSE_KEY3 = "favoritesList";
	
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(method = RequestMethod.GET, path = "/{user_email}")
	@ResponseBody
	public Optional<User> getUser(@PathVariable String user_email){
		return userRepository.findById(user_email);
	}

	//TODO change the mapping here, as well as method name. the json one should have '/json/' in the url
	// 	Once 'getAllUsersJSONObject' method can be correctly parsed by 
	//		front end, this will be deleted
	@GetMapping("old/all")
	public Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}

	/**
	 * TODO: The User's favorites list will be added here as well. It will be the second key in this response=
	 * @param user_email
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, path = "json/{user_email}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONObject getUserJSONObject(@PathVariable String user_email) {//TODO will just be changed to getUser once conversion is complete
		Optional<User> temp = userRepository.findById(user_email);
		JSONObject response = new JSONObject();
		response.put(JSON_OBJECT_RESPONSE_KEY1, temp.get());
		return response;
	}
	
	/**
	 * This private helper method is used to pull all the users from the Data base so it is easier to parse into a JSONObject
	 * @return List<User>
	 */
	private List<User> getUsers(){
		Iterable<User> uIters = userRepository.findAll();
		List<User> uList = new ArrayList<User>();
		uIters.forEach(uList::add);
		return uList;
	}
	
	/**
	 * This is a private helper method that parses the Backend's version of a user into a JSON object, 
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private JSONObject parseUserIntoJSONObject(User user) {
		final String USER_EMAIL_KEY = "user_email";
		final String USER_TYPE_KEY = "user_type";
		JSONObject userAsJSONObj = new JSONObject();
		userAsJSONObj.put(USER_EMAIL_KEY, user.getUser_email());
		userAsJSONObj.put(USER_TYPE_KEY, user.getUser_type());
		return userAsJSONObj;
	}

	/**
	 * 
	 * @return JSONObject that has key1-> "Users": value1->JSONArray of users in System
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllUsersJSONObject()  {
		JSONObject toReturn = new JSONObject();
		String key1 = JSON_OBJECT_RESPONSE_KEY1;
		JSONArray listOfUsers = new JSONArray();
		List<User> uList = getUsers();
		for(User user : uList) {
			listOfUsers.add(parseUserIntoJSONObject(user));
		}
		toReturn.put(key1, listOfUsers);
		return toReturn;
	}

	/**
	 * Currently just takes user Object. Might need to be a JSONObject I parse if more info is required.
	 * @param newUser
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONObject createUser(@RequestBody User newUser) {
		JSONObject response;
		try {
			if(userRepository.existsById(newUser.getUser_email())) {//User already exists
				throw new IllegalArgumentException();
			}
			userRepository.save(newUser);
			response = generateResponse(204, HttpStatus.OK, "User has been created");
		}catch (IllegalArgumentException e) {
			response = generateResponse(400, HttpStatus.BAD_REQUEST, "User might already exist, or your fields are incorrect, double check your request");
		}catch (Exception e) {
			response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
		}
		return response;
	}
	
	/**
	 * Deletes the user given their unique id
	 * @param user_email
	 * @return
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{user_email}", produces = MediaType.APPLICATION_JSON_VALUE) 
	@ResponseBody
	public JSONObject deleteUser(@PathVariable String user_email) {
		JSONObject response;
		try {
			if(!userRepository.existsById(user_email)) {//Checks to see if User is even in the DB
				throw new IllegalArgumentException();
			}
			//User user = userRepository.findByID(user_email);
			//userRepository.delete(user);
			userRepository.deleteById(user_email);
			response = generateResponse(204, HttpStatus.OK, "User has been deleted");
		}catch (IllegalArgumentException e) {
			response = generateResponse(400, HttpStatus.BAD_REQUEST, "Could not find that user in the database, or your fields are incorrect, double check your request");
		}catch (Exception e) {
			response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
		}
		return response;
	}
	
	/**
	 * JSONObject. 1st key is old
	 * 2nd key is new
	 * PUT is for update
	 * TODO: When a user wants to change emails, will need to switch over favorites list
	 * @param userToEdit
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "/edit/{user_email}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONObject editUser(@RequestBody User newUserInfo, @PathVariable String user_email) {
		JSONObject response;
		try {
			if(!userRepository.existsById(user_email)) {//pretty sure that is how I want to do this.
				throw new IllegalArgumentException();
			}
			userRepository.save(newUserInfo);//this will edit the user
			response = generateResponse(200, HttpStatus.OK, "User has been edited");
		}catch (IllegalArgumentException e) {
			response = generateResponse(400, HttpStatus.BAD_REQUEST, "Could not find that user in the database, or your fields are incorrect, double check your request");
		}catch (Exception e) {
			response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
		}
		return response;
	}
	
	/**
	 * Made Public for testing
	 * @param status
	 * @param input
	 * @param message
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONObject generateResponse(int status, HttpStatus input, String message) {
		JSONObject response = new JSONObject();
		response.put("status", status);
		response.put("HttpStatus", input);
		response.put("message", message);
		return response;
	}
}