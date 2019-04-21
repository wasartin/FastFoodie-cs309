package com.example.business.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.business.data.entities.Favorites;

public interface FavoritesRepository extends CrudRepository<Favorites, Integer> {

	@Query(value ="SELECT * FROM favorites f WHERE f.user_id =?1", nativeQuery = true)
	List<Favorites> favorites_For_User(String user_email);
	
	@Query(value ="SELECT * FROM favorites f WHERE f.user_id =?1 AND f.fid = ?2", nativeQuery = true)
	Favorites favorite_by_user_and_food(String user_email, int food_id);
	
}
