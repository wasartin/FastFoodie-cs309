package com.example.business.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.business.data.entities.Favorites;
import com.example.business.data.repositories.FavoritesRepository;

@Service
public class FavoritesService extends AbstractService<Favorites, Integer>{
	@Autowired 
	FavoritesRepository favoritesRepository;
	
	public List<Favorites> getAllFavoritesForUser(String user_id) {
		return favoritesRepository.favorites_For_User(user_id);
	}
	
	public Favorites getFavoriteByUserAndFood(String user_email, int food_id) {
		return favoritesRepository.favorite_by_user_and_food(user_email, food_id);
	}

}