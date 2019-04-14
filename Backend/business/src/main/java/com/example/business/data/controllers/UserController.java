package com.example.business.data.controllers;

import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.business.data.services.UserService;

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
	
	@Autowired
	UserService userService;
	
	//TODO Be sure to delete this
	//	This is only here so that the old way of pulling users still works. 
	//		Once the 'getUserJSONObject' method can be parsed my Front end, 
	//		this will then be deleted.
	@RequestMapping(method = RequestMethod.GET, path = "old/{user_email}")
	@ResponseBody
	public Optional<User> getUser(@PathVariable String user_email){
		return userService.getUser_OLD(user_email);
	}

	//TODO change the mapping here, as well as method name. the json one should have '/json/' in the url
	// 	Once 'getAllUsersJSONObject' method can be correctly parsed by 
	//		front end, this will be deleted
	@GetMapping("old/all")
	public Iterable<User> getAllUsers() {
		return userService.getAllUsers_OLD();
	}

	/**
	 * TODO: The User's favorites list will be added here as well. It will be the second key in this response=
	 * @param user_email
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/{user_email}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONObject getUserJSONObject(@PathVariable String user_email) {//TODO will just be changed to getUser once conversion is complete
		return userService.getUserJSONObject(user_email);
	}

	/**
	 * 
	 * @return JSONObject that has key1-> "Users": value1->JSONArray of users in System
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllUsersJSONObject()  {
		return userService.getAllUsersJSONObject();
	}

	/**
	 * Currently just takes user Object. Might need to be a JSONObject I parse if more info is required.
	 * @param newUser
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONObject createUser(@RequestBody User newUser) {
		return userService.createUser(newUser);
	}
	
	/**
	 * Deletes the user given their unique id
	 * @param user_email
	 * @return
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{user_email}", produces = MediaType.APPLICATION_JSON_VALUE) 
	@ResponseBody
	public JSONObject deleteUser(@PathVariable String user_email) {
		return userService.deleteUser(user_email);
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
		return userService.editUser(newUserInfo, user_email);
	}
	
}