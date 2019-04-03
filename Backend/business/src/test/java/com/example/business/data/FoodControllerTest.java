package com.example.business.data;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.business.data.controllers.FoodController;
import com.example.business.data.entities.Food;
import com.example.business.data.entities.User;
import com.example.business.data.repositories.FoodRepository;

public class FoodControllerTest {
	
	@InjectMocks
	FoodController foodController;
	
	@Mock
	FoodRepository repo;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getFoodByIdTest() {
		Food found = new Food(66, "Royal with Cheese", 31, 42, 28, 540, "$5.00", "Beef", 0);
		
		when(repo.findById(66)).thenReturn((Optional.of(found)));
		
		Optional<Food> result = foodController.getFood(66);
		verify(repo, times(1)).findById(66);
	}
	
	@Test
	public void getFoodListByIdTest() {
		List<Food> list = new ArrayList<>();
		when(repo.findAll()).thenReturn(list);

		List<Food> uList = (List<Food>) foodController.getAllFood();
		verify(repo, times(1)).findAll();
	}
	
	@Test
	public void getFoodByIdTest_Fail() {
		
	}
	
	@Test
	public void createFoodTest() {
		
	}
	
	@Test
	public void createFoodTest_Fail() {
		
	}
	
	@Test
	public void editFoodTest() {
		
	}
	
	@Test
	public void editFoodTest_Fail() {
		
	}
	
	@Test
	public void deleteFoodTest() {
		
	}
	
	@Test
	public void deleteFoodTest_Fail() {
		
	}
	
}