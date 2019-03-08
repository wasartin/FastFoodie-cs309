package com.example.business.data.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.business.data.entities.Favorites;

public interface FavoritesRepository extends CrudRepository<Favorites, Integer> {

}
