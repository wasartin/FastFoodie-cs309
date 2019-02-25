package edu.iastate.graysonc.fastfood;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mainNavigation;
    private FrameLayout mainFrame;

    private Repository repo;

    private Fragment homeFragment;
    private Fragment favoritesFragment;
    private Fragment profileFragment;
    private Fragment signInFragment;

    private ViewModel profileViewModel;


    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient googleSignInClient;
    private GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainNavigation = findViewById(R.id.main_navigation);
        mainFrame = findViewById(R.id.main_frame);

        // Initialize Repository
        repo = new Repository(getApplication());

        // Instantiate ViewModels
        profileViewModel = new ProfileViewModel(repo, getApplication());

        // Instantiate all fragments
        homeFragment = new HomeFragment();
        favoritesFragment = new FavoritesFragment();
        profileFragment = new ProfileFragment();

        // Start in home fragment
        setFragment(homeFragment);

        mainNavigation.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    setFragment(homeFragment);
                    break;
                case R.id.navigation_favorites:
                    setFragment(favoritesFragment);
                    break;
                case R.id.navigation_profile:
                    setFragment(profileFragment);
                    break;
            }
            return false;
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
}
