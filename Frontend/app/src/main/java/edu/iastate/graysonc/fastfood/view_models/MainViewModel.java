package edu.iastate.graysonc.fastfood.view_models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class MainViewModel extends AndroidViewModel {
    private LiveData<GoogleSignInAccount> account;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<GoogleSignInAccount> getUser() {
        return account;
    }
}
