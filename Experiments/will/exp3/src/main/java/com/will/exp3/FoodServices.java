package com.will.exp3;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodServices {
	private FoodRepository foodRepository;
	
	@Autowired
	public FoodServices(FoodRepository foodRepository) {
		super();
		this.foodRepository = foodRepository;
	}
	
	public List<Food> getAllFoods(){
		List<Food> foods = new ArrayList<>();
		this.foodRepository.findAll().forEach(foods::add);
		return foods;
	}
}
