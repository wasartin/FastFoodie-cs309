package com.example.business.data.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
	private List<Restaurant> getRestaurants(){
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
		String key1 = "Restaurants";
		JSONArray listOfRestaurants = new JSONArray(); 
		List<Restaurant> uList = getRestaurants(); 
		for(int i = 0; i < uList.size(); i++) {
			JSONObject temp = new JSONObject(); 
			temp.put("lastUpdated", uList.get(i).getLast_updated());
			temp.put("restaurantName", uList.get(i).getRestaurant_name());
			temp.put("restaurantID", uList.get(i).getRestaurant_id()); 
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
		restaurantInfo.put("lastUpdated", temp.get().getLast_updated());
		restaurantInfo.put("restaurantName", temp.get().getRestaurant_name());
		restaurantInfo.put("restaurantID", temp.get().getRestaurant_id());
		toReturn.put("Restaurant", restaurantInfo);
		return toReturn;
	}

	
	/**
	 * adds a new restaurant to the database if said restaurant doesn't already exist
	 * @param newRestaurant
	 * @return Map of the message and http status of the post
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/create", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	private Map<String, Object> createRestaurant(@RequestBody Restaurant newRestaurant){
		HashMap<String, Object> response = new HashMap<>();
		try {
			if(restaurantRepo.existsById(newRestaurant.getRestaurant_id())) {
				throw new IllegalArgumentException();	
			}
			
			newRestaurant.setLast_updated(null);
			
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
	
	@RequestMapping(method = RequestMethod.POST, path = "/create/JSON", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	private Map<String, Object> createRestaurantJSON(@RequestBody JSONObject newJSON){
		HashMap<String, Object> response = new HashMap<>();
		
		JSONObject restaurantInfo = new JSONObject();
		restaurantInfo = (JSONObject) newJSON.get("Restaurant");
		
		Restaurant newRestaurant = new Restaurant();
		newRestaurant.setRestaurant_id((int) restaurantInfo.get("restaurantID"));
		newRestaurant.setRestaurant_name((String) restaurantInfo.get("restaurantName"));
		newRestaurant.setLast_updated((Timestamp) restaurantInfo.get("lastUpdated"));


		try {
			if(restaurantRepo.existsById(newRestaurant.getRestaurant_id())) {
				throw new IllegalArgumentException();	
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
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{restaurant_id}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	private Map<String,Object> deleteRestaurant(@PathVariable int restaurant_id ) {
		HashMap<String,Object> response = new HashMap<>();
		try {
			if(!restaurantRepo.existsById(restaurant_id))//Checks to see if restaurant is in the DB
				throw new IllegalArgumentException();
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

}
