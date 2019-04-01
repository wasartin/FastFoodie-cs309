package com.example.business.tests;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.mockito.InjectMocks;

import com.example.business.data.controllers.RestaurantController;
import com.example.business.data.entities.Restaurant;
import com.example.business.data.repositories.RestaurantRepository;

/**
 * Tests are designed to test functionality of Restaurant Controller
 * @author Jonathan
 *
 */
public class RestaurantControllerTests {

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
		when(restRepo.findById(1)).thenReturn(new Restaurant(1, "McDonalds", now));
		
		Optional<Restaurant> rest = restCont.getRestaurant_OLD(1);

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

		List<Restaurant> restList = restCont.getRestaurants();

		assertEquals(3, restList.size());
		verify(repo, times(1)).getAccountList();
	}

	@Test
	public void createRestaurantTest() {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		List<Restaurant> list = new ArrayList<Restaurant>();
		when(restRepo.findAll()).thenReturn(list);
		
		List<Restaurant> restList = restCont.getRestaurants();
		
		assertEquals(0, restList.size());
		
		list.add(new Restaurant(1, "McDonalds", now));
		restList = restCont.getRestaurants();
		assertEquals(1, restList.size());
	}

	
}
