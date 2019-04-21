package edu.iastate.graysonc.fastfood.database.dao;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import edu.iastate.graysonc.fastfood.database.entities.Favorite;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface FavoriteDao {
    @Insert(onConflict = REPLACE)
    void insert(Favorite favorite);

    @Insert(onConflict = REPLACE)
    void insert(List<Favorite> favorites);

    @Query("DELETE FROM favorite WHERE userEmail = :userEmail AND foodId = :foodId")
    void delete(String userEmail, int foodId);

    @Query("DELETE FROM favorite")
    void deleteAll();

    //@Query("SELECT food.id, name, proteinTotal, carbTotal, fatTotal, calorieTotal, location, isFavorite, food.lastRefresh FROM food INNER JOIN favorite ON food.id = favorite.foodId WHERE favorite.userEmail = :userEmail")
    @Query("SELECT * FROM favorite WHERE userEmail = :userEmail")
    LiveData<List<Favorite>> getFavoritesForUser(final String userEmail);

    @Query("SELECT * FROM favorite WHERE userEmail = :userEmail AND foodId = :foodId AND lastRefresh > :lastRefreshMax LIMIT 1")
    Favorite hasFavorite(String userEmail, int foodId, Date lastRefreshMax);
}
