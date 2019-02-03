package com.will.exp3;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")	
public class ApiController {
	
	private FoodServices foodServices;
	
	@Autowired
	public ApiController(FoodServices foodServices) {
		super();
		this.foodServices = foodServices;
	}
	
	@GetMapping("foods")
	public List<Food> getAllFoods(){
		return this.foodServices.getAllFoods();
	}
	
}
