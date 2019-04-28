package com.example.business.data.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
@Transactional
public class FoodService extends AbstractService<Food, Integer>{
	private int initalPage = 0;
	private int initalSize = 0;
	
	@Autowired
	FoodRepository foodRepository;

//	Page<Food> listFoodByKeyword(Pageable pageable){
//		
//	}
	
	public Page<Food> listAllByPage(Pageable pageable) {
		return foodRepository.findAll(pageable);
	}

	public Page<Food> findPaginated(int page, int size) {
		 return foodRepository.findAll(PageRequest.of(page, size));
	}
}
