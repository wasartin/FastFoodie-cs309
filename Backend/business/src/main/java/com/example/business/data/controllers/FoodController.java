package com.example.business.data.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.business.data.entities.Food;
import com.example.business.data.services.FoodService;

/**
 *  A (REST Api) Controller class that "receives" HTTP requests from the front end for interacting with the Food repository.
 * @author Will and Jon
 *
 */
@RestController
@RequestMapping(value="/foods")
public class FoodController {
	
	@Autowired
	FoodService foodService;

	/**
	 * returns an optional for a specified food
	 * @param food_id
	 * @return optional<food>
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/{food_id}")
	@ResponseBody
	public Optional<Food> getFood(@PathVariable int food_id){
		return foodService.getEntityByID(food_id);
	}
	
	//return pages for just all food for now.
	@RequestMapping(value="/search", method=RequestMethod.GET)
	Page<Food> listAllFood(Pageable pageable){
		Page<Food> foods = foodService.listAllByPage(pageable);
		return foods;
	} 
	
	//Better, but still annoying.
	@GetMapping(params = { "page", "size" })
	public List<Food> findPaginated(@RequestParam("page") int page, 
									@RequestParam("size") int size, 
									UriComponentsBuilder uriBuilder,
									HttpServletResponse response) throws Exception {
	    Page<Food> resultPage = foodService.findPaginated(page, size);
	    if (page > resultPage.getTotalPages()) {
	        throw new Exception();
	    }
	    return resultPage.getContent();
	}
	
	//what next?
	
	/**
	 * returns iterable for all food objects
	 * @return iterable<food>
	 */
	@GetMapping("/all")
	public Iterable<Food> getAllFoodList() {
		return foodService.getAllEntities();
	}

	/**
	 * Currently just takes food Object. Might need to be a JSONObject I parse if more info is required.
	 * @param newFood
	 * @return a response Entity 
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> createFood(@RequestBody Food newFood) {
		return foodService.createEntity(newFood, newFood.getFood_id());
	}
	
	/**
	 * Deletes the food given their unique id
	 * @param food_id
	 * @return a response Entity 
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{food_id}", produces = MediaType.APPLICATION_JSON_VALUE) 
	@ResponseBody
	public ResponseEntity<?> deleteFood(@PathVariable int food_id) {
		return foodService.deleteEntityById(food_id);
	}
	
	/**takes in a food object and edits the specified food to match the object taken in 
	 * @param food To edit
	 * @return a response Entity 
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "/edit/{food_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> editFood(@RequestBody Food newFood, @PathVariable int food_id) {
		return foodService.editEntity(newFood, food_id);
	}
	
	/**
	 * Get all the foods that contain this keyword
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/{keyword}")
	@ResponseBody
	public List<Food> getFood(@PathVariable String keyword){

		return null;
	}
	
}