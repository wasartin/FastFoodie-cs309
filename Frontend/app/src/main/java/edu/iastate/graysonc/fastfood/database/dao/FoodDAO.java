package edu.iastate.graysonc.fastfood.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.Date;

import edu.iastate.graysonc.fastfood.database.entities.Food;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface FoodDAO {
    @Insert(onConflict = REPLACE)
    void save(Food food);

    @Query("SELECT * FROM food WHERE food_id = :id")
    LiveData<Food> load(int id);

    @Query("SELECT * FROM food WHERE food_id = :foodID AND lastRefresh > :lastRefreshMax LIMIT 1")
    Food hasFood(int foodID, Date lastRefreshMax);
}
