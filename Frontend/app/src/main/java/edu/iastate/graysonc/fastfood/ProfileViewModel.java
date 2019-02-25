package edu.iastate.graysonc.fastfood;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

public class ProfileViewModel extends ViewModel {
    private Repository repo;
    private LiveData<User> user;

    @Inject
    public ProfileViewModel(Repository repo) {
        this.repo = repo;
    }

    public void init(String userId) {
        if (this.user != null) {
            // ViewModel is created on a per-Fragment basis, so the userId
            // doesn't change.
            return;
        }
        user = repo.getUser(userId);
    }

    public LiveData<User> getUser() {
        return this.user;
    }
}
