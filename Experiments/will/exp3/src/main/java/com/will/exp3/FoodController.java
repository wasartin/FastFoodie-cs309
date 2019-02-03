package com.will.exp3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/foods")
public class FoodController {

	private FoodServices foodService;
	
	@Autowired // this allows spring to resolve and inject collab beans into this bean
	public FoodController(FoodServices foodService) {
		super();
		this.foodService = foodService;
	}
	
	@GetMapping
	public String getAllFoods(Model model) {
		model.addAttribute("foods", this.foodService.getAllFoods());
		return "foods";
	}
}
