package edu.iastate.graysonc.fastfood.view_models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import edu.iastate.graysonc.fastfood.database.entities.User;
import edu.iastate.graysonc.fastfood.repositories.Repository;

public class HomeViewModel extends ViewModel {
    private Repository repo;
    //private LiveData<User> user;

    @Inject
    public HomeViewModel(Repository repo) {
        this.repo = repo;
    }

    public void init(String userId) {
        // TODO
    }

    public LiveData<User> getUser() {
        // TODO
        return null;
    }
}
