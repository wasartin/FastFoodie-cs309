package com.example.business.data;

import static org.mockito.Mockito.atLeast;
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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.example.business.data.controllers.ApiController;
import com.example.business.data.entities.Favorites;
import com.example.business.data.entities.Food;
import com.example.business.data.entities.User;
import com.example.business.data.repositories.FavoritesRepository;
import com.example.business.data.repositories.FoodRepository;
import com.example.business.data.services.FavoritesService;
import com.example.business.data.services.FoodService;

public class ApiControllerTest {
	
	@InjectMocks
	ApiController apiController;
	
	@Mock
	FoodService foodServ;
	
	@Mock
	FavoritesService favServ;
	
	@Mock
	FoodRepository repoFood;
	
	@Mock 
	FavoritesRepository repoFav;
	
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		
	}
	
	@Test
	public void getUsersFavoriteFoods() {
		List<Food> found = new ArrayList<Food>();
		Favorites aFav = new Favorites(0, "BigBird@SesameStreet.edu", 64);
		List<Favorites> favList = new ArrayList<Favorites>();
		favList.add(aFav);
		
		User user = new User("BigBird@SesameStreet.edu", "admin");
		List<Favorites> usersFavorites = new ArrayList<Favorites>(); //getAllFavoritesForUser 
		Food foodById = new Food(64, "Royal with Cheese", 31, 42, 28, 540, "$5.00", "Beef", 0, 0, 0);
		found.add(foodById);
		
		when(favServ.getAllFavoritesForUser(user.getUser_email())).thenReturn(favList);
		when(foodServ.getEntityByID(favList.get(0).getFid())).thenReturn(Optional.of(foodById));

		List<Food> result = apiController.getUsersFavoriteFoods(user.getUser_email());
		verify(favServ, times(1)).getAllFavoritesForUser(user.getUser_email());
	}
	
	@Test
	public void getUsersFavoriteFoods_Fail() {
		User user = new User("BigBird@SesameStreet.edu", "admin");
		when(favServ.getAllFavoritesForUser(user.getUser_email())).thenReturn(new ArrayList<Favorites>());

		List<Food> result = apiController.getUsersFavoriteFoods(user.getUser_email());
		verify(favServ, times(1)).getAllFavoritesForUser(user.getUser_email());
		verify(foodServ, never()).getEntityByID(10);
	}
}
	
