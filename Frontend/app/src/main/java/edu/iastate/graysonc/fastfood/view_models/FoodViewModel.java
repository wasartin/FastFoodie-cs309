package edu.iastate.graysonc.fastfood.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.List;

import javax.inject.Inject;

import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.repositories.Repository;

public class FoodViewModel extends ViewModel {
    private String TAG = "FoodViewModel";
    private Repository repo;
    private LiveData<List<Food>> favoriteFoods;
    private Food selectedFood;

    private String mUserEmail;

    // For paging
    private FoodDataSourceFactory foodDataSourceFactory;
    private LiveData<PagedList<Food>> searchResults;
    private LiveData<String> progressLoadStatus = new MutableLiveData<>();

    @Inject
    public FoodViewModel(Repository repo) {
        this.repo = repo;
        foodDataSourceFactory = new FoodDataSourceFactory(repo);
        initializePaging();
    }

    private void initializePaging() {
        PagedList.Config pagedListConfig =
                new PagedList.Config.Builder()
                        .setEnablePlaceholders(true)
                        .setInitialLoadSizeHint(20)
                        .setPageSize(20).build();
        searchResults = new LivePagedListBuilder<>(foodDataSourceFactory, pagedListConfig)
                .build();
        progressLoadStatus = Transformations.switchMap(foodDataSourceFactory.getMutableLiveData(), FoodDataSource::getProgressLiveStatus);
    }

    public LiveData<String> getProgressLoadStatus() {
        return progressLoadStatus;
    }

    public LiveData<PagedList<Food>> getSearchResults() {
        return searchResults;
    }

    public void initFavoriteFoods(String userEmail) {
        mUserEmail = userEmail;
        loadFavoriteFoods();
    }

    public LiveData<List<Food>> getFavoriteFoods() {
        if (favoriteFoods == null) {
            favoriteFoods = new MutableLiveData<List<Food>>();
            //loadFavoriteFoods();
        }
        return favoriteFoods;
    }

    public void loadFavoriteFoods() {
        favoriteFoods = repo.getFavoriteFoodsForUser(mUserEmail);
    }

    public Food getSelectedFood() {
        return selectedFood;
    }

    public void setSelectedFood(Food food) {
        selectedFood = food;
    }

    public void addToFavorites(String userEmail, int foodId) {
        repo.createFavorite(userEmail, foodId);
    }

    public void removeFromFavorites(String userEmail, int foodId) {
        repo.deleteFavorite(userEmail, foodId);
    }

    public void submitRating(int foodId, int rating) {
        repo.submitRating(mUserEmail, foodId, rating);
    }
}
