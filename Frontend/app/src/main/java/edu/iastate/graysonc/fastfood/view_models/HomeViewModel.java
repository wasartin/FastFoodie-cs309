package edu.iastate.graysonc.fastfood.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.List;

import javax.inject.Inject;

import edu.iastate.graysonc.fastfood.App;
import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.repositories.Repository;

public class HomeViewModel extends ViewModel {
    private Repository repo;
    private LiveData<List<Food>> foods;

    @Inject
    public HomeViewModel(Repository repo) {
        this.repo = repo;
        foods = repo.getFavoriteFoodsForUser(GoogleSignIn.getLastSignedInAccount(App.context).getEmail());
    }

    public void doSearch(String query) {
        foods = repo.getFoodMatches(query);
    }

    public LiveData<List<Food>> getFoods() {
        return foods;
    }
}
