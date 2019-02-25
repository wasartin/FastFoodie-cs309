package com.example.business.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.business.data.entities.Restaurant;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant,Integer> {

}
