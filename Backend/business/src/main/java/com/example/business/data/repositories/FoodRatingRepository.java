package com.example.business.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.business.data.entities.FoodRating;

@Repository
public interface FoodRatingRepository extends CrudRepository<FoodRating, Integer> {

	//@Query(value = "select * from user where email = ?1", nativeQuery = true)
	//List<User> findByID(String email);
	
	@Query(value = "SELECT rating FROM foodRating WHERE food_id = 2", nativeQuery = true)
	default
	List<FoodRating> findAllRatingsForFood(int food_id){
		
		return null;
	}
}
