package com.example.business.data;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.example.business.data.entities.Food;
import com.example.business.data.repositories.FoodRepository;
import com.example.business.data.services.FoodService;

public class FoodServiceTest {
	
	@InjectMocks
	FoodService foodService;
	
	@Mock
	FoodRepository repo;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getFoodByIdTest() {
		Food found = new Food(64, "Royal with Cheese", 31, 42, 28, 540, "$5.00", "Beef", 0, 0);
		
		when(repo.findById(64)).thenReturn((Optional.of(found)));
		
		foodService.getEntityByID(64);
		verify(repo, times(1)).findById(64);
	}

	@Test
	public void getFoodListTest() {
		List<Food> list = new ArrayList<>();
		when(repo.findAll()).thenReturn(list);

		foodService.getAllEntities();
		verify(repo, times(1)).findAll();
	}
	
	@Test
	public void createFoodTest() {
		Food toAdd = new Food(100, "PacMan Pizza", 31, 42, 28, 540, "$25.00", "Pizza", 2, 0);
		when(repo.existsById(toAdd.getFood_id())).thenReturn(false);
		when(repo.save(toAdd)).thenReturn(new Food());
		
		ResponseEntity<?> response = foodService.createEntity(toAdd, toAdd.getFood_id());
		
		verify(repo, times(1)).save(toAdd);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getHeaders().getContentType(), MediaType.APPLICATION_JSON);
		assertEquals(response.getBody(), toAdd.getClass().getSimpleName());
	}
	
	@Test
	public void createFoodTest_Fail() {
		Food toAdd = new Food(100, "PacMan Pizza", 31, 42, 28, 540, "$25.00", "Pizza", 2, 0);
		when(repo.existsById(toAdd.getFood_id())).thenReturn(true);
		
		ResponseEntity<?> response = foodService.createEntity(toAdd, toAdd.getFood_id());
		verify(repo, never()).save(toAdd);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(response.getHeaders().getContentType(), MediaType.APPLICATION_JSON);
		assertEquals(response.getBody(), toAdd.getClass().getSimpleName());
	}
	
	@Test
	public void editFoodTest() {
		Food toEdit = new Food(50, "PacMan Pizza", 31, 42, 28, 540, "$26.00", "Pizza", 2, 0);
		when(repo.existsById(toEdit.getFood_id())).thenReturn(true);
		when(repo.findById(50)).thenReturn(Optional.of(new Food(50, "PacMan Pizza", 31, 42, 28, 540, "$25.00", "Pizza", 2, 0)));
		
		when(repo.save(toEdit)).thenReturn(new Food());
		
		ResponseEntity<?> response = foodService.editEntity(toEdit, 50);
		
		verify(repo, times(1)).save(toEdit);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getHeaders().getContentType(), MediaType.APPLICATION_JSON);
		assertEquals(response.getBody(), toEdit.getClass().getSimpleName());
	}
	
	@Test
	public void editFoodTest_Fail() {
		Food toAdd = new Food(50, "PacMan Pizza", 31, 42, 28, 540, "$25.00", "Pizza", 2, 0);
		when(repo.existsById(toAdd.getFood_id())).thenReturn(false);
		
		ResponseEntity<?> response = foodService.editEntity(toAdd, 50);
		
		verify(repo, never()).save(toAdd);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(response.getHeaders().getContentType(), MediaType.APPLICATION_JSON);
		assertEquals(response.getBody(), toAdd.getClass().getSimpleName());
	}
	
	@Test
	public void deleteFoodTest() {
		Food toDelete = new Food(50, "PacMan Pizza", 31, 42, 28, 540, "$25.00", "Pizza", 2, 0);
		when(repo.existsById(50)).thenReturn(true);
		when(repo.findById(50)).thenReturn(Optional.of(toDelete));

		ResponseEntity<?> response = foodService.deleteEntityById(50);
		verify(repo, times(1)).deleteById(50);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getHeaders().getContentType(), MediaType.APPLICATION_JSON);
		assertEquals(response.getBody(), toDelete.getClass().getSimpleName());
	}
	
	@Test
	public void deleteFoodTest_Fail() {
		when(repo.existsById(50)).thenReturn(false);

		ResponseEntity<?> response = foodService.deleteEntityById(50);
		verify(repo, never()).deleteById(50);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(response.getHeaders().getContentType(), MediaType.APPLICATION_JSON);
	}
	
}