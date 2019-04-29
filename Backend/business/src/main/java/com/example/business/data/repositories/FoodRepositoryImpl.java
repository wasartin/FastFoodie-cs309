package com.example.business.data.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.business.data.entities.Food;

public class FoodRepositoryImpl implements FoodRepositoryCustom {

	@Override
	public Page<Food> getFoodListWithKeyword(String keyword, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
