package edu.iastate.graysonc.fastfood.repositories;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.ItemKeyedDataSource;
import androidx.paging.PagedList;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.util.CrashUtils;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.iastate.graysonc.fastfood.App;
import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.api.Webservice;
import edu.iastate.graysonc.fastfood.database.dao.FoodDao;
import edu.iastate.graysonc.fastfood.database.dao.UserDao;
import edu.iastate.graysonc.fastfood.database.entities.Favorite;
import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.database.entities.ResultList;
import edu.iastate.graysonc.fastfood.database.entities.Ticket;
import edu.iastate.graysonc.fastfood.database.entities.User;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.motion.widget.MotionScene.TAG;

@Singleton
public class Repository {
    private String TAG = "Repository";

    private static int FRESH_TIMEOUT_IN_MINUTES = 1;

    private final Webservice webservice;
    private final UserDao userDao;
    private final FoodDao foodDao;
    private final Executor executor;

    @Inject
    public Repository(Webservice webservice, UserDao userDao, FoodDao foodDao, Executor executor) {
        this.webservice = webservice;
        this.userDao = userDao;
        this.foodDao = foodDao;
        this.executor = executor;
        executor.execute(() -> {
            foodDao.deleteAll();
        });// FOR DEBUGGING
    }

    public LiveData<List<Food>> getSearchResults(String query) {
        MutableLiveData<List<Food>> searchResults = new MutableLiveData<>();
        webservice.doSearch(query).enqueue(new Callback<ResultList>() {
            @Override
            public void onResponse(Call<ResultList> call, Response<ResultList> response) {
                ResultList resultList = response.body();
                List<Food> foods = response.body().getContent();
                for (int i = 0; i < foods.size(); i++) {
                    int finalI = i;
                    webservice.getAverageRating(foods.get(i).getId()).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.body() != null) {
                                double r = 0.0;
                                if (!response.body().equals("NaN")) {
                                    r = Double.parseDouble(response.body());
                                }
                                Log.d(TAG, "onResponse: Rating = " + r);
                                foods.get(finalI).setRating(r);
                            }
                            searchResults.postValue(foods);
                        }
                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ResultList> call, Throwable t) {

            }
        });
        return searchResults;
    }

    public Call<ResultList> getFoodMatches(String query, int page) {
        return webservice.doSearch(query);
    }

    public Call<String> getAverageRating(int foodId) {
        return webservice.getAverageRating(foodId);
    }

    public LiveData<User> getUser(String userEmail) {
        refreshUser(userEmail); // Refresh if possible
        return userDao.load(userEmail); // Returns a LiveData object directly from the database.
    }

    private void refreshUser(final String userEmail) {
        executor.execute(() -> {
            // Check if user was fetched recently
            boolean userExists = (userDao.hasUser(userEmail, getMaxRefreshTime(new Date())) != null);
            // If user have to be updated
            if (!userExists) {
                webservice.getUser(userEmail).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.d(TAG, "DATA REFRESHED FROM NETWORK");
                        executor.execute(() -> {
                            User user = response.body();
                            if (user == null) {
                                user = new User(userEmail, "General", new Date());
                                webservice.createUser(user).enqueue(new Callback<User>() {
                                    @Override
                                    public void onResponse(Call<User> call, Response<User> response) {
                                        Log.d(TAG, "onResponse: User created successfully");
                                    }

                                    @Override
                                    public void onFailure(Call<User> call, Throwable t) {
                                        Log.e(TAG, "onFailure: Error creating user");
                                    }
                                });
                            } else {
                                user.setLastRefresh(new Date());
                            }
                            userDao.insert(user);
                        });
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }

    public LiveData<Food> getFood(int foodId) {
        refreshFood(foodId); // Refresh if possible
        return foodDao.load(foodId); // Returns a LiveData object directly from the database.
    }

    private void refreshFood(final int foodId) {
        executor.execute(() -> {
            // Check if food was fetched recently
            boolean foodExists = (foodDao.hasFood(foodId, getMaxRefreshTime(new Date())) != null);
            // If food have to be updated
            if (!foodExists) {
                webservice.getFood(foodId).enqueue(new Callback<Food>() {
                    @Override
                    public void onResponse(Call<Food> call, Response<Food> response) {
                        Log.d(TAG, "DATA REFRESHED FROM NETWORK");
                        executor.execute(() -> {
                            Food food = response.body();
                            if (food == null) {
                                Log.e(TAG,"Grayson your code doesn't work <3 - refreshFood");
                            } else {
                                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(App.context);
                                if (account != null) {
                                    webservice.getFavoriteFoodsForUser(account.getEmail()).enqueue(new Callback<List<Food>>() {
                                        @Override
                                        public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                                            List<Food> favorites = response.body();
                                            for (Food fav : favorites) {
                                                if (fav.getId() == food.getId()) {
                                                    food.setIsFavorite(1);
                                                    break;
                                                }
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call<List<Food>> call, Throwable t) {

                                        }
                                    });
                                }
                                food.setLastRefresh(new Date());
                                foodDao.update(food);
                            }
                        });
                    }
                    @Override
                    public void onFailure(Call<Food> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }

    public LiveData<List<Food>> getFavoriteFoodsForUser(String userEmail) {
        refreshFavoritesForUser(userEmail);
        return foodDao.loadFavorites();
    }

    private void refreshFavoritesForUser(String userEmail) {
        executor.execute(() -> {
            webservice.getFavoriteFoodsForUser(userEmail).enqueue(new Callback<List<Food>>() {
                @Override
                public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                    Log.d(TAG, "FAVORITES REFRESHED FROM NETWORK");
                    executor.execute(() -> {
                        List<Food> favorites = response.body();
                        if (favorites == null) {
                            Log.e(TAG,"Grayson your code doesn't work <3 - refreshFood");
                        } else {
                            for (Food f : favorites) {
                                foodDao.delete(f.getId());
                                webservice.getAverageRating(f.getId()).enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        f.setIsFavorite(1);
                                        if (response.body() != null) {
                                            double r = 0.0;
                                            if (!response.body().equals("NaN")) {
                                                r = Double.parseDouble(response.body());
                                            }
                                            Log.d(TAG, "onResponse: Rating = " + r);
                                            f.setRating(r);
                                        }
                                        executor.execute(() -> {
                                            foodDao.insert(f);
                                        });
                                    }
                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        t.printStackTrace();
                                    }
                                });
                            }
                        }
                    });
                }
                @Override
                public void onFailure(Call<List<Food>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        });
    }

    public void createFavorite(String userEmail, int foodId) {
        executor.execute(() -> {
            webservice.createFavorite(userEmail, foodId).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.d(TAG, "FAVORITE ADDED");
                    Toast.makeText(App.context, R.string.favorite_created_toast, Toast.LENGTH_SHORT).show();
                    executor.execute(() -> {
                        foodDao.delete(foodId);
                    });
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(App.context, R.string.favorite_created_toast, Toast.LENGTH_SHORT).show();
                    executor.execute(() -> {
                        foodDao.delete(foodId);
                    });
                    Log.e(TAG, "onFailure: ", t);
                }
            });
        });
    }

    public void deleteFavorite(String userEmail, int foodId) {
        executor.execute(() -> {
            //foodDao.delete(foodId);
            webservice.deleteFavorite(userEmail, foodId).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.d(TAG, "FAVORITE REMOVED - " + foodId);
                    Toast.makeText(App.context, R.string.favorite_deleted_toast, Toast.LENGTH_SHORT).show();
                    executor.execute(() -> {
                        foodDao.delete(foodId);
                    });
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(App.context, R.string.favorite_deleted_toast, Toast.LENGTH_SHORT).show();
                    executor.execute(() -> {
                        foodDao.delete(foodId);
                    });
                    t.printStackTrace();
                }
            });
        });
    }

    public void submitRating(String userEmail, int foodId, int rating) {
        executor.execute(() -> {
            webservice.submitRating(userEmail, foodId, rating).enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    Log.d(TAG, "onResponse: Rating submitted successfully");
                    Toast.makeText(App.context, R.string.rating_submitted_toast, Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    Log.d(TAG, "onResponse: Rating submitted successfully");
                    Toast.makeText(App.context, R.string.rating_submitted_toast, Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        });
    }

    public void deleteRating(String userEmail, int foodId) {
        executor.execute(() -> {
            webservice.deleteRating(userEmail, foodId).enqueue(new Callback<Double>() {
                @Override
                public void onResponse(Call<Double> call, Response<Double> response) {
                    Log.d(TAG, "onResponse: Rating submitted successfully");
                }
                @Override
                public void onFailure(Call<Double> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        });
    }

    public void submitTicket(Ticket ticket) {
        executor.execute(() -> {
            webservice.submitTicket(ticket).enqueue(new Callback<Ticket>() {
                @Override
                public void onResponse(Call<Ticket> call, Response<Ticket> response) {
                    Log.d(TAG, "onResponse: Ticket submitted successfully");
                    Toast.makeText(App.context, "Ticket submitted successfully!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Ticket> call, Throwable t) {
                    Log.d(TAG, "onFailure: Error submitting ticket");
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
