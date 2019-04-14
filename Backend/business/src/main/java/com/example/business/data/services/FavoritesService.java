package com.example.business.data.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.business.data.entities.Favorites;
import com.example.business.data.repositories.FavoritesRepository;

@Service
public class FavoritesService {
	
	private final String JSON_OBJECT_RESPONSE_KEY1 = "data";

	@Autowired 
	FavoritesRepository favoritesRepository;
	
	public Optional<Favorites> getfavorite_OLD(int fav_id){
		return favoritesRepository.findById(fav_id);
	}
	
	public Iterable<Favorites> getAllfavorite_OLD() {
		return favoritesRepository.findAll();
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getfavoriteJSONObject(int favorites_id) {
		JSONObject response = new JSONObject();
		JSONArray listOfFavorites = new JSONArray();
		List<Favorites> fullListOfFavorites = getFavorites();
		for(Favorites fav : fullListOfFavorites) {
			if(fav.getFavorites_id() == favorites_id) {
				listOfFavorites.add(fav);
			}
		}
		response.put("data", listOfFavorites);
		return response;
	}
	
	/**
	 * This private helper method is used to pull all the favorite from the Data base so it is easier to parse into a JSONObject
	 * @return List<favorite>
	 */
	private List<Favorites> getFavorites(){
		Iterable<Favorites> uIters = favoritesRepository.findAll();
		List<Favorites> uList = new ArrayList<Favorites>();
		uIters.forEach(uList::add);
		return uList;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject parsefavoriteIntoJSONObject(Favorites favorite) {
		final String favorite_USER_ID_KEY = "user_id";
		final String favorite_fid_KEY = "fid";
		
		JSONObject favoriteAsJSONObj = new JSONObject();
		favoriteAsJSONObj.put(favorite_USER_ID_KEY, favorite.getUser_id());
		favoriteAsJSONObj.put(favorite_fid_KEY, favorite.getFid());
		favoriteAsJSONObj.put("favorites_id", favorite.getFavorites_id());
		return favoriteAsJSONObj;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getAllFavoritesJSONObject()  {
		JSONObject toReturn = new JSONObject();
		String key1 = JSON_OBJECT_RESPONSE_KEY1;
		JSONArray listOfFavorites = new JSONArray();
		List<Favorites> uList = getFavorites();
		for(Favorites favorite : uList) {
			listOfFavorites.add(parsefavoriteIntoJSONObject(favorite));
		}
		toReturn.put(key1, listOfFavorites);
		return toReturn;
	}
	
	@SuppressWarnings("unchecked")
	public JSONArray getAllFavorites(String user_id) {
		JSONArray usersFavorites = new JSONArray();
		List<Favorites> fullList = getFavorites();
		for(Favorites fav : fullList) {
			if(fav.getUser_id().equals(user_id)) {
				usersFavorites.add(parsefavoriteIntoJSONObject(fav));
			}
		}
		return usersFavorites;
	}
	
	private boolean alreadyExists(Favorites newFav) {
		List<Favorites> fullListOfFavorites = getFavorites();
		for(Favorites fav : fullListOfFavorites) {
			if(fav.getUser_id().equals(newFav.getUser_id()) && fav.getFid()== newFav.getFid()) {
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject generateResponse(int status, HttpStatus input, String message) {
		JSONObject response = new JSONObject();
		response.put("status", status);
		response.put("HttpStatus", input);
		response.put("message", message);
		return response;
	}
	
	public JSONObject createFavorite(Favorites newfavorite) {
		JSONObject response;
		try {
			if(favoritesRepository.existsById(newfavorite.getFavorites_id())) {
				throw new IllegalArgumentException();
			}
			if(alreadyExists(newfavorite)) {
				throw new IllegalArgumentException();
			}
			favoritesRepository.save(newfavorite);
			response = generateResponse(204, HttpStatus.OK, "favorite has been created");
		}catch (IllegalArgumentException e) {
			response = generateResponse(400, HttpStatus.BAD_REQUEST, "favorite might already exist, or your fields are incorrect, double check your request");
		}catch (Exception e) {
			response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
		}
		return response;
	}
	
	public JSONObject createFavoriteForUser(String user_id, int fid) {
		Favorites newFav = new Favorites(user_id, fid);
		JSONObject response;
		try {
			if(alreadyExists(newFav)) {
				throw new IllegalArgumentException();
			}
			favoritesRepository.save(newFav);
			response = generateResponse(204, HttpStatus.OK, "favorite has been created");
		}catch (IllegalArgumentException e) {
			response = generateResponse(400, HttpStatus.BAD_REQUEST, "favorite might already exist, or your fields are incorrect, double check your request");
		}catch (Exception e) {
			response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
		}
		return response;
	}
	
	public JSONObject deleteFavorite(int favorites_id) {
		JSONObject response;
		try {
			if(!favoritesRepository.existsById(favorites_id)) {
				throw new IllegalArgumentException();
			}
			favoritesRepository.deleteById(favorites_id);
			response = generateResponse(204, HttpStatus.OK, "favorite has been deleted");
		}catch (IllegalArgumentException e) {
			response = generateResponse(400, HttpStatus.BAD_REQUEST, "Could not find that favorite in the database, or your fields are incorrect, double check your request");
		}catch (Exception e) {
			response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
		}
		return response;
	}
	
	public JSONObject deleteFavoriteByUser(String user_id, int fid) {
		JSONObject response;
		boolean found = false;
		try {
			List<Favorites> fullListOfFavorites = getFavorites();
			for(Favorites fav : fullListOfFavorites) {
				if(fav.getUser_id().equalsIgnoreCase(user_id) && fav.getFid()==fid) {
					favoritesRepository.deleteById(fav.getFavorites_id());
					found = true;
					break;
				}
			}
			if(!found) {
				throw new IllegalArgumentException();
			}
			response = generateResponse(204, HttpStatus.OK, "favorite has been deleted");
		}catch (IllegalArgumentException e) {
			response = generateResponse(400, HttpStatus.BAD_REQUEST, "Could not find that favorite in the database, or your fields are incorrect, double check your request");
		}catch (Exception e) {
			response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
		}
		return response;
	}
}
