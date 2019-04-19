package com.example.business.data.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.business.data.entities.Food;
import com.example.business.data.repositories.FoodRepository;

@Service
public class FoodService {
	
	private final String JSON_OBJECT_RESPONSE_KEY1 = "data";
	
	@Autowired
	FoodRepository foodRepo;

	public Optional<Food> getFood(int food_id){
		return foodRepo.findById(food_id);
	}
	
	public Iterable<Food> getAllFoodList(){
		return foodRepo.findAll();
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject jsonGetFood(int food_id) {
		Optional<Food> temp = foodRepo.findById(food_id);
		JSONObject response = new JSONObject();
		response.put(JSON_OBJECT_RESPONSE_KEY1, temp.get());
		return response;
	}
	
	public List<Food> getFoods(){
		Iterable<Food> uIters = foodRepo.findAll();
		List<Food> uList = new ArrayList<Food>();
		uIters.forEach(uList::add);
		return uList;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject parseFoodIntoJSONObject(Food food) {
		final String FOOD_ID_KEY = "food_id";
		final String FOOD_NAME_KEY = "food_name";
		final String PROTEIN_KEY = "protein_total";
		final String CARB_KEY = "carb_total";
		final String FAT_KEY = "fat_total";
		final String CALORIE_KEY = "calorie_total";
		final String LOCATED_AT_KEY = "located_at";
		
		JSONObject foodAsJSONObj = new JSONObject();
		foodAsJSONObj.put(FOOD_ID_KEY, food.getFood_id());
		foodAsJSONObj.put(FOOD_NAME_KEY, food.getFood_name());
		foodAsJSONObj.put(PROTEIN_KEY, food.getProtein_total());
		foodAsJSONObj.put(CARB_KEY, food.getCarb_total());
		foodAsJSONObj.put(FAT_KEY, food.getFat_total());
		foodAsJSONObj.put(CALORIE_KEY, food.getCalorie_total());
		foodAsJSONObj.put(LOCATED_AT_KEY, food.getLocated_at());
		return foodAsJSONObj;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject jsonGetAllFood()  {
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
	

	
	public JSONObject createFood(Food newFood) {
		JSONObject response;
		try {
			if(foodRepo.existsById(newFood.getFood_id())) {
				throw new IllegalArgumentException();
			}
			foodRepo.save(newFood);
			response = generateResponse(204, HttpStatus.OK, "Food has been created");
		}catch (IllegalArgumentException e) {
			response = generateResponse(400, HttpStatus.BAD_REQUEST, "Food might already exist, or your fields are incorrect, double check your request");
		}catch (Exception e) {
			response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
		}
		return response;
	}
	
	public JSONObject deleteFood(int food_id) {
		JSONObject response;
		try {
			if(!foodRepo.existsById(food_id)) {
				throw new IllegalArgumentException();
			}
			foodRepo.deleteById(food_id);
			response = generateResponse(204, HttpStatus.OK, "Food has been deleted");
		}catch (IllegalArgumentException e) {
			response = generateResponse(400, HttpStatus.BAD_REQUEST, "Could not find that food in the database, or your fields are incorrect, double check your request");
		}catch (Exception e) {
			response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
		}
		return response;
	}
	
	public JSONObject editFood(Food newFood, int food_id) {
		JSONObject response;
		try {
			if(!foodRepo.existsById(food_id)) {
				throw new IllegalArgumentException();
			}
			if(newFood.getFood_id() != food_id) {
				foodRepo.deleteById(food_id);
			}
			foodRepo.save(newFood);
			response = generateResponse(200, HttpStatus.OK, "Food has been edited");
		}catch (IllegalArgumentException e) {
			response = generateResponse(400, HttpStatus.BAD_REQUEST, "Could not find that Food in the database, or your fields are incorrect, double check your request");
		}catch (Exception e) {
			response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
		}
		return response;
	}
}
