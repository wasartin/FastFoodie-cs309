package com.example.business.data.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.business.data.entities.Food;

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
	

	//FILTERING
	//{GT/LT/EQ}, {NUMBER}, {FOOD int} (protein, calorie, etc), can have one arg, or 1+
	
	//SORTING
	//{[ONE ARG]} OR {[ARG_ONE} : {ARG_TWO}]
	//apprently the code already makes my queiers?
	
	Page<Food> findByCalorieLessThan(int maxCal, Pageable pageable);
	
	Page<Food> findByCarbLessThan(int maxCarb, Pageable pageable);
	

}
