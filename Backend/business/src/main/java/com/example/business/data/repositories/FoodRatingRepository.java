package com.example.business.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.business.data.entities.FoodRating;

@Repository
public interface FoodRatingRepository extends CrudRepository<FoodRating, Integer> {

	@Query(value ="SELECT * FROM food_rating r WHERE r.user_email =?1", nativeQuery = true)
	List<FoodRating> getFoodRatingsForUser(String user_email);
	
	@Query(value="SELECT r.rating FROM food_rating r WHERE r.user_email = ?1 AND r.food_id = ?2", nativeQuery = true)
	FoodRating getFoodRatingByUserAndFood(String user_email, int food_id);
	
	@Query(value = "SELECT rating FROM food_rating WHERE food_id = ?1", nativeQuery = true)
	List<Integer> findAllRatingsForFood(int food_id);
	
	@Modifying
	@Query(value = "UPDATE food_rating r SET r.rating = ?1 WHERE r.user_email = ?2 AND r.food_id ?3", 
	  nativeQuery = true)
	void updateRating(int rating, String user_email, int food_id);
	
	@Modifying
	@Query(value = "DELETE FROM food_rating WHERE r.user_email = ?1 AND r.food_id ?2", 
	  nativeQuery = true)
	void removeRating(String user_email, int food_id);
}