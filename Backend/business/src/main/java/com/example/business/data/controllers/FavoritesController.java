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

import com.example.business.data.entities.Favorites;
import com.example.business.data.repositories.FavoritesRepository;

@RestController
@RequestMapping(value="/favorites")
public class FavoritesController {

	private final String JSON_OBJECT_RESPONSE_KEY1 = "data";
	@SuppressWarnings("unused")
	private final String JSON_OBJECT_RESPONSE_KEY2 = "info";
	
	@Autowired
	FavoritesRepository favoritesRepository;
	
	@RequestMapping(method = RequestMethod.GET, path = "old/{user_id}")
	@ResponseBody
	public Optional<Favorites> getfavorite_OLD(@PathVariable int user_id){
		return favoritesRepository.findById(user_id);
	}

	@GetMapping("old/all")
	public Iterable<Favorites> getAllfavorite_OLD() {
		return favoritesRepository.findAll();
	}

	/**
	 *
	 * @param favorite_id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, path = "/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONObject getfavoriteJSONObject(@PathVariable int user_id) {
		Optional<Favorites> temp = favoritesRepository.findById(user_id);
		JSONObject response = new JSONObject();
		response.put(JSON_OBJECT_RESPONSE_KEY1, temp.get());
		return response;
	}
	
	/**
	 * This private helper method is used to pull all the favorite from the Data base so it is easier to parse into a JSONObject
	 * @return List<favorite>
	 */
	private List<Favorites> getFavorites(){
		Iterable<Favorites> uIters = favoritesRepository.findAll();
		List<Favorites> uList = new ArrayList<Favorites>();
		uIters.forEach(uList::add);
		return uList;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject parsefavoriteIntoJSONObject(Favorites favorite) {
		final String favorite_USER_ID_KEY = "user_id";
		final String favorite_fid_KEY = "fid";
		
		JSONObject favoriteAsJSONObj = new JSONObject();
		favoriteAsJSONObj.put(favorite_USER_ID_KEY, favorite.getUser_id());
		favoriteAsJSONObj.put(favorite_fid_KEY, favorite.getFid());
		return favoriteAsJSONObj;
	}

	/**
	 * 
	 * @return JSONObject 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllUsersJSONObject()  {
		JSONObject toReturn = new JSONObject();
		String key1 = JSON_OBJECT_RESPONSE_KEY1;
		JSONArray listOfUsers = new JSONArray();
		List<Favorites> uList = getFavorites();
		for(Favorites favorite : uList) {
			listOfUsers.add(parsefavoriteIntoJSONObject(favorite));
		}
		toReturn.put(key1, listOfUsers);
		return toReturn;
	}

	/**
	 * Currently just takes favorite Object. Might need to be a JSONObject I parse if more info is required.
	 * @param newUser
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	private JSONObject createUser(@RequestBody Favorites newfavorite) {
		JSONObject response;
		try {
			if(favoritesRepository.existsById(newfavorite.getUser_id())) {
				throw new IllegalArgumentException();
			}
			favoritesRepository.save(newfavorite);
			response = generateResponse(204, HttpStatus.OK, "favorite has been created");
		}catch (IllegalArgumentException e) {
			response = generateResponse(400, HttpStatus.BAD_REQUEST, "favorite might already exist, or your fields are incorrect, double check your request");
		}catch (Exception e) {
			response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
		}
		return response;
	}
	
	/**
	 * Deletes the user given their unique id
	 * @param favorite_id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE) 
	@ResponseBody
	private JSONObject deleteUser(@PathVariable int user_id) {
		JSONObject response;
		try {
			if(!favoritesRepository.existsById(user_id)) {
				throw new IllegalArgumentException();
			}
			favoritesRepository.deleteById(user_id);
			response = generateResponse(204, HttpStatus.OK, "favorite has been deleted");
		}catch (IllegalArgumentException e) {
			response = generateResponse(400, HttpStatus.BAD_REQUEST, "Could not find that favorite in the database, or your fields are incorrect, double check your request");
		}catch (Exception e) {
			response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
		}
		return response;
	}
	
	/**
	 * @param favorite To edit
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "/edit/{user_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	private JSONObject editUser(@RequestBody Favorites newfavorite, @PathVariable int user_id) {
		JSONObject response;
		try {
			if(!favoritesRepository.existsById(user_id)) {
				throw new IllegalArgumentException();
			}
			if(newfavorite.getUser_id() != user_id) {
				favoritesRepository.deleteById(user_id);
			}
			favoritesRepository.save(newfavorite);
			response = generateResponse(200, HttpStatus.OK, "favorite has been edited");
		}catch (IllegalArgumentException e) {
			response = generateResponse(400, HttpStatus.BAD_REQUEST, "Could not find that favorite in the database, or your fields are incorrect, double check your request");
		}catch (Exception e) {
			response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
		}
		return response;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject generateResponse(int status, HttpStatus input, String message) {
		JSONObject response = new JSONObject();
		response.put("status", status);
		response.put("HttpStatus", input);
		response.put("message", message);
		return response;
	}
}
