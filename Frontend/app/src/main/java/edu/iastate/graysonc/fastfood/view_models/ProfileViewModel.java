package edu.iastate.graysonc.fastfood.view_models;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import edu.iastate.graysonc.fastfood.repositories.Repository;
import edu.iastate.graysonc.fastfood.database.entities.User;

public class ProfileViewModel extends ViewModel {
    private Repository repo;
    private LiveData<User> user;
    private GoogleSignInAccount account;

    @Inject
    public ProfileViewModel(Repository repo) {
        this.repo = repo;
    }

    public void init(GoogleSignInAccount account) {
        if (this.user != null) {
            return;
        }
        this.account = account;
        user = repo.getUser(account.getEmail());
    }

    public LiveData<User> getUser() {
        return this.user;
    }

    public GoogleSignInAccount getGoogleSignInAccount() {
        return account;
    }
}
