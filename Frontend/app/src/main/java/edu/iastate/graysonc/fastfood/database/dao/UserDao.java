package edu.iastate.graysonc.fastfood.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.Date;
import java.util.List;

import edu.iastate.graysonc.fastfood.database.entities.User;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

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
