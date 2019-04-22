package com.example.business.data;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
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

import com.example.business.data.entities.Favorites;
import com.example.business.data.repositories.FavoritesRepository;
import com.example.business.data.services.FavoritesService;

public class FavoritesServiceTest {
	
	@InjectMocks
	FavoritesService favService;
	
	@Mock
	FavoritesRepository repo;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getFavoritesByIdTest() {
		Favorites newFav = new Favorites();
		Optional<Favorites> favOp= Optional.of(newFav);
		when(repo.findById(1)).thenReturn(favOp);
		
		favService.getEntityByID(1);
		verify(repo, times(1)).findById(1);
	}
	
	@Test
	public void getFavoritesByIdTest_Fail() {
		when(repo.existsById(1)).thenReturn(false);
		
		favService.getEntityByID(1);
		verify(repo, never()).existsById(1);
	}
	
	@Test
	public void getFavoritesByUserIdAndFidTest() {
		Favorites newFav = new Favorites(1, "jongreaz@gmail.com", 98);
		when(repo.favorite_by_user_and_food(newFav.getUser_id(), newFav.getFid())).thenReturn(newFav);
		
		favService.getFavoriteByUserAndFood(newFav.getUser_id(), newFav.getFid());
		verify(repo, times(1)).favorite_by_user_and_food(newFav.getUser_id(), newFav.getFid());
	}
	
	@Test
	public void getFavoritesByUserIdAndFidTest_Fail() {
		Favorites newFav = new Favorites(1, "jongreaz@gmail.com", 3000);
		when(repo.favorite_by_user_and_food(newFav.getUser_id(), newFav.getFid())).thenReturn(null);
		
		favService.getFavoriteByUserAndFood(newFav.getUser_id(), newFav.getFid());
		verify(repo, times(1)).favorite_by_user_and_food(newFav.getUser_id(), newFav.getFid());
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

		when(repo.findAll()).thenReturn(list);

		List<Favorites> favList = (List<Favorites>) favService.getAllEntities();

		assertEquals(3, favList.size());
		verify(repo, times(1)).findAll();
	}
	
	@Test
	public void getAllFavoritesForUserTest() {
		List<Favorites> list = new ArrayList<Favorites>();
		Favorites favOne = new Favorites(1, "jongreaz@gmail.com", 98);
		Favorites favTwo = new Favorites(2, "jongreaz@gmail.com", 45);
		Favorites favThree = new Favorites(3, "jongreaz@gmail.com", 34);

		list.add(favOne);
		list.add(favTwo);
		list.add(favThree);

		when(repo.favorites_For_User(favOne.getUser_id())).thenReturn(list);

		List<Favorites> favList = (List<Favorites>) favService.getAllFavoritesForUser(favOne.getUser_id());
		assertEquals(3, favList.size());
		verify(repo, times(1)).favorites_For_User(favOne.getUser_id());
	}

	@Test
	public void createFavoritesTest() {
		Favorites found = new Favorites(3, "jongreaz@gmail.com", 34);
		when(repo.save(found)).thenReturn(new Favorites());
		
		ResponseEntity<?> response = favService.createEntity(found, found.getFavorites_id());
		assertThat(repo.save(found), is(notNullValue()));
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getHeaders().getContentType(), MediaType.APPLICATION_JSON);
		assertEquals(response.getBody(), found.getClass().getSimpleName());
	}
	
	@Test
	public void createFavoritesTest_Fail() {
		Favorites alreadyInDB = new Favorites(3, "jongreaz@gmail.com", 34);
		
		when(repo.existsById(alreadyInDB.getFavorites_id())).thenReturn(true);
		when(repo.save(alreadyInDB)).thenReturn(null);
		
		ResponseEntity<?> response = favService.createEntity(alreadyInDB, alreadyInDB.getFavorites_id());
		
		assertThat(repo.save(alreadyInDB), is(nullValue()));
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(response.getHeaders().getContentType(), MediaType.APPLICATION_JSON);
		assertEquals(response.getBody(), alreadyInDB.getClass().getSimpleName());
	}

	@Test 
	public void deleteFavoriteTest() {
		Favorites toDelete = new Favorites(3, "jongreaz@gmail.com", 34);
		
		when(repo.existsById(toDelete.getFavorites_id())).thenReturn(true);
		when(repo.findById(toDelete.getFavorites_id())).thenReturn(Optional.of(toDelete));

		ResponseEntity<?> response = favService.deleteEntityById(toDelete.getFavorites_id());
		verify(repo, times(1)).deleteById(toDelete.getFavorites_id());
		
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getHeaders().getContentType(), MediaType.APPLICATION_JSON);
	}

	@Test 
	public void deleteFavoriteTest_Fail() {
		Favorites toDelete = new Favorites(3, "jongreaz@gmail.com", 34);
		
		when(repo.existsById(1)).thenReturn(false);

		ResponseEntity<?> response = favService.deleteEntityById(toDelete.getFavorites_id());
		verify(repo, never()).deleteById(toDelete.getFavorites_id());
		
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(response.getHeaders().getContentType(), MediaType.APPLICATION_JSON);
	}
	
	@Test
	public void deleteFavoritesTest_Exception() {
		Favorites toDelete = new Favorites(3, "jongreaz@gmail.com", 34);
		when(repo.existsById(1)).thenThrow(IllegalArgumentException.class);
		
		ResponseEntity<?> response = favService.deleteEntityById(toDelete.getFavorites_id());
		verify(repo, never()).deleteById(toDelete.getFavorites_id());

		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(response.getHeaders().getContentType(), MediaType.APPLICATION_JSON);
	}
	
	@Test
	public void editFavoritesTest() {
		Favorites alreadyInDB = new Favorites(3, "jongreaz@gmail.com", 34);
		
		when(repo.save(alreadyInDB)).thenReturn(new Favorites());
		when(repo.existsById(alreadyInDB.getFavorites_id())).thenReturn(true);

		ResponseEntity<?> response = favService.editEntity(alreadyInDB, alreadyInDB.getFavorites_id());

		verify(repo, times(1)).save(alreadyInDB);
		assertThat(repo.save(alreadyInDB), is(notNullValue()));
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getHeaders().getContentType(), MediaType.APPLICATION_JSON);
		assertEquals(response.getBody(), alreadyInDB.getClass().getSimpleName());
	}
	
	@Test
	public void editFavoritesTest_Fail() {
		Favorites notInDB = new Favorites(3, "jongreaz@gmail.com", 34);
		when(repo.existsById(notInDB.getFavorites_id())).thenReturn(false);

		ResponseEntity<?> response = favService.editEntity(notInDB, notInDB.getFavorites_id());

		verify(repo, never()).save(notInDB);
		
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(response.getHeaders().getContentType(), MediaType.APPLICATION_JSON);
		assertEquals(response.getBody(), notInDB.getClass().getSimpleName());
	}
}
