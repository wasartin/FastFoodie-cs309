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

import com.example.business.data.entities.FoodRating;
import com.example.business.data.repositories.FoodRatingRepository;

@RestController
@RequestMapping(value="/foodRatings")
public class FoodRatingController {

		@Autowired
		FoodRatingRepository foodRatingRepo;

		@RequestMapping(method = RequestMethod.GET, path = "/{user_email}/{food_id}")
		@ResponseBody
		public Optional<FoodRating> getFoodRating(@PathVariable String user_email, @PathVariable int food_id){
			return null;
			//return foodRatingRepo.findById(user_email, food_id);
		}
		
		@RequestMapping(method = RequestMethod.GET, path = "/{food_id}/all")
		@ResponseBody
		public List<FoodRating> getFoodRatingList(@PathVariable int food_id){
			return foodRatingRepo.findAllRatingsForFood(food_id);
		}
		
		@RequestMapping(method = RequestMethod.GET, path = "/{food_id}")
		@ResponseBody
		public double getFoodRating(@PathVariable int food_id){
			List<FoodRating> ratingList = foodRatingRepo.findAllRatingsForFood(food_id);
			 Integer sum = 0;
			  if(!ratingList.isEmpty()) {
			    for (FoodRating curr : ratingList) {
			       sum += curr.getRating();
			    }
			    return sum.doubleValue() / ratingList.size();
			  }
			  return sum;
		}
}
