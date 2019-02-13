package entities.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import entities.data.Food;

@Repository
public interface FoodRepository extends PagingAndSortingRepository<Food, Long>{
	
}
