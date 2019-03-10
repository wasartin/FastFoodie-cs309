package com.example.business.data.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Entity;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.business.data.entities.Favorites;
import com.example.business.data.entities.Food;
import com.example.business.data.entities.User;
import com.example.business.data.repositories.FavoritesRepository;
import com.example.business.data.repositories.FoodRepository;
import com.example.business.data.repositories.UserRepository;

/**
 * 
 * @author watis
 *
 */

@Entity
@RestController
@RequestMapping(value="/userProfile")
public class UserProfileController {
	
	//TODO Ensure this is the key that Frontend would like to see
	private final String JSON_OBJECT_RESPONSE_KEY1 = "data";
	@SuppressWarnings("unused")
	private final String JSON_OBJECT_RESPONSE_KEY3 = "favoritesList";
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	FoodRepository foodRepository;
	
	@Autowired
	FavoritesRepository favoritesRepository;
	
	
	public UserProfileController() {
		super();
	}
	
	//:SJLDFN:LKSZNF
	
	@SuppressWarnings({ "unchecked", "unlikely-arg-type" })
	@RequestMapping(method = RequestMethod.GET, path = "/{user_email}/favorites", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONArray getFoodFromFavoritesList(@PathVariable String user_email) {
		JSONArray foodInFavoritesList = new JSONArray();

		List<Favorites> fullListOfFavorites = getFavorites();
		List<Food> fullListOfFoods = getFoods();
		for(Favorites fav : fullListOfFavorites) {
			if(fav.getUser_id().equals(user_email)) {
				if(fullListOfFoods.contains(fav.getFid())) {
					foodInFavoritesList.add(fullListOfFoods.get(fav.getFid()));
				}
			}
		}
		return foodInFavoritesList;
	}
	
	@SuppressWarnings({ "unchecked", "unlikely-arg-type" })
	@RequestMapping(method = RequestMethod.GET, path = "/{user_email}/favorites/2", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONArray getFoodFromFavoritesList__OTHER(@PathVariable String user_email) {
		
		
		JSONArray foodInFavoritesList = new JSONArray();

		List<Favorites> fullListOfFavorites = getFavorites();
		List<Food> fullListOfFoods = getFoods();
		for(Favorites fav : fullListOfFavorites) {
			if(fav.getUser_id().equals(user_email)) {
				if(fullListOfFoods.contains(fav.getFid())) {
					foodInFavoritesList.add(parseFoodIntoJSONObject(fullListOfFoods.get(fav.getFid())));
				}
			}
		}
		return foodInFavoritesList;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject parseFoodIntoJSONObject(Food food) {
		final String FOOD_ID_KEY = "food_id";
		final String FOOD_NAME_KEY = "food_name";
		final String PROTEIN_KEY = "protein_total";
		final String CARB_KEY = "carb_total";
		final String FAT_KEY = "fat_total";
		final String CALORIE_KEY = "calorie_total";
		final String LOCATED_AT_KEY = "located_at";
		
		JSONObject foodAsJSONObj = new JSONObject();
		foodAsJSONObj.put(FOOD_ID_KEY, food.getFood_id());
		foodAsJSONObj.put(FOOD_NAME_KEY, food.getFood_name());
		foodAsJSONObj.put(PROTEIN_KEY, food.getProtein_total());
		foodAsJSONObj.put(CARB_KEY, food.getCarb_total());
		foodAsJSONObj.put(FAT_KEY, food.getFat_total());
		foodAsJSONObj.put(CALORIE_KEY, food.getCalorie_total());
		foodAsJSONObj.put(LOCATED_AT_KEY, food.getLocated_at());
		return foodAsJSONObj;
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

	
	private List<Favorites> getFavorites(){
		Iterable<Favorites> uIters = favoritesRepository.findAll();
		List<Favorites> uList = new ArrayList<Favorites>();
		uIters.forEach(uList::add);
		return uList;
	}
	
	private List<Food> getFoods(){
		Iterable<Food> uIters = foodRepository.findAll();
		List<Food> uList = new ArrayList<Food>();
		uIters.forEach(uList::add);
		return uList;
	}


	//TODO THIS IS NEW
	//getting favorites from a specific user
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/user/{user_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONArray getAllFavorites(@PathVariable String user_id) {
		JSONArray usersFavorites = new JSONArray();
		List<Favorites> fullList = getFavorites();
		for(Favorites fav : fullList) {
			if(fav.getUser_id().equals(user_id)) {
				usersFavorites.add(parsefavoriteIntoJSONObject(fav));
			}
		}
		return usersFavorites;
	}

	
	//:SJLDFN:LKSZNF
	
	@RequestMapping(method = RequestMethod.GET, path = "/{user_email}")
	@ResponseBody
	public Optional<User> getUser(@PathVariable String user_email){
		return userRepository.findById(user_email);
	}
	
	/**
	 * TODO: The User's favorites list will be added here as well. It will be the second key in this response=
	 * @param user_email
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, path = "/{user_email}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONObject getUserJSONObject(@PathVariable String user_email) {//TODO will just be changed to getUser once conversion is complete
		Optional<User> temp = userRepository.findById(user_email);
		JSONObject response = new JSONObject();
		response.put(JSON_OBJECT_RESPONSE_KEY1, temp.get());
		return response;
	}
	
	/**
	 * This private helper method is used to pull all the users from the Data base so it is easier to parse into a JSONObject
	 * @return List<User>
	 */
	private List<User> getUsers(){
		Iterable<User> uIters = userRepository.findAll();
		List<User> uList = new ArrayList<User>();
		uIters.forEach(uList::add);
		return uList;
	}
	
	/**
	 * This is a private helper method that parses the Backend's version of a user into a JSON object, 
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private JSONObject parseUserIntoJSONObject(User user) {
		final String USER_EMAIL_KEY = "user_email";
		final String USER_TYPE_KEY = "user_type";
		JSONObject userAsJSONObj = new JSONObject();
		userAsJSONObj.put(USER_EMAIL_KEY, user.getUser_email());
		userAsJSONObj.put(USER_TYPE_KEY, user.getUser_type());
		return userAsJSONObj;
	}
	
	/**
	 * Deletes the user given their unique id
	 * @param user_email
	 * @return
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{user_email}", produces = MediaType.APPLICATION_JSON_VALUE) 
	@ResponseBody
	private JSONObject deleteUser(@PathVariable String user_email) {
		JSONObject response;
		try {
			if(!userRepository.existsById(user_email)) {//Checks to see if User is even in the DB
				throw new IllegalArgumentException();
			}
			userRepository.deleteById(user_email);
			response = generateResponse(204, HttpStatus.OK, "User has been deleted");
		}catch (IllegalArgumentException e) {
			response = generateResponse(400, HttpStatus.BAD_REQUEST, "Could not find that user in the database, or your fields are incorrect, double check your request");
		}catch (Exception e) {
			response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
		}
		return response;
	}
	
	/**
	 * JSONObject. 1st key is old
	 * 2nd key is new
	 * PUT is for update
	 * TODO: When a user wants to change emails, will need to switch over favorites list
	 * @param userToEdit
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "/edit/{user_email}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	private JSONObject editUser(@RequestBody User newUserInfo, @PathVariable String user_email) {
		JSONObject response;
		try {
			if(!userRepository.existsById(user_email)) {//pretty sure that is how I want to do this.
				throw new IllegalArgumentException();
			}
			userRepository.save(newUserInfo);//this will edit the user
			response = generateResponse(200, HttpStatus.OK, "User has been edited");
		}catch (IllegalArgumentException e) {
			response = generateResponse(400, HttpStatus.BAD_REQUEST, "Could not find that user in the database, or your fields are incorrect, double check your request");
		}catch (Exception e) {
			response = generateResponse(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server might be down now. Try again");
		}
		return response;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject generateResponse(int status, HttpStatus input, String message) {
		JSONObject response = new JSONObject();
		response.put("status", status);
		response.put("HttpStatus", input);
		response.put("message", message);
		return response;
	}
}