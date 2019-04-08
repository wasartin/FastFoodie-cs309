package edu.iastate.graysonc.fastfood.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
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

    @Delete
    void delete(Food food);

    @Query("DELETE FROM food WHERE id = :id")
    void delete(int id);

    @Query("SELECT * FROM food WHERE id = :id")
    LiveData<Food> load(int id);

    @Query("SELECT * FROM food WHERE isFavorite = 1")
    LiveData<List<Food>> loadFavorites();

    @Query("SELECT * FROM food")
    LiveData<List<Food>> loadAll();

    @Query("SELECT * FROM food WHERE id = :foodID AND lastRefresh > :lastRefreshMax LIMIT 1")
    Food hasFood(int foodID, Date lastRefreshMax);
}
