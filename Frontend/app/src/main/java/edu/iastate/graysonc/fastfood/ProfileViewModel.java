package edu.iastate.graysonc.fastfood;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class ProfileViewModel extends AndroidViewModel {
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient googleSignInClient;
    private LiveData<GoogleSignInAccount> account;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(String userId) {
        // TODO
    }

    public LiveData<GoogleSignInAccount> getUser() {
        return account;
    }



}
