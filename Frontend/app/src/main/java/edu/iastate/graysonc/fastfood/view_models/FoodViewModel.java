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
    private LiveData<List<Food>> searchResults;

    // Sorting/filtering
    private boolean isFilterByPrice, isFilterByCalorie, isFilterByProtein, isFilterByFat, isFilterByCarb;
    private double priceMin, priceMax;
    private int calorieMin, calorieMax, proteinMin, proteinMax, fatMin, fatMax, carbMin, carbMax;
    private int sortOption;


    @Inject
    public FoodViewModel(Repository repo) {
        this.repo = repo;

    }
    public void doSearch(String query) {
        searchResults = repo.getSearchResults(query);
    }

    public LiveData<List<Food>> getSearchResults() {
        if (searchResults == null) {
            searchResults = new MutableLiveData<>();
        }
        return searchResults;
    }

    public void clearSearchResults() {
        searchResults = new MutableLiveData<>();
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
        if (favoriteFoods != null) {
            if (favoriteFoods.getValue() != null) {
                for (Food f : favoriteFoods.getValue()) {
                    if (f.getId() == food.getId()) {
                        selectedFood.setIsFavorite(1);
                    }
                }
            }
        }
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

    public boolean isFilterByPrice() {
        return isFilterByPrice;
    }

    public void setFilterByPrice(boolean filterByPrice) {
        isFilterByPrice = filterByPrice;
    }

    public boolean isFilterByCalorie() {
        return isFilterByCalorie;
    }

    public void setFilterByCalorie(boolean filterByCalorie) {
        isFilterByCalorie = filterByCalorie;
    }

    public boolean isFilterByProtein() {
        return isFilterByProtein;
    }

    public void setFilterByProtein(boolean filterByProtein) {
        isFilterByProtein = filterByProtein;
    }

    public boolean isFilterByFat() {
        return isFilterByFat;
    }

    public void setFilterByFat(boolean filterByFat) {
        isFilterByFat = filterByFat;
    }

    public boolean isFilterByCarb() {
        return isFilterByCarb;
    }

    public void setFilterByCarb(boolean filterByCarb) {
        isFilterByCarb = filterByCarb;
    }

    public double getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(double priceMin) {
        this.priceMin = priceMin;
    }

    public double getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(double priceMax) {
        this.priceMax = priceMax;
    }

    public int getCalorieMin() {
        return calorieMin;
    }

    public void setCalorieMin(int calorieMin) {
        this.calorieMin = calorieMin;
    }

    public int getCalorieMax() {
        return calorieMax;
    }

    public void setCalorieMax(int calorieMax) {
        this.calorieMax = calorieMax;
    }

    public int getProteinMin() {
        return proteinMin;
    }

    public void setProteinMin(int proteinMin) {
        this.proteinMin = proteinMin;
    }

    public int getProteinMax() {
        return proteinMax;
    }

    public void setProteinMax(int proteinMax) {
        this.proteinMax = proteinMax;
    }

    public int getFatMin() {
        return fatMin;
    }

    public void setFatMin(int fatMin) {
        this.fatMin = fatMin;
    }

    public int getFatMax() {
        return fatMax;
    }

    public void setFatMax(int fatMax) {
        this.fatMax = fatMax;
    }

    public int getCarbMin() {
        return carbMin;
    }

    public void setCarbMin(int carbMin) {
        this.carbMin = carbMin;
    }

    public int getCarbMax() {
        return carbMax;
    }

    public void setCarbMax(int carbMax) {
        this.carbMax = carbMax;
    }

    public int getSortOption() {
        return sortOption;
    }

    public void setSortOption(int sortOption) {
        this.sortOption = sortOption;
    }
}
