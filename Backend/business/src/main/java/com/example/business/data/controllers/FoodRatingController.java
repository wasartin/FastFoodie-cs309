package com.example.business.data.controllers;

import java.util.List;
import java.util.Optional;

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

//		@RequestMapping(method = RequestMethod.GET, path = "/{user_email}/{food_id}")
//		@ResponseBody
//		public Optional<FoodRating> getFoodRatingForUser(@PathVariable String user_email, @PathVariable int food_id){
//			return null;
//			//return foodRatingRepo.findById(user_email, food_id);
//		}
		
		@RequestMapping(method = RequestMethod.GET, path = "/all")
		@ResponseBody
		public List<FoodRating> getAll(){
			return (List<FoodRating>) foodRatingRepo.findAll();
		}
		
		@RequestMapping(method = RequestMethod.GET, path = "/{food_id}/all")
		@ResponseBody
		public List<Double> getFoodRatingList(@PathVariable int food_id){
			return foodRatingRepo.findAllRatingsForFood(food_id);
		}
		
		@RequestMapping(method = RequestMethod.GET, path = "/{food_id}")
		@ResponseBody
		public double getFoodRating(@PathVariable int food_id){
			List<Double> ratingList = foodRatingRepo.findAllRatingsForFood(food_id);
			 Double sum = 0.0;
			  if(!ratingList.isEmpty()) {
			    for (Double curr : ratingList) {
			       sum += curr;
			    }
			    return sum / ratingList.size();
			  }
			  return sum;
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
			newRating.setUser_id(user_email);
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
		
		
		@SuppressWarnings("unchecked")
		private JSONObject generateResponse(int status, HttpStatus input, String message) {
			JSONObject response = new JSONObject();
			response.put("status", status);
			response.put("HttpStatus", input);
			response.put("message", message);
			return response;
		}
}