package edu.iastate.graysonc.fastfood.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.Date;
import java.util.List;

import edu.iastate.graysonc.fastfood.database.entities.Favorite;
import edu.iastate.graysonc.fastfood.database.entities.Food;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface FavoriteDao {
    @Insert(onConflict = REPLACE)
    void insert(Favorite favorite);

    @Insert(onConflict = REPLACE)
    void insert(List<Favorite> favorites);

    @Query("SELECT food.id, name, proteinTotal, carbTotal, fatTotal, calorieTotal, location, food.lastRefresh FROM food INNER JOIN favorite ON food.id = favorite.foodId WHERE favorite.userEmail = :userEmail")
    LiveData<List<Food>> getFavoritesForUser(final String userEmail);

    @Query("SELECT * FROM favorite WHERE userEmail = :userEmail AND foodId = :foodId AND lastRefresh > :lastRefreshMax LIMIT 1")
    Favorite hasFavorite(String userEmail, int foodId, Date lastRefreshMax);
}
