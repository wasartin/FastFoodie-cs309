package com.example.business.data.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.business.data.entities.Food;
import com.example.business.data.services.FoodService;

/**
 * Food controller. 
 * @author Will and Jon
 *
 */
@RestController
@RequestMapping(value="/foods")
public class FoodController {
	
	@Autowired
	FoodService foodService;

	/**
	 * returns an optional for a specified food
	 * @param food_id
	 * @return optional<food>
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/{food_id}")
	@ResponseBody
	public Optional<Food> getFood(@PathVariable int food_id){
		return foodService.getEntityByID(food_id);
	}
	
	/**
	 * returns iterable for all food objects
	 * @return iterable<food>
	 */
	@GetMapping("/all")
	public Iterable<Food> getAllFoodList() {
		return foodService.getAllEntities();
	}

	/**
	 * Currently just takes food Object. Might need to be a JSONObject I parse if more info is required.
	 * @param newFood
	 * @return a response Entity 
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> createFood(@RequestBody Food newFood) {
		return foodService.createEntity(newFood, newFood.getFood_id());
	}
	
	/**
	 * Deletes the food given their unique id
	 * @param food_id
	 * @return a response Entity 
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{food_id}", produces = MediaType.APPLICATION_JSON_VALUE) 
	@ResponseBody
	public ResponseEntity<?> deleteFood(@PathVariable int food_id) {
		return foodService.deleteEntityById(food_id);
	}
	
	/**takes in a food object and edits the specified food to match the object taken in 
	 * @param food To edit
	 * @return a response Entity 
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "/edit/{food_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> editFood(@RequestBody Food newFood, @PathVariable int food_id) {
		return foodService.editEntity(newFood, food_id);
	}
}