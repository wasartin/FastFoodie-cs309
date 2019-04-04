package edu.iastate.graysonc.fastfood.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.Date;
import java.util.List;

import edu.iastate.graysonc.fastfood.database.entities.Food;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface FoodDao {
    @Insert(onConflict = REPLACE)
    void insert(Food food);

    @Insert(onConflict = REPLACE)
    void insert(List<Food> foods);

    @Query("SELECT * FROM food WHERE id = :id")
    LiveData<Food> load(int id);

    @Query("SELECT * FROM food")
    LiveData<List<Food>> loadAll();

    @Query("SELECT * FROM food WHERE id IN (SELECT foodId FROM favorite WHERE favorite.userEmail = :userEmail)")
    LiveData<List<Food>> getFavoriteFoodsForUser(String userEmail);

    @Query("SELECT * FROM food WHERE id = :foodID AND lastRefresh > :lastRefreshMax LIMIT 1")
    Food hasFood(int foodID, Date lastRefreshMax);
}
