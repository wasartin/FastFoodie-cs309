package edu.iastate.graysonc.fastfood.view_models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;
import java.util.function.Predicate;

import javax.inject.Inject;

import edu.iastate.graysonc.fastfood.database.entities.Favorite;
import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.database.entities.User;
import edu.iastate.graysonc.fastfood.repositories.Repository;

public class FavoritesViewModel extends ViewModel {
    private String TAG = "FavoritesViewModel";
    private Repository repo;
    private LiveData<List<Food>> favoriteFoods;

    @Inject
    public FavoritesViewModel(Repository repo) {
        this.repo = repo;
    }

    /**
     * Initializes the ViewModel with the specified User's favorite Foods
     * @param userEmail The User's email address
     */
    public void init(String userEmail) {
        if (this.favoriteFoods != null) {
            return;
        }
        favoriteFoods =  repo.getFavoriteFoodsForUser(userEmail);
    }

    /**
     *
     * @return The User's favorite Foods
     */
    public LiveData<List<Food>> getFavorites() {
        return favoriteFoods;
    }

    public void updateFavorites() {
        // TODO
    }

    /**
     * Removes the specified Food from the specified User's favorites
     * @param userEmail The User's email address
     * @param foodId The Food's id
     */
    public void removeFavorite(String userEmail, int foodId) {
        repo.deleteFavorite(userEmail, foodId);
    }
}
