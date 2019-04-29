package com.example.business.data.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import com.example.business.data.entities.Food;
import com.querydsl.core.annotations.QueryDelegate;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanPath;

/**
 * The food repository is the layer that interfaces with the database. It uses the favorites dao and performs general
 * and specific function calls to the database.
 * @author watis
 *
 */
@Repository
public interface FoodRepository extends PagingAndSortingRepository<Food, Integer>,
										QueryByExampleExecutor<Food>,
										QuerydslPredicateExecutor<Food>{
	
	/**
	 * Finds foods that contain the keyword
	 * @param keyword
	 * @return a list of foods that contain the keyword
	 */
	@Query(value =
			"SELECT *"
			+" FROM food"
			+" WHERE fname LIKE %?1%", nativeQuery = true)
	Page<Food> getFoodListWithKeyword(String keyword, Pageable pageable);//wondering if all args must be page
	//FILTERING
	//{GT/LT/EQ}, {NUMBER}, {FOOD int} (protein, calorie, etc), can have one arg, or 1+
//	@QueryDelegate(Food.class)
//	public static BooleanPath isManagedBy(QFood f, Food other){
//	    return Food.manager.eq(other);
//	}   
	//SORTING
	//{[ONE ARG]} OR {[ARG_ONE} : {ARG_TWO}]
	//apprently the code already makes my queiers?
	
	Page<Food> generalQuery(Pageable pageable);

	
	
	Page<Food> findByCalorieLessThan(int max, Pageable p);	Page<Food> findByCalorieGreaterThan(int min, Pageable p);
	Page<Food> findByCarbLessThan(int max, Pageable p);		Page<Food> findByCarbGreaterThan(int min, Pageable p);
	Page<Food> findByFatLessThan(int max, Pageable p);		Page<Food> findByFatGreaterThan(int min, Pageable p);
	Page<Food> findByProteinLessThan(int max, Pageable p);	Page<Food> findByProteinGreaterThan(int max, Pageable p);
	Page<Food> findByPriceLessThan(int max, Pageable p);	Page<Food> findByPriceGreaterThan(int min, Pageable p);
	
	Page<Food> genQ(Predicate predicate, Pageable pageable);
	
	//fid
	//fname
	//protein
	///carb
	//fat
	//calorie
	//price
	//category
	//located
	//rating
	//rated
	final String FID = "fid";
	final String FNAME="fname";
	final String PROTEIN = "protein";
	final String CARB = "carb";
	final String FAT = "fat";
	final String CALORIE = "calorie";
	final String PRICE = "price";
	final String LESS_THAN_EQ = "<=";
	final String GREATHER_THAN_EQ = ">=";

	//Also best ratio stuff.//query two names?
	String BASE = "SELECT * FROM food WHERE "; 
	String ARG_1 = "?1";
	String priceLess = BASE + PRICE + LESS_THAN_EQ + ARG_1; 		String priceMore = BASE + PRICE + GREATHER_THAN_EQ + ARG_1;
	String proLess = BASE + PROTEIN + LESS_THAN_EQ + ARG_1;			String proMORE = BASE + PROTEIN + GREATHER_THAN_EQ + ARG_1;
	String carbLess = BASE + CARB + LESS_THAN_EQ + ARG_1;			String carbMORE = BASE + CARB + GREATHER_THAN_EQ + ARG_1;
	String fatLess = BASE + FAT + LESS_THAN_EQ + ARG_1;				String fatMore = BASE + FAT + GREATHER_THAN_EQ + ARG_1;
	String calLess = BASE + CALORIE + LESS_THAN_EQ + ARG_1;			String calMore = BASE + CALORIE + GREATHER_THAN_EQ + ARG_1;
	
	
	//lessthanorequalto
	//greaterthenequalto
	
	Page<Food> LessThan(int arg, Pageable pageable);


}
