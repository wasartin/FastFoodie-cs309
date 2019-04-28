package com.example.business.data.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpEntity;
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
	
	private final int PAGE_SIZE = 10;
	
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
	
	/**
	 * Returns all of the food in the database to the client in pages.
	 * @param pageable
	 * @return a page of food
	 */
	@RequestMapping(value="/search", method=RequestMethod.GET)
	Page<Food> listAllFood(@PageableDefault(size = PAGE_SIZE) Pageable pageable){
		Page<Food> foods = foodService.listAllByPage(pageable);
		return foods;
	} 
	
	/**
	 * Finds a food by it's keyword and returns a page to the client
	 * @param keyword
	 * @param pageable
	 * @return page of food
	 */
	@RequestMapping(value="/search/keyword/{keyword}", method=RequestMethod.GET)
	Page<Food> listFoodWithKeyword(@PathVariable String keyword, @PageableDefault(size = PAGE_SIZE, sort="food_name") Pageable pageable){
		Page<Food> foods = foodService.listFoodWithKeyword(keyword, pageable);
		return foods;
	} 
	
	//sort by calories
	@RequestMapping(value="/search/num", method=RequestMethod.GET)
	Page<Food> listFoodByNum(@RequestParam(value = "searchTerm", required = false) String searchTerm,
											@PageableDefault Pageable p){
		//PageRequest.of(page, size, direction, properties)
		Page<Food> result = foodService.listAllByPage(PageRequest.of(p.getPageNumber(), p.getPageSize(), sortByArgumentDesc(searchTerm)));
		return result;
	} 

	@GetMapping(params = { "protein_total", "carb_total", "fat_total", "calorie_total", "carb_total", "price", "category", "located_at", "rating", "rating_count" })
	public List<Food> findPaginated(
									@RequestParam("page") int page, 
									//@RequestParam(value = "page", required =false, defaultValue="0") int page, 
									@RequestParam(value = "size", required=false) int size,
									@RequestParam(value = "protein_total", required=false) int protein_total, 
									@RequestParam(value = "carb_total", required=false) int carb_total, 
									@RequestParam(value = "fat_total", required=false) int fat_total, 
									@RequestParam(value = "calorie_total", required=false) int calorie_total, 
									@RequestParam(value = "price", required=false) int price, 
									@RequestParam(value = "category", required=false) int category, 
									@RequestParam(value = "located_at", required=false) int located_at, 
									@RequestParam(value = "rating", required=false) int rating, 
									@RequestParam(value = "rating_count", required=false) int rating_count, 
									UriComponentsBuilder uriBuilder,
									HttpServletResponse response) throws Exception {
		String selected;
		//sortByArgumentDesc
	    Page<Food> resultPage = foodService.findPaginated(page, size);
	    if (page > resultPage.getTotalPages()) {
	        throw new Exception();
	    }
	    return resultPage.getContent();
	}
	
	@RequestMapping(value="/search/num/other", method=RequestMethod.GET)
	Page<Food> other(@RequestParam(value = "searchTerm", required = false) String searchTerm,
											@PageableDefault Pageable p){
		Page<Food> result = foodService.listAllByPage(PageRequest.of(p.getPageNumber(), p.getPageSize(), sortByArgumentDesc(searchTerm)));
		return result;
	} 

	@RequestMapping(value = "/conditionalPagination", method = RequestMethod.GET)
	public Page<Food> somethingNew(@RequestParam(value="property", required=false) String property,
									@RequestParam(value="direction", required=false) Optional<String> direction, 
									@RequestParam("page") int page,
									@RequestParam("size") int size) {
	Sort.Direction wayToGo = Sort.Direction.fromString(direction.orElse("desc"));
	Page<Food> list = foodService.somethingNew(property, wayToGo, page, size);
	return list;
	}
	
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
		return foodService.createEntity(newFood, newFood.getFid());
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
	
	private Sort sortByArgumentDesc(String input) {
		return new Sort(Sort.Direction.DESC, input);
	}
	
}