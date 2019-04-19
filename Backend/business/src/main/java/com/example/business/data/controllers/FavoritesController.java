package com.example.business.data.controllers;

import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.business.data.entities.Favorites;
import com.example.business.data.services.FavoritesService;

@RestController
@RequestMapping(value="/favorites")
public class FavoritesController {
	
	@Autowired
	FavoritesService favoritesService;
	
	@RequestMapping(method = RequestMethod.GET, path = "/{fav_id}")
	@ResponseBody
	public Optional<Favorites> getfavorite(@PathVariable int fav_id){
		return favoritesService.getfavorite(fav_id);
	}

	@GetMapping("/all")
	public Iterable<Favorites> getAllfavoritesList() {
		return favoritesService.getAllfavorite();
	}

	/**
	 * Returns a favorite JSON object for the specified favorites_id
	 * @param favorite_id
	 * @return JSONObject for favorite
	 */
	@RequestMapping(method = RequestMethod.GET, path = "json/{favorites_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONObject getfavoriteJSONObject(@PathVariable int favorites_id) {
		return favoritesService.getfavoriteJSONObject(favorites_id);
	}

	/**
	 * 
	 * @return JSONObject 
	 */
	@RequestMapping(value = "json/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject getAllFavoritesJSONObject()  {
		return favoritesService.getAllFavoritesJSONObject();
	}
	
	/**
	 * getting favorites from a specific user
	 * @param user_id
	 * @return
	 */
	@RequestMapping(value = "/user/{user_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONArray getAllFavorites(@PathVariable String user_id) {
		return favoritesService.getAllFavorites(user_id);
	}
	
	/**
	 * Currently just takes favorite Object. Might need to be a JSONObject I parse if more info is required.
	 * @param newUser
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONObject createFavorite(@RequestBody Favorites newfavorite) {
		return favoritesService.createFavorite(newfavorite);
	}
	
	/**
	 * @param newUser
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/create/{user_id}/{fid}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONObject createFavoriteForUser(@PathVariable String user_id, @PathVariable int fid) {
		return favoritesService.createFavoriteForUser(user_id, fid);
	}
	
	
	/**
	 * Deletes the favorite given their unique id
	 * @param favorite_id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{favorites_id}", produces = MediaType.APPLICATION_JSON_VALUE) 
	@ResponseBody
	public JSONObject deleteFavorite(@PathVariable int favorites_id) {
		return favoritesService.deleteFavorite(favorites_id);
	}
	
	/**
	 * Deletes the favorite given their user and food 
	 * @param favorite_id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{user_id}/{fid}", produces = MediaType.APPLICATION_JSON_VALUE) 
	@ResponseBody
	public JSONObject deleteFavoriteByUser(@PathVariable String user_id, @PathVariable int fid) {
		return favoritesService.deleteFavoriteByUser(user_id, fid);
	}

}