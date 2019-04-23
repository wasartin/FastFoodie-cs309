package edu.iastate.graysonc.fastfood.view_models;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import edu.iastate.graysonc.fastfood.App;
import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.repositories.Repository;

public class HomeViewModel extends ViewModel {
    private Repository repo;
    private LiveData<List<Food>> foods;

    @Inject
    public HomeViewModel(Repository repo) {
        this.repo = repo;
    }

    public void init() { // This just displays my favorites right now. I need to make this fetch foods based on other criteria.
        foods = repo.getFavoriteFoodsForUser(App.account.getEmail());
    }

    public LiveData<List<Food>> getFoods() {
        return foods;
    }
}
