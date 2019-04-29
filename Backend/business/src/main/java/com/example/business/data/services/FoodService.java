package com.example.business.data.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.business.data.entities.Food;
import com.example.business.data.repositories.FoodRepository;

/**
 * The Food service class is where the bulk of the business logic is. 
 * This is the layer that will interface with its repository. 
 * @author watis
 *
 */
@Service
@Transactional
public class FoodService extends AbstractService<Food, Integer>{

	@Autowired
	FoodRepository foodRepository;
	
	public Page<Food> getQuery(String property, Sort.Direction direction, int page, int size){
		Sort howToSort = new Sort(direction, property);
		Pageable pageable = PageRequest.of(page, size, howToSort);
		return foodRepository.findAll(pageable);
	}

	public Page<Food> listFoodWithKeyword(String keyword, Pageable pageable){
		return foodRepository.getFoodListWithKeyword(keyword, pageable);
	}
	
	public Page<Food> listAllByPage(Pageable pageable) {
		return foodRepository.findAll(pageable);
	}
	
	public Page<Food> listCaloriesLessThan(int max, Pageable pageable) {
		//return foodRepository.findByCalorieLessThanDesc_maxCal(max, pageable);
		return null;
	}
//
	public Page<Food> findPaginated(int page, int size) {
		return foodRepository.findAll(PageRequest.of(page, size));
	}
	
	public Page<Food> findPaginatedAndSort(int page, int size, Sort sort) {
		return foodRepository.findAll(PageRequest.of(page, size, sort));
	}
	
	public Page<Food> propertySearch(String property, Sort.Direction direction, int page, int size){
		Sort howToSort = new Sort(direction, property);
		Pageable pageable = PageRequest.of(page, size, howToSort);
		return foodRepository.findAll(pageable);
	}
	
	public Page<Food> orderBy(Sort sort, int page, int size){
		Pageable pageable = PageRequest.of(page, size, sort);
		return foodRepository.findAll(pageable);
	}
}
