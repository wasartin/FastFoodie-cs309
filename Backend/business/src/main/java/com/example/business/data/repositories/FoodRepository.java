package com.example.business.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.business.data.entities.Food;

/**
 * The food repository is the layer that interfaces with the database. It uses the favorites dao and performs general
 * and specific function calls to the database.
 * @author watis
 *
 */
@Repository
public interface FoodRepository extends CrudRepository<Food, Integer>{
	
	
}
