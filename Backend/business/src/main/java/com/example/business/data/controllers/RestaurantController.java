package com.example.business.data.controllers;

import java.sql.Timestamp; 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.business.data.entities.Restaurant;
import com.example.business.data.repositories.RestaurantRepository;

@RestController
@RequestMapping(value="restaurants")
public class RestaurantController {

	@Autowired
	RestaurantRepository restaurantRepo;

	/**
	 * Private method used for returned a list of restaurants
	 * @return List of Restaurants
	 */
	public List<Restaurant> getRestaurants(){
		Iterable<Restaurant> rIters = restaurantRepo.findAll();
		List<Restaurant> rList = new ArrayList<Restaurant>();
		rIters.forEach(rList::add);
		return rList;
	}
	
	/**
	 * 
	 * @return JSONObject that has key1-> "Restaurants": value1->JSONArray of restaurants in System
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value ="/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllRestaurantsJSONObject()  {
		JSONObject toReturn = new JSONObject();
		String key1 = "data";
		JSONArray listOfRestaurants = new JSONArray(); 
		List<Restaurant> uList = getRestaurants(); 
		for(int i = 0; i < uList.size(); i++) {
			JSONObject temp = new JSONObject(); 
			temp.put("last_updated", uList.get(i).getLast_updated());
			temp.put("restaurant_name", uList.get(i).getRestaurant_name());
			temp.put("restaurant_id", uList.get(i).getRestaurant_id()); 
			listOfRestaurants.add(temp);
		}
		toReturn.put(key1, listOfRestaurants);
		return toReturn;
	}
	
	/**
	 * returns JSON Object of restaurant whose id was specified
	 * @param restaurant_id id of desired restaurant
	 * @return JSON Object of desired restaurant
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, path = "/{restaurant_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONObject getRestaurantJSONObject(@PathVariable int restaurant_id) {
		Optional<Restaurant> temp = restaurantRepo.findById(restaurant_id);
		JSONObject toReturn = new JSONObject();
		JSONObject restaurantInfo = new JSONObject();
		restaurantInfo.put("last_updated", temp.get().getLast_updated());
		restaurantInfo.put("restaurant_name", temp.get().getRestaurant_name());
		restaurantInfo.put("restaurant_id", temp.get().getRestaurant_id());
		toReturn.put("data", restaurantInfo);
		return toReturn;
	}
	
	/**
	 * Old method for GETting restaurant object
	 * @param restaurant_id
	 * @return restaurant object of stated id. (Not JSON Object)
	 */
	@RequestMapping(method = RequestMethod.GET, path = "old/{restaurant_id}")
	@ResponseBody
	public Optional<Restaurant> getRestaurant_OLD(@PathVariable int restaurant_id){
		return restaurantRepo.findById(restaurant_id);
	}


	
	/**
	 * adds a new restaurant to the database if said restaurant doesn't already exist
	 * @param newRestaurant
	 * @return Map of the message and http status of the post
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/create", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> createRestaurant(@RequestBody Restaurant newRestaurant){
		HashMap<String, Object> response = new HashMap<>();
		try {
			if(restaurantRepo.existsById(newRestaurant.getRestaurant_id())) {
				throw new IllegalArgumentException();	
			}
			if(newRestaurant.getLast_updated() == null) {
				newRestaurant.setLast_updated(new Timestamp(System.currentTimeMillis()));
			}
			restaurantRepo.save(newRestaurant);
			response.put("status", 200);
			response.put("message",HttpStatus.OK);
		} catch(IllegalArgumentException e) {
			response.put("status", 400);
			response.put("error", HttpStatus.BAD_REQUEST);
			response.put("message", "Restaurant might already exists or your fields are incorrect. Double check your request");
		}catch (Exception e) {
			response.put("status", 500);
			response.put("error", HttpStatus.INTERNAL_SERVER_ERROR);
			response.put("message", "Server might be down now. Try again");
		}
		return response;
	}
	
	/**
	 * Deletes restaurant corresponding to restaurant_id in url path
	 * @param restaurant_id
	 * @return response
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{restaurant_id}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String,Object> deleteRestaurant(@PathVariable int restaurant_id) {
		HashMap<String,Object> response = new HashMap<>();
		try {
			if(!restaurantRepo.existsById(restaurant_id)) {
				throw new IllegalArgumentException();
			}
			restaurantRepo.deleteById(restaurant_id);
			response.put("status", 200);
			response.put("message", HttpStatus.OK);
		}catch (IllegalArgumentException e) {
			response.put("status", 400);
			response.put("error", HttpStatus.BAD_REQUEST);
			response.put("message", "Could not find that restaurant in the database or your fields are incorrect. Double check your request");
		}catch (Exception e) {
			response.put("status", 500);
			response.put("error", HttpStatus.INTERNAL_SERVER_ERROR);
			response.put("message", "Server might be down now. Try again");
		}
		return response;
	}
	
	/**
	 * edits a restaurant, specified in the url path by its id 
	 * @param updatedRestaurant info to update restaurant with
	 * @param restaurant_id which restaurant to update
	 * @return response
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "/edit/{restaurant_id}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String,Object> editRestaurant(@RequestBody Restaurant updatedRestaurant, @PathVariable int restaurant_id) {
		HashMap<String,Object> response = new HashMap<>();
		try {
			if(!restaurantRepo.existsById(restaurant_id)) {
				throw new IllegalArgumentException();
			}
			if(updatedRestaurant.getLast_updated()==null) {
				updatedRestaurant.setLast_updated(new Timestamp(System.currentTimeMillis()));
			}
			if(restaurant_id != updatedRestaurant.getRestaurant_id()) {
				restaurantRepo.deleteById(restaurant_id);
			}
			restaurantRepo.save(updatedRestaurant);
			response.put("status", 200);
			response.put("message", HttpStatus.OK);
		}catch (IllegalArgumentException e) {
			response.put("status", 400);
			response.put("error", HttpStatus.BAD_REQUEST);
			response.put("message", "Could not find that restaurant in the database or your fields are incorrect. Double check your request");
		}catch (Exception e) {
			response.put("status", 500);
			response.put("error", HttpStatus.INTERNAL_SERVER_ERROR);
			response.put("message", "Server might be down now. Try again");
		}
		return response;
	}
}