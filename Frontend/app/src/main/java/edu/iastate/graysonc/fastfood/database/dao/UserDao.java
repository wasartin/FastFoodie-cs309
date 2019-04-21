package edu.iastate.graysonc.fastfood.database.dao;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import edu.iastate.graysonc.fastfood.database.entities.User;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {
    @Insert(onConflict = REPLACE)
    void insert(User user);

    @Insert(onConflict = REPLACE)
    void insert(List<User> users);

    @Query("SELECT * FROM user WHERE email = :email")
    LiveData<User> load(String email);

    @Query("SELECT * FROM user WHERE email = :email AND lastRefresh > :lastRefreshMax LIMIT 1")
    User hasUser(String email, Date lastRefreshMax);
}
