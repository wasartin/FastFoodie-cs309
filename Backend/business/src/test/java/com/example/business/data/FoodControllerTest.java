package com.example.business.data;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

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
	public void createFoodTest() {
		Food toAdd = new Food(100, "PacMan Pizza", 31, 42, 28, 540, "$25.00", "Pizza", 2);
		when(repo.existsById(toAdd.getFood_id())).thenReturn(false);
		when(repo.save(toAdd)).thenReturn(new Food());
		
		JSONObject response = foodController.createFood(toAdd);
		assertEquals(response.get("HttpStatus"), HttpStatus.OK);
		assertEquals(response.get("message"), "Food has been created");
		assertEquals(response.get("status"), 204);
	}
	
	@Test
	public void createFoodTest_Fail() {
		Food toAdd = new Food(100, "PacMan Pizza", 31, 42, 28, 540, "$25.00", "Pizza", 2);
		when(repo.existsById(toAdd.getFood_id())).thenReturn(true);
		when(repo.save(toAdd)).thenReturn(null);
		
		JSONObject response = foodController.createFood(toAdd);
		assertEquals(response.get("HttpStatus"), HttpStatus.BAD_REQUEST);
		assertEquals(response.get("message"), "Food might already exist, or your fields are incorrect, double check your request");
		assertEquals(response.get("status"), 400);
	}
	
	@Test
	public void editFoodTest() {
		Food toAdd = new Food(50, "PacMan Pizza", 31, 42, 28, 540, "$25.00", "Pizza", 2);
		when(repo.existsById(toAdd.getFood_id())).thenReturn(true);
		when(repo.save(toAdd)).thenReturn(new Food());
		
		JSONObject response = foodController.editFood(toAdd, 50);
		assertEquals(response.get("HttpStatus"), HttpStatus.OK);
		assertEquals(response.get("message"), "Food has been edited");
		assertEquals(response.get("status"), 200);
	}
	
	@Test
	public void editFoodTest_Fail() {
		Food toAdd = new Food(50, "PacMan Pizza", 31, 42, 28, 540, "$25.00", "Pizza", 2);
		when(repo.existsById(toAdd.getFood_id())).thenReturn(true);
		when(repo.save(toAdd)).thenReturn(new Food());
		
		JSONObject response = foodController.editFood(toAdd, 50);
		assertEquals(response.get("HttpStatus"), HttpStatus.OK);
		assertEquals(response.get("message"), "Food has been edited");
		assertEquals(response.get("status"), 200);
	}
	
	@Test
	public void deleteFoodTest() {
		when(repo.existsById(50)).thenReturn(true);

		JSONObject response = foodController.deleteFood(50);
		verify(repo, times(1)).deleteById(50);
		assertEquals(response.get("HttpStatus"), HttpStatus.OK);
		assertEquals(response.get("message"), "Food has been deleted");
		assertEquals(response.get("status"), 204);
	}
	
	@Test
	public void deleteFoodTest_Fail() {
		when(repo.existsById(50)).thenReturn(false);

		JSONObject response = foodController.deleteFood(50);
		verify(repo, never()).deleteById(50);
		assertEquals(response.get("HttpStatus"), HttpStatus.BAD_REQUEST);
		assertEquals(response.get("message"), "Could not find that food in the database, or your fields are incorrect, double check your request");
		assertEquals(response.get("status"), 400);
	}
	
}