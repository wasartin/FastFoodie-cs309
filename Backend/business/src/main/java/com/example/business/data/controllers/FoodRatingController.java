package com.example.business.data.controllers;

import java.util.List;

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

import com.example.business.data.entities.FoodRating;
import com.example.business.data.repositories.FoodRatingRepository;

@RestController
@RequestMapping(value="/foodRatings")
public class FoodRatingController {

		@Autowired
		FoodRatingRepository foodRatingRepo;
		
		@RequestMapping(method = RequestMethod.GET, path = "/all")
		@ResponseBody
		public List<FoodRating> getAll(){
			return (List<FoodRating>) foodRatingRepo.findAll();
		}
		
		@RequestMapping(method = RequestMethod.GET, path = "/{user_email}/{food_id}")
		@ResponseBody
		public FoodRating getSpecific(@PathVariable String user_email, @PathVariable int food_id){
			return foodRatingRepo.getFoodRatingByUserAndFood(user_email, food_id);
		}
		
		@RequestMapping(method = RequestMethod.GET, path = "all/user/{user_email}")
		@ResponseBody
		public List<FoodRating> getAllForUser(@PathVariable String user_email){
			return (List<FoodRating>) foodRatingRepo.getFoodRatingsForUser(user_email);
		}
		
		@RequestMapping(method = RequestMethod.GET, path = "/all/food/{food_id}")
		@ResponseBody
		public List<Integer> getFoodRatingList(@PathVariable int food_id){
			return foodRatingRepo.findAllRatingsForFood(food_id);
		}
		
		@RequestMapping(method = RequestMethod.GET, path = "/average/{food_id}")
		@ResponseBody
		public double getFoodRating(@PathVariable int food_id){
			List<Integer> ratingList = foodRatingRepo.findAllRatingsForFood(food_id);
			 double sum = 0;
			  if(!ratingList.isEmpty()) {
			    for (int curr : ratingList) {
			    	if (curr > 0 && curr < 6) {
					       sum += curr;
			    	}

			    }
			  }
			    return sum / ratingList.size();
		}
		
		@RequestMapping(method = RequestMethod.POST, path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public JSONObject createFoodRatingByJSON(@RequestBody FoodRating newRating) {
			JSONObject response;
			try {
				if(foodRatingRepo.existsById(newRating.getRating_id())) {
					throw new IllegalArgumentException();
				}
				foodRatingRepo.save(newRating);
				response = generateResponse(204, HttpStatus.OK, "Food has bee rated");
			}catch (IllegalArgumentException e) {
				response = generateResponse(400, HttpStatus.BAD_REQUEST, "Rating might already exist, or your fields are incorrect, double check your request");
			}catch (Exception e) {
				response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
			}
			return response;
		}
		
		@RequestMapping(method = RequestMethod.POST, path = "/create/{user_email}/{food_id}/{rating}", produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public JSONObject createFoodRating(@PathVariable String user_email, @PathVariable int food_id, @PathVariable int rating) {
			FoodRating newRating = new FoodRating();
			newRating.setFood_id(food_id);
			newRating.setRating(rating);
			newRating.setUser_email(user_email);
			JSONObject response;
			try {
				if(foodRatingRepo.getFoodRatingByUserAndFood(newRating.getUser_email(), newRating.getFood_id()) != null) {
					throw new IllegalArgumentException();
				}
				foodRatingRepo.save(newRating);
				response = generateResponse(204, HttpStatus.OK, "Food has been rated");
			}catch (IllegalArgumentException e) {
				response = generateResponse(400, HttpStatus.BAD_REQUEST, "Rating might already exist, or your fields are incorrect, double check your request");
			}catch (Exception e) {
				response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
			}
			return response;
		}
		
		@RequestMapping(method = RequestMethod.PUT, path = "/edit/{user_email}/{food_id}/{rating}", produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public JSONObject editFoodRating(@PathVariable String user_email, @PathVariable int food_id, @PathVariable int rating) {
			FoodRating findOldVersion = foodRatingRepo.getFoodRatingByUserAndFood(user_email, food_id);
			JSONObject response;
			try {
				if(findOldVersion == null) {
					throw new IllegalArgumentException();
				}
				findOldVersion.setRating(rating);
				foodRatingRepo.save(findOldVersion);
				response = generateResponse(204, HttpStatus.OK, "Food rating has been edited");
			}catch (IllegalArgumentException e) {
				response = generateResponse(400, HttpStatus.BAD_REQUEST, "Rating might already exist, or your fields are incorrect, double check your request");
			}catch (Exception e) {
				response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
			}
			return response;
		}
		
		@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{user_email}/{food_id}", produces = MediaType.APPLICATION_JSON_VALUE) 
		@ResponseBody
		public JSONObject deleteFood(@PathVariable String user_email, @PathVariable int food_id) {
			FoodRating oldVersion = foodRatingRepo.getFoodRatingByUserAndFood(user_email, food_id);
			JSONObject response;
			try {
				if(oldVersion == null) {
					throw new IllegalArgumentException();
				}
				foodRatingRepo.deleteById(oldVersion.getRating_id());
				response = generateResponse(204, HttpStatus.OK, "Food Rating has been deleted");
			}catch (IllegalArgumentException e) {
				response = generateResponse(400, HttpStatus.BAD_REQUEST, "Could not find that food rating in the database, or your fields are incorrect, double check your request");
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