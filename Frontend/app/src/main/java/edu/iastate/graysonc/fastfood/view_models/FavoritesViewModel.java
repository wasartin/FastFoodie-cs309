package edu.iastate.graysonc.fastfood.view_models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;
import java.util.function.Predicate;

import javax.inject.Inject;

import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.database.entities.User;
import edu.iastate.graysonc.fastfood.repositories.Repository;

public class FavoritesViewModel extends ViewModel {
    private Repository repo;
    private LiveData<List<Food>> favorites;

    @Inject
    public FavoritesViewModel(Repository repo) {
        this.repo = repo;
    }

    public void init(String userEmail) {
        if (this.favorites != null) {
            return;
        }
        favorites = repo.getFavorites(userEmail);
    }

    public LiveData<List<Food>> getFavorites() {
        return favorites;
    }

    public void updateFavorites() {
        // TODO
    }

    public void removeFavorite(String userEmail, int foodId) {
        repo.deleteFavorite(userEmail, foodId);
    }
}
