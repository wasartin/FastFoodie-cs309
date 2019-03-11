package edu.iastate.graysonc.fastfood.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import edu.iastate.graysonc.fastfood.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient googleSignInClient;
    private GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignInClient.signOut();


        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.continue_without_signin_button).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Un-comment this block if we want the app to sign in with the previously signed in account
        /*account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            Log.e(TAG, "onStart: Got account from " +  account.getDisplayName());
            startMainActivity(account);
        }*/
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /**
     * There's no "sign out" button on this screen, so this does nothing.
     * I'll probably delete this later.
     */
    private void signOut() {
        googleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);
            if (account != null) {
                // Signed in successfully, start MainActivity.
                startMainActivity(account);
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            // TODO: Display error message or ask user to re-attempt a login, maybe
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.continue_without_signin_button:
                startMainActivity(null);
                break;
        }
    }

    /**
     * Starts MainActivity
     * @param account If null, MainActivity will be in "Unregistered User" mode.
     */
    private void startMainActivity(GoogleSignInAccount account) {
        Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
        if (account != null) {
            // Give MainActivity access to the Google account
            startIntent.putExtra("edu.iastate.graysonc.fastfood.ACCOUNT", account);
        }
        startActivity(startIntent);
    }
}
