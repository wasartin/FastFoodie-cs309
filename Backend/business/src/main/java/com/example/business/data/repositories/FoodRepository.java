package com.example.business.data.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import com.example.business.data.entities.Food;
import com.querydsl.core.annotations.QueryDelegate;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanPath;

/**
 * The food repository is the layer that interfaces with the database. It uses the favorites dao and performs general
 * and specific function calls to the database.
 * @author watis
 *
 */
@Repository
public interface FoodRepository extends PagingAndSortingRepository<Food, Integer>{
	
	/**
	 * Finds foods that contain the keyword
	 * @param keyword
	 * @return a list of foods that contain the keyword
	 */
	@Query(value =
			"SELECT *"
			+" FROM food"
			+" WHERE fname LIKE %?1%", nativeQuery = true)
	Page<Food> getFoodListWithKeyword(String keyword, Pageable pageable);//wondering if all args must be page

}
