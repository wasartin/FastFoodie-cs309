package edu.iastate.graysonc.fastfood.view_models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import edu.iastate.graysonc.fastfood.database.entities.User;
import edu.iastate.graysonc.fastfood.repositories.Repository;

public class HomeViewModel extends ViewModel {
    private Repository repo;
    private LiveData<User> user;

    @Inject
    public HomeViewModel(Repository repo) {
        this.repo = repo;
    }

    /**
     * Initializes the ViewModel
     * @param userId The User's email address
     */
    public void init(String userId) {
        // TODO
    }

    /**
     * Gets the object corresponding to the current User
     * @return The User object
     */
    public LiveData<User> getUser() {
        // TODO
        return null;
    }

    /**
     * Adds the specified Food to the specified User's favorites
     * @param userEmail The User's email address
     * @param foodId The Food's id
     */
    public void addFavorite(String userEmail, int foodId) {
        repo.createFavorite(userEmail, foodId);
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
