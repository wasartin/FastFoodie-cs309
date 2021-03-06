package edu.iastate.graysonc.fastfood.di.module;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import edu.iastate.graysonc.fastfood.api.Webservice;
import edu.iastate.graysonc.fastfood.database.MyDatabase;
import edu.iastate.graysonc.fastfood.database.dao.FoodDao;
import edu.iastate.graysonc.fastfood.database.dao.UserDao;
import edu.iastate.graysonc.fastfood.repositories.Repository;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule {

    // --- DATABASE INJECTION ---

    @Provides
    @Singleton
    MyDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application,
                MyDatabase.class, "MyDatabase.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    UserDao provideUserDao(MyDatabase database) { return database.userDao(); }

    @Provides
    @Singleton
    FoodDao provideFoodDao(MyDatabase database) { return database.foodDao(); }

    // --- REPOSITORY INJECTION ---

    @Provides
    Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    @Singleton
    Repository provideRepository(Webservice webservice, UserDao userDAO, FoodDao foodDAO, Executor executor) {
        return new Repository(webservice, userDAO, foodDAO, executor);
    }

    // --- NETWORK INJECTION ---

    private static String BASE_URL = "http://cs309-bs-1.misc.iastate.edu:8080/";

    @Provides
    Gson provideGson() { return new GsonBuilder().create(); }

    @Provides
    Retrofit provideRetrofit(Gson gson) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    Webservice provideApiWebservice(Retrofit restAdapter) {
        return restAdapter.create(Webservice.class);
    }
}