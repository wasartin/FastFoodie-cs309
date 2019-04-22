package com.example.business.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.business.data.entities.FoodRating;
import com.example.business.data.repositories.FoodRatingRepository;

@Service
public class FoodRatingsService extends AbstractService<FoodRating, Integer>{
	@Autowired
	FoodRatingRepository repo;
	
	public List<FoodRating> getAllRatingsByUser(String user_email){
		return repo.getFoodRatingsForUser(user_email);
	}
	
	public FoodRating getRatingForUserAndFood(String user_email, int food_id){
		return repo.getFoodRatingByUserAndFood(user_email, food_id);
	}
	
	public List<Integer> findAllRatingsForFood(int food_id){
		return repo.findAllRatingsForFood(food_id);
	}
	
	
}
