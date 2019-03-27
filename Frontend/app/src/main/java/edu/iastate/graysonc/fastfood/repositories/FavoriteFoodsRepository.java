package edu.iastate.graysonc.fastfood.repositories;

import android.arch.lifecycle.LiveData;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.iastate.graysonc.fastfood.App;
import edu.iastate.graysonc.fastfood.api.Webservice;
import edu.iastate.graysonc.fastfood.database.dao.FavoriteDao;
import edu.iastate.graysonc.fastfood.database.dao.FoodDao;
import edu.iastate.graysonc.fastfood.database.dao.UserDao;
import edu.iastate.graysonc.fastfood.database.entities.Favorite;
import edu.iastate.graysonc.fastfood.database.entities.Food;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class FavoriteFoodsRepository {
    private String TAG = "FavoriteFoodsRepository";

    private static int FRESH_TIMEOUT_IN_MINUTES = 1;

    private final Webservice webservice;
    private final FoodDao foodDao;
    private final FavoriteDao favoriteDao;
    private final Executor executor;

    @Inject
    public FavoriteFoodsRepository(Webservice webservice, FoodDao foodDao, FavoriteDao favoriteDao, Executor executor) {
        this.webservice = webservice;
        this.foodDao = foodDao;
        this.favoriteDao = favoriteDao;
        this.executor = executor;
    }

    public LiveData<List<Food>> getFavorites(String userEmail) {
        refreshFavorites(userEmail); // Refresh if possible
        LiveData<List<Food>> favorites = favoriteDao.getFavoritesForUser(userEmail); // Returns a LiveData object directly from the database.
        if (favorites.getValue() != null) {
            for (Food f: favorites.getValue()) {
                f.setFavorite(true);
                foodDao.insert(f);
            }
        }
        return favoriteDao.getFavoritesForUser(userEmail); // Returns a LiveData object directly from the database.
    }

    public void createFavorite(String userEmail, int foodId) {
        executor.execute(() -> {
            webservice.createFavorite(userEmail, foodId).enqueue(new Callback<Favorite>() {
                @Override
                public void onResponse(Call<Favorite> call, Response<Favorite> response) {
                    Log.d(TAG, "FAVORITE ADDED");
                    //Toast.makeText(App.context, "Added to favorites", Toast.LENGTH_LONG).show();
                    refreshFavorites(userEmail);
                }
                @Override
                public void onFailure(Call<Favorite> call, Throwable t) { t.printStackTrace(); }
            });
        });
    }

    public void deleteFavorite(String userEmail, int foodId) {
        executor.execute(() -> {
            favoriteDao.delete(userEmail, foodId);
            webservice.deleteFavorite(userEmail, foodId).enqueue(new Callback<Food>() {
                @Override
                public void onResponse(Call<Food> call, Response<Food> response) {
                    Log.d(TAG, "FAVORITE REMOVED");
                    //Toast.makeText(App.context, "Removed from favorites", Toast.LENGTH_LONG).show();
                    refreshFavorites(userEmail);
                }
                @Override
                public void onFailure(Call<Food> call, Throwable t) { t.printStackTrace(); }
            });
        });
    }

    private void refreshFavorites(final String userEmail) {
        //fetchAllFoods(); // TODO: Not this ever again. This is bad.
        executor.execute(() -> {
            webservice.getFavoritesForUser(userEmail).enqueue(new Callback<List<Favorite>>() {
                @Override
                public void onResponse(Call<List<Favorite>> call, Response<List<Favorite>> response) {
                    Log.d(TAG, "FAVORITES REFRESHED FROM NETWORK");
                    Toast.makeText(App.context, "Data refreshed from network", Toast.LENGTH_LONG).show();
                    executor.execute(() -> {
                        List<Favorite> favorites = response.body();
                        if (favorites == null) {
                            Log.e(TAG,"Grayson your code doesn't work <3 - refreshFavorites");
                        } else {
                            favoriteDao.insert(favorites);
                            for (Favorite f : favorites) {
                                executor.execute(() -> { // TODO: This is awful. I'm sorry to whomever's reading this code.
                                    webservice.getFood(f.getFoodId()).enqueue(new Callback<Food>() {
                                        @Override
                                        public void onResponse(Call<Food> call, Response<Food> response) {
                                            executor.execute(() -> {
                                                Food food = response.body();
                                                food.setFavorite(true);
                                                foodDao.insert(food);
                                            });
                                        }

                                        @Override
                                        public void onFailure(Call<Food> call, Throwable t) {
                                            t.printStackTrace();
                                        }
                                    });
                                });
                            }
                        }

                    });
                }
                @Override
                public void onFailure(Call<List<Favorite>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        });
    }

    private Date getMaxRefreshTime(Date currentDate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.MINUTE, -FRESH_TIMEOUT_IN_MINUTES);
        return cal.getTime();
    }
}
