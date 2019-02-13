package entities.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import entities.data.Restaurant;

@Repository
public interface RestaurantRespository extends CrudRepository<Restaurant, Long>{
	Restaurant findByName(String input);

}
