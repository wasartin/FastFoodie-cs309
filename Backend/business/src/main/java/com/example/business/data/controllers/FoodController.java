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

import com.example.business.data.entities.Food;
import com.example.business.data.repositories.FoodRepository;

@RestController
@RequestMapping(value="/foods")
public class FoodController {

	private final String JSON_OBJECT_RESPONSE_KEY1 = "data";
	@SuppressWarnings("unused")
	private final String JSON_OBJECT_RESPONSE_KEY2 = "info";
	
	@Autowired
	FoodRepository foodRepository;
	
	@RequestMapping(method = RequestMethod.GET, path = "/{food_id}")
	@ResponseBody
	public Optional<Food> getFood(@PathVariable int food_id){
		return foodRepository.findById(food_id);
	}

	@GetMapping("/all")
	public Iterable<Food> getAllFood() {
		return foodRepository.findAll();
	}

	/**
	 *
	 * @param food_id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, path = "json/{food_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONObject getFoodJSONObject(@PathVariable int food_id) {
		Optional<Food> temp = foodRepository.findById(food_id);
		JSONObject response = new JSONObject();
		response.put(JSON_OBJECT_RESPONSE_KEY1, temp.get());
		return response;
	}
	
	/**
	 * This private helper method is used to pull all the food from the Data base so it is easier to parse into a JSONObject
	 * @return List<Food>
	 */
	private List<Food> getFoods(){
		Iterable<Food> uIters = foodRepository.findAll();
		List<Food> uList = new ArrayList<Food>();
		uIters.forEach(uList::add);
		return uList;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject parseFoodIntoJSONObject(Food food) {
		final String FOOD_ID_KEY = "food_id";
		final String FOOD_NAME_KEY = "food_name";
		final String PROTEIN_KEY = "protein_total";
		final String CARB_KEY = "carb_total";
		final String FAT_KEY = "fat_total";
		final String CALORIE_KEY = "calorie_total";
		final String LOCATED_AT_KEY = "located_at";
		final String PRICE_KEY = "price";
		final String CATEGORY_KEY = "category";
		
		JSONObject foodAsJSONObj = new JSONObject();
		foodAsJSONObj.put(FOOD_ID_KEY, food.getFood_id());
		foodAsJSONObj.put(FOOD_NAME_KEY, food.getFood_name());
		foodAsJSONObj.put(PROTEIN_KEY, food.getProtein_total());
		foodAsJSONObj.put(CARB_KEY, food.getCarb_total());
		foodAsJSONObj.put(FAT_KEY, food.getFat_total());
		foodAsJSONObj.put(CALORIE_KEY, food.getCalorie_total());
		foodAsJSONObj.put(LOCATED_AT_KEY, food.getLocated_at());
		foodAsJSONObj.put(PRICE_KEY, food.getPrice());
		foodAsJSONObj.put(CATEGORY_KEY, food.getCategory());
		return foodAsJSONObj;
	}

	/**
	 * 
	 * @return JSONObject 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "json/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllFoodJSONObject()  {
		JSONObject toReturn = new JSONObject();
		String key1 = JSON_OBJECT_RESPONSE_KEY1;
		JSONArray listOfFoods = new JSONArray();
		List<Food> uList = getFoods();
		for(Food food : uList) {
			listOfFoods.add(parseFoodIntoJSONObject(food));
		}
		toReturn.put(key1, listOfFoods);
		return toReturn;
	}

	/**
	 * Currently just takes food Object. Might need to be a JSONObject I parse if more info is required.
	 * @param newFood
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONObject createFood(@RequestBody Food newFood) {
		JSONObject response;
		try {
			if(foodRepository.existsById(newFood.getFood_id())) {
				throw new IllegalArgumentException();
			}
			foodRepository.save(newFood);
			response = generateResponse(204, HttpStatus.OK, "Food has been created");
		}catch (IllegalArgumentException e) {
			response = generateResponse(400, HttpStatus.BAD_REQUEST, "Food might already exist, or your fields are incorrect, double check your request");
		}catch (Exception e) {
			response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
		}
		return response;
	}
	
	/**
	 * Deletes the food given their unique id
	 * @param food_id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{food_id}", produces = MediaType.APPLICATION_JSON_VALUE) 
	@ResponseBody
	public JSONObject deleteFood(@PathVariable int food_id) {
		JSONObject response;
		try {
			if(!foodRepository.existsById(food_id)) {
				throw new IllegalArgumentException();
			}
			foodRepository.deleteById(food_id);
			response = generateResponse(204, HttpStatus.OK, "Food has been deleted");
		}catch (IllegalArgumentException e) {
			response = generateResponse(400, HttpStatus.BAD_REQUEST, "Could not find that food in the database, or your fields are incorrect, double check your request");
		}catch (Exception e) {
			response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
		}
		return response;
	}
	
	/**
	 * @param food To edit
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "/edit/{food_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONObject editFood(@RequestBody Food newFood, @PathVariable int food_id) {
		JSONObject response;
		try {
			if(!foodRepository.existsById(food_id)) {
				throw new IllegalArgumentException();
			}
			if(newFood.getFood_id() != food_id) {
				foodRepository.deleteById(food_id);
			}
			foodRepository.save(newFood);
			response = generateResponse(200, HttpStatus.OK, "Food has been edited");
		}catch (IllegalArgumentException e) {
			response = generateResponse(400, HttpStatus.BAD_REQUEST, "Could not find that Food in the database, or your fields are incorrect, double check your request");
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
