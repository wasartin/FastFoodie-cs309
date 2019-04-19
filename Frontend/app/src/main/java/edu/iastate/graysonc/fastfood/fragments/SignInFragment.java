package edu.iastate.graysonc.fastfood.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import edu.iastate.graysonc.fastfood.App;
import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.activities.MainActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient googleSignInClient;
    private GoogleSignInAccount account;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    /**
     * Creates a view model for this method
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_in, container, false);

    }

    @Override


    /**
     * Creates an activity with all the needed handlers and variables
     */
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        googleSignInClient.signOut();


        getView().findViewById(R.id.sign_in_button_frag).setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.sign_in_button_frag:
                signIn();
                break;
            case R.id.continue_without_sign_in_button:
                startMainActivity(null);
                break;
        }
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

    /**
     * Handles a user signing in, assigns appropriate values to the repository
     * @param completedTask Package with all needed information
     */
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

    /**
     * Creates a new intent with google sign on
     */
    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /**
     * Creates a new main activity with a set account, essentially restarting the app
     * @param account a newly signed in user
     */
    private void startMainActivity(GoogleSignInAccount account) {
        Intent startIntent = new Intent(getContext(), MainActivity.class);
        if (account != null) {
            App.account = account;
        }
        startActivity(startIntent);
    }
}
