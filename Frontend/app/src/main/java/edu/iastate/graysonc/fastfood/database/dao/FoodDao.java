package edu.iastate.graysonc.fastfood.database.dao;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import edu.iastate.graysonc.fastfood.database.entities.Food;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface FoodDao {
    @Insert(onConflict = REPLACE)
    void insert(Food food);

    @Insert(onConflict = REPLACE)
    void insert(List<Food> foods);

    @Delete
    void delete(Food food);

    @Update
    void update(Food food);

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
