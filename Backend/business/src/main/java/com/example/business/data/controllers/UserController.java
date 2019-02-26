package com.example.business.data.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
	
	//TODO Ensure this is the key that Frontend would like to see
	private final String JSON_OBJECT_RESPONSE_KEY1 = "data";
	private final String JSON_OBJECT_RESPONSE_KEY2 = "info";
	
	@Autowired
	UserRepository userRepository;
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	//TODO Be sure to delete this
	//	This is only here so that the old way of pulling users still works. 
	//		Once the 'getUserJSONObject' method can be parsed my Front end, 
	//		this will then be deleted.
	@RequestMapping(method = RequestMethod.GET, path = "old/{user_email}")
	@ResponseBody
	public Optional<User> getUser_OLD(@PathVariable String user_email){
		return userRepository.findById(user_email);
	}

	//TODO Be sure to delete this
	// 	Once 'getAllUsersJSONObject' method can be correctly parsed by 
	//		front end, this will be deleted
	@GetMapping("old/all")
	public Iterable<User> getAllUsers_OLD() {
		return userRepository.findAll();
	}

	/**
	 * TODO: The User's favorites list will be added here as well. It will be the second key in this response=
	 * @param user_email
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/{user_email}", produces = MediaType.APPLICATION_JSON_VALUE)
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
		final String USER_EMAIL_KEY = "email";
		final String USER_TYPE_KEY = "role";
		JSONObject userAsJSONObj = new JSONObject();
		userAsJSONObj.put(USER_EMAIL_KEY, user.getEmail());
		userAsJSONObj.put(USER_TYPE_KEY, user.getUserType());
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

	/** TODO need a way to codense error handling
	 * Currently just takes user Object. Might need to be a JSONObject I parse if more info is required.
	 * @param newUser
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST, path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	private JSONObject createUser(@RequestBody User newUser) {
		JSONObject response = new JSONObject();
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{user_email}", produces = MediaType.APPLICATION_JSON_VALUE) 
	@ResponseBody
	private JSONObject deleteUser(@RequestBody @PathVariable String user_email) {
		JSONObject response = new JSONObject();
		try {
			if(!userRepository.existsById(user_email)) {//Checks to see if User is even in the DB
				throw new IllegalArgumentException();
			}
			userRepository.deleteById(user_email);
			response.put("status", 204);
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
	
	/**
	 * JSONObject. 1st key is old
	 * 2nd key is new
	 * PUT is for update
	 * @param userToEdit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.PUT, path = "/edit/{user_email}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	private JSONObject editUser(@RequestBody JSONObject sentObject) {
		JSONObject response = new JSONObject();
		JSONObject oldInfo = (JSONObject) sentObject.get("oldInfo");
		JSONObject newInfo = (JSONObject) sentObject.get("newInfo");
		User toEdit = new User((String)newInfo.get("email"), (String)newInfo.get("userType"));
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
	
//	
//	/**
//	 * TODO: IDK
//	 * @param user_email
//	 * @return
//	 */
//	@RequestMapping(method = RequestMethod.GET, path = "/{user_email}", produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
//	public ResponseEntity User (@PathVariable String user_email) {//TODO will just be changed to getUser once conversion is complete
//		Optional<User> temp = userRepository.findById(user_email);
//		JSONObject response = new JSONObject();
//		response.put(JSON_OBJECT_RESPONSE_KEY1, temp.get());
//		return null;
//	}
//	
	
}