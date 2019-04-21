package com.example.business.data;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
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

import com.example.business.data.entities.FoodRating;
import com.example.business.data.repositories.FoodRatingRepository;
import com.example.business.data.services.FoodRatingsService;

public class FoodRatingServiceTest {

	@InjectMocks
	FoodRatingsService frServ;
	
	@Mock
	FoodRatingRepository repo;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getByIdTest() {
		FoodRating fRating = new FoodRating();
		Optional<FoodRating> fRatingOp = Optional.of(fRating);
		when(repo.findById(1)).thenReturn(fRatingOp);
		
		frServ.getEntityByID(1);
		verify(repo, times(1)).findById(1);
	}
	
	@Test
	public void getAllTest() {
		List<FoodRating> frList = new ArrayList<FoodRating>();
		when(repo.findAll()).thenReturn(frList);
		
		frServ.getAllEntities();
		verify(repo, times(1)).findAll();
	}
	
	@Test
	public void getByUserAndFoodIdTest() {
		FoodRating fr = new FoodRating();
		when(repo.getFoodRatingByUserAndFood("Shawn", 0)).thenReturn(fr);
		
		frServ.getRatingForUserAndFood("Shawn", 0);
		verify(repo, times(1)).getFoodRatingByUserAndFood("Shawn", 0);
		assertThat(repo.getFoodRatingByUserAndFood("Shawn", 0), is(notNullValue()));
	}
	
	@Test
	public void getByUserAndFoodIdTest_Fail() {
		
	}
	
	@Test
	public void getAllForUser() {
		
	}
	
	@Test
	public void getAllForUser_Fail() {
		
	}
	
	@Test
	public void getAllForFood() {
		
	}
	
	@Test
	public void getAllForFood_Fail() {
		
	}
	
	@Test
	public void createRating() {
		
	}
	
	@Test
	public void createRating_Fail() {
		
	}
	
	@Test
	public void editRating() {
		
	}
	
	@Test
	public void editRating_Fail() {
		
	}
	
	@Test
	public void deleteRating() {
		
	}
	
	@Test
	public void deleteRating_Fail() {
		
	}
	
}
