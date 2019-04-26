package edu.iastate.graysonc.fastfood.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import edu.iastate.graysonc.fastfood.database.converter.DateConverter;
import edu.iastate.graysonc.fastfood.database.dao.FoodDao;
import edu.iastate.graysonc.fastfood.database.dao.UserDao;
import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.database.entities.User;

@Database(entities = {User.class, Food.class}, version = 12, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class MyDatabase extends RoomDatabase {
    // --- SINGLETON ---
    private static volatile MyDatabase INSTANCE;

    // --- DAO ---
    public abstract UserDao userDao();
    public abstract FoodDao foodDao();
}