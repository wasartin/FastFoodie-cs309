package com.example.business.data.controllers;

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
import org.springframework.web.bind.annotation.GetMapping;
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
	
	//This is why you had an error, there are two methods that map to /all. 
	//TODO Just delete this method
	//@GetMapping("/all")
	//public Iterable<Restaurant> getAllRestaurants(){
	//	return restaurantRepo.findAll();
	//}

	/**
	 * Private method used for returned a list of restaurants
	 * @return
	 */
	private List<Restaurant> getRestaurants(){
		Iterable<Restaurant> rIters = restaurantRepo.findAll();
		List<Restaurant> rList = new ArrayList<Restaurant>();
		rIters.forEach(rList::add);
		return rList;
	}
	
	/**
	 * 
	 * @return JSONObject that has key1-> "Restaurants": value1->JSONArray of resturants in System
	 */
	//TODO Go over this and delete all these comments
	//TODO change this if you want, name shit that makes the most sense
	@SuppressWarnings("unchecked")
	@RequestMapping(value ="/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllRestaurantsJSONObject()  {
		JSONObject toReturn = new JSONObject();//This is what will be returned
		String key1 = "Restaurants";//This is the first key in the JSONObject
		JSONArray listOfRestaurants = new JSONArray(); //This is the first value (mapped to Restaurants)
		List<Restaurant> uList = getRestaurants(); //Here the Private method is used
		for(int i = 0; i < uList.size(); i++) {//Iterate the whole list returned of restaurnts in the DB
			JSONObject temp = new JSONObject(); //This is a temp object for making the JSON object to put in the JSONArray (listOfRestaurants)
			temp.put("restaurantID", uList.get(i).getRestaurant_id()); //key = id, value = get, this is the parsing
			temp.put("restaurantName", uList.get(i).getRestaurant_name());//Parsing
			temp.put("lastUpdated", uList.get(i).getLast_updated());//Parsing
			listOfRestaurants.add(temp);//Add to JSONARRAY
		}
		toReturn.put(key1, listOfRestaurants);//Add the KEY & VALUE to the JSONObj we are returning
		return toReturn;//return that shit
	}
	
	/**
	 * I don't think we want to do this 
	 * @param restaurant_id
	 * @return
	 */
	//TODO Change this to return a JSONObject with String key1 = 'restaurant' 
		//then the VALUE here would be ANOTHER JSONObject that is the restaurant
	//TODO Return a JSONObject not optional
	//NOTE: this will essentially look a lot like getAllRestaurantsJSONObject().
	@RequestMapping(method = RequestMethod.GET, path = "/{restaurant_id}")
	@ResponseBody
	public Optional<Restaurant> getRestaurant(@PathVariable String restaurant_id){ //
		return restaurantRepo.findById(restaurant_id);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/create")
	@ResponseBody
	private Map<String, Object> createRestaurant(@RequestBody Restaurant newRestaurant){
		HashMap<String, Object> response = new HashMap<>();
		try {
			if(restaurantRepo.findById(newRestaurant.getRestaurant_id())==null) //TODO restaurantRepo has a method that does this much better, restaurantRepo.exitsById(.better..)
				throw new IllegalArgumentException();	
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
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{restaurant_id}")
	@ResponseBody
	private Map<String,Object> deleteRestaurant(@PathVariable String restaurant_id ) { //TODO
		HashMap<String,Object> response = new HashMap<>();
		try {
			if(restaurantRepo.findById(restaurant_id) == null)//TODO restaurantRepo has a method that does this much better, restaurantRepo.exitsById(...)
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
