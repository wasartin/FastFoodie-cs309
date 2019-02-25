package edu.iastate.graysonc.fastfood;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import javax.inject.Inject;

public class ProfileViewModel extends AndroidViewModel {
    private Repository repo;
    private LiveData<GoogleSignInAccount> account;

    @Inject
    public ProfileViewModel(Repository repo, @NonNull Application application) {
        super(application);
        this.repo = repo;
    }

    public void init(GoogleSignInAccount account) {
        if (this.account != null) {
            // Do nothing
        } else {
            ((MutableLiveData<GoogleSignInAccount>) this.account).setValue(account);
        }
    }

    public LiveData<GoogleSignInAccount> getUser() {
        return account;
    }

    public void setUser(GoogleSignInAccount account) {
        ((MutableLiveData<GoogleSignInAccount>) this.account).setValue(account);
    }



}
