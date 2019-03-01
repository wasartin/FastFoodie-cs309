package edu.iastate.graysonc.fastfood.view_models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import edu.iastate.graysonc.fastfood.repositories.Repository;
import edu.iastate.graysonc.fastfood.database.entities.User;

public class ProfileViewModel extends ViewModel {
    private Repository repo;
    private LiveData<User> user;

    @Inject
    public ProfileViewModel(Repository repo) {
        this.repo = repo;
    }

    public void init(String email) {
        if (this.user != null) {
            return;
        }
        user = repo.getUser(email);
    }

    public LiveData<User> getUser() {
        return this.user;
    }
}
