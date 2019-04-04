package com.example.business.tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import com.example.business.data.controllers.RestaurantController;
import com.example.business.data.entities.Favorites;
import com.example.business.data.entities.Restaurant;
import com.example.business.data.repositories.RestaurantRepository;

public class FavoritesControllerTests {
	@InjectMocks
	FavoritesController restCont;
	
	@Mock
	RestaurantRepository restRepo;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}


	@Test
	public void getFavoritesByIdTest() {
		when(restRepo.findById(1)).thenReturn(Optional.of(new Favorites(1,"jongreaz@gmail.com",214)));
		
		Optional<Favorites> favO = restCont.getfavorite_OLD(1);
		Favorites fav = favO.get();
		assertEquals(1, fav.getFavorites_id());
		assertEquals("jongreaz@gmail.com", fav.getUser_id());
		assertEquals(214, fav.getFid());
	}
	
	@Test
	public void getAllFavoritesTest() {
		List<Favorites> list = new ArrayList<Favorites>();
		Favorites favOne = new Favorites(1, "jongreaz@gmail.com", 98);
		Favorites favTwo = new Favorites(2, "jongreaz@gmail.com", 45);
		Favorites favThree = new Favorites(3, "jongreaz@gmail.com", 34);

		list.add(favOne);
		list.add(favTwo);
		list.add(favThree);

		when(restRepo.findAll()).thenReturn(list);

		List<Favorites> favList = (List<Favorites>) restCont.getFavorites();

		assertEquals(3, favList.size());
		verify(restRepo, times(1)).findAll();
	}

	@Test
	public void createFavoritesTest() {
		Favorites found = new Favorites(3, "jongreaz@gmail.com", 34);
		when(restRepo.save(found)).thenReturn(new Favorites());
		
		JSONObject response = restCont.createFavorite(found);
		assertThat(restRepo.save(found), is(notNullValue()));
		assertEquals(response.get("HttpStatus"), HttpStatus.OK);
		assertEquals(response.get("message"), "favorite has been created");
		assertEquals(response.get("status"), 204);	
	}

	@Test 
	public void deleteFavoriteTest() {
		when(restRepo.existsById(1)).thenReturn(true);

		JSONObject response = restCont.deleteFavorite(1);
		verify(restRepo, times(1)).deleteById(1);
		assertEquals(response.get("status"), 204);
		assertEquals(response.get("HttpStatus"), HttpStatus.OK);
		assertEquals(response.get("message"), "favorite has been deleted");
	}

}
