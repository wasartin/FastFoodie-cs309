package com.example.business.data.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	
	@GetMapping("/all")
	public Iterable<Restaurant> getAllRestaurants(){
		return restaurantRepo.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/{restaurant_id}")
	@ResponseBody
	public Optional<Restaurant> getRestaurant(@PathVariable String restaurant_id){
		return restaurantRepo.findById(restaurant_id);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/create")
	@ResponseBody
	private Map<String, Object> createRestaurant(@RequestBody Restaurant newRestaurant){
		HashMap<String, Object> response = new HashMap<>();
		try {
			if(restaurantRepo.findById(newRestaurant.getRestaurant_id())==null)
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
	private Map<String,Object> deleteRestaurant(@PathVariable String restaurant_id ) {
		HashMap<String,Object> response = new HashMap<>();
		try {
			if(restaurantRepo.findById(restaurant_id) == null)//Checks to see if User is even in the DB
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
