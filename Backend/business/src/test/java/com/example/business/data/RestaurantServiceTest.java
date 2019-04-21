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
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.example.business.data.entities.Restaurant;
import com.example.business.data.repositories.RestaurantRepository;
import com.example.business.data.services.RestaurantService;

public class RestaurantServiceTest {

	@InjectMocks
	RestaurantService restService;
	
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
		
		Optional<Restaurant> restO = restService.getEntityByID(1);
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

		List<Restaurant> restList = (List<Restaurant>) restService.getAllEntities();

		assertEquals(3, restList.size());
		verify(restRepo, times(1)).findAll();
	}

	@Test
	public void createRestaurantTest() {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Restaurant found = new Restaurant(1, "McDonalds", now);
		when(restRepo.save(found)).thenReturn(new Restaurant());
		
		ResponseEntity<?> response = restService.createEntity(found, found.getRestaurant_id());
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getHeaders().getContentType(), MediaType.APPLICATION_JSON);
		assertEquals(response.getBody(), found.getClass().getSimpleName());
	}

	@Test 
	public void deleteRestaurantTest() {
		Restaurant toDelete = new Restaurant(1, "McDonalds", new Timestamp(System.currentTimeMillis()));
		when(restRepo.existsById(1)).thenReturn(true);
		when(restRepo.findById(1)).thenReturn(Optional.of(toDelete));

		ResponseEntity<?> response = restService.deleteEntityById(1);
		
		verify(restRepo, times(1)).deleteById(1);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getHeaders().getContentType(), MediaType.APPLICATION_JSON);
	}
	
	@Test
	public void editRestaurantTest() {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Restaurant rest = new Restaurant(1, "McDonalds", now);
		
		when(restRepo.save(rest)).thenReturn(new Restaurant());
		when(restRepo.existsById(1)).thenReturn(true);
		
		ResponseEntity<?> response = restService.editEntity(rest, 1);

		assertThat(restRepo.save(rest), is(notNullValue()));
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getHeaders().getContentType(), MediaType.APPLICATION_JSON);
	}
	
}