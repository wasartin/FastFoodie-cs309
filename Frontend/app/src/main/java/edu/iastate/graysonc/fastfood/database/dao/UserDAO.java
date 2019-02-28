package edu.iastate.graysonc.fastfood.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import edu.iastate.graysonc.fastfood.database.entities.User;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDAO {
    @Insert(onConflict = REPLACE)
    void save(User user);

    @Query("SELECT * FROM user WHERE email = :email")
    LiveData<User> load(String email);
}
