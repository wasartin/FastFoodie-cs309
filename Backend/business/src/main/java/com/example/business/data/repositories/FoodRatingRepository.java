package com.example.business.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.business.data.entities.FoodRating;

@Repository
public interface FoodRatingRepository extends CrudRepository<FoodRating, Integer> {

	@Query(value ="SELECT * FROM foodRating r WHERE r.user_email =?1", nativeQuery = true)
	List<FoodRating> getFoodRatingsForUser(String user_email);
	
	@Query(value="SELECT r.rating FROM foodRating r WHERE r.user_email = ?1 AND r.food_id = ?2", nativeQuery = true)
	FoodRating getFoodRatingByUserAndFood(String user_email, int food_id);
	
	@Query(value = "SELECT rating FROM foodRating WHERE food_id = ?1", nativeQuery = true)
	List<Double> findAllRatingsForFood(int food_id);
}