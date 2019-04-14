package com.example.business.data;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import com.example.business.data.controllers.RestaurantController;
import com.example.business.data.entities.Restaurant;
import com.example.business.data.repositories.RestaurantRepository;

public class RestaurantControllerTest {

	@InjectMocks
	RestaurantController restCont;
	
	@Mock
	RestaurantRepository restRepo;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}


	@Test
	public void getRestaurantByIdTest() {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		when(restRepo.findById(1)).thenReturn(Optional.of(new Restaurant(1, "McDonalds", now)));
		
		Optional<Restaurant> restO = restCont.getRestaurant_OLD(1);
		Restaurant rest = restO.get();
		assertEquals(1, rest.getRestaurant_id());
		assertEquals("McDonalds", rest.getRestaurant_name());
		assertEquals(now, rest.getLast_updated());
	}
	
	@Test
	public void getAllRestaurantsTest() {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		List<Restaurant> list = new ArrayList<Restaurant>();
		Restaurant restOne = new Restaurant(1, "McDonalds", now);
		Restaurant restTwo = new Restaurant(2, "Perkins", now);
		Restaurant restThree = new Restaurant(3, "Hooters", now);

		list.add(restOne);
		list.add(restTwo);
		list.add(restThree);

		when(restRepo.findAll()).thenReturn(list);

		List<Restaurant> restList = (List<Restaurant>) restCont.getAllRestaurantsList();

		assertEquals(3, restList.size());
		verify(restRepo, times(1)).findAll();
	}

	@Test
	public void createRestaurantTest() {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Restaurant found = new Restaurant(1, "McDonalds", now);
		when(restRepo.save(found)).thenReturn(new Restaurant());
		
		Map<String,Object> response = restCont.createRestaurant(found);
		assertThat(restRepo.save(found), is(notNullValue()));
		assertEquals(response.get("message"), HttpStatus.OK);
		assertEquals(response.get("status"), 200);	
	}

	@Test 
	public void deleteRestaurantTest() {
		when(restRepo.existsById(1)).thenReturn(true);

		Map<String, Object> response = restCont.deleteRestaurant(1);
		verify(restRepo, times(1)).deleteById(1);
		assertEquals(response.get("status"), 200);
	}
	
	@Test
	public void editRestaurantTest() {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Restaurant rest = new Restaurant(1, "McDonalds", now);
		
		when(restRepo.save(rest)).thenReturn(new Restaurant());
		when(restRepo.existsById(1)).thenReturn(true);
		
		Map<String, Object> response = restCont.editRestaurant(rest, 1);

		assertThat(restRepo.save(rest), is(notNullValue()));
		System.out.println(response.toString());
		
		assertEquals(response.get("status"), 200);
	}
	
}