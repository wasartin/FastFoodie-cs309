package com.example.business.data;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
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

import com.example.business.data.controllers.FavoritesController;
import com.example.business.data.entities.Favorites;
import com.example.business.data.repositories.FavoritesRepository;

public class FavoritesControllerTest {
	@InjectMocks
	FavoritesController favCont;
	
	@Mock
	FavoritesRepository favRepo;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getFavoritesByIdTest() {
		Favorites newFav = new Favorites();
		Optional<Favorites> favOp= Optional.of(newFav);
		when(favRepo.findById(1)).thenReturn(favOp);
		
		favCont.getfavorite_OLD(1);
		verify(favRepo, times(1)).findById(1);
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

		when(favRepo.findAll()).thenReturn(list);

		List<Favorites> favList = (List<Favorites>) favCont.getFavorites();

		assertEquals(3, favList.size());
		verify(favRepo, times(1)).findAll();
	}

	@Test
	public void createFavoritesTest() {
		Favorites found = new Favorites(3, "jongreaz@gmail.com", 34);
		when(favRepo.save(found)).thenReturn(new Favorites());
		
		JSONObject response = favCont.createFavorite(found);
		assertThat(favRepo.save(found), is(notNullValue()));
		assertEquals(response.get("HttpStatus"), HttpStatus.OK);
		assertEquals(response.get("message"), "favorite has been created");
		assertEquals(response.get("status"), 204);	
	}

	@Test 
	public void deleteFavoriteTest() {
		when(favRepo.existsById(1)).thenReturn(true);

		JSONObject response = favCont.deleteFavorite(1);
		verify(favRepo, times(1)).deleteById(1);
		assertEquals(response.get("status"), 204);
		assertEquals(response.get("HttpStatus"), HttpStatus.OK);
		assertEquals(response.get("message"), "favorite has been deleted");
	}

}
