package edu.iastate.graysonc.fastfood.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.repositories.Repository;

public class FoodProfileViewModel extends ViewModel {
    private Repository repo;
    private LiveData<Food> food;

    @Inject
    public FoodProfileViewModel(Repository repo) {
        this.repo = repo;
    }

    public void init(int foodId) {
        food = repo.getFood(foodId);
    }

    public LiveData<Food> getFood() {
        return this.food;
    }
}
