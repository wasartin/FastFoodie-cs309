package com.example.business.data.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@Transactional//new, may really mess things up
public class FoodService extends AbstractService<Food, Integer>{
	
	final FoodRepository foodRepository;
	
	@Autowired
	FoodService(FoodRepository foodRepository){
		this.foodRepository = foodRepository;
	}

//	Page<Food> listFoodByKeyword(Pageable pageable){
//		
//	}
	
	public Page<Food> listAllByPage(Pageable pageable) {
		 return foodRepository.findAll(pageable);
	}
}
