package edu.iastate.graysonc.fastfood.view_models;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import edu.iastate.graysonc.fastfood.App;
import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.repositories.Repository;

public class SearchResultsViewModel extends ViewModel {
    private Repository repo;
    private LiveData<List<Food>> foods;

    @Inject
    public SearchResultsViewModel(Repository repo) {
        this.repo = repo;
    }

    /**
     * Performs a query and initializes this ViewModel with the results
     * @param query - The query to be executed
     */
    public void init(String query) {
    }

    public LiveData<List<Food>> getFoods() {
        return foods;
    }

    public void addFavorite(String userEmail, int foodId) {
        repo.createFavorite(userEmail, foodId);
    }

    public void removeFavorite(String userEmail, int foodId) {
        repo.deleteFavorite(userEmail, foodId);
    }
}
