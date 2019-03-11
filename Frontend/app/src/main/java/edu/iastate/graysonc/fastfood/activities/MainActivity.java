package edu.iastate.graysonc.fastfood.activities;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.fragments.FavoritesFragment;
import edu.iastate.graysonc.fastfood.fragments.HomeFragment;
import edu.iastate.graysonc.fastfood.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    private static final String TAG = "MainActivity";
    private Fragment homeFragment;
    private Fragment favoritesFragment;
    private Fragment profileFragment;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidInjection.inject(this);

        // Instantiate all fragments
        homeFragment = new HomeFragment();
        favoritesFragment = new FavoritesFragment();
        profileFragment = new ProfileFragment();

        // Pass the current user's email address and GoogleSignInAccount object to each fragment
        GoogleSignInAccount account = getIntent().getExtras().getParcelable("edu.iastate.graysonc.fastfood.ACCOUNT");
        Bundle bundle = new Bundle();
        bundle.putString("USER_EMAIL", account.getEmail());
        bundle.putParcelable("ACCOUNT", account);
        homeFragment.setArguments(bundle);
        favoritesFragment.setArguments(bundle);
        profileFragment.setArguments(bundle);

        // Start in home fragment
        if (savedInstanceState == null) {
            setFragment(homeFragment);
        }

        ((BottomNavigationView)findViewById(R.id.main_navigation)).setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    menuItem.setChecked(true);
                    setFragment(homeFragment);
                    break;
                case R.id.navigation_favorites:
                    menuItem.setChecked(true);
                    setFragment(favoritesFragment);
                    break;
                case R.id.navigation_profile:
                    menuItem.setChecked(true);
                    setFragment(profileFragment);
                    break;
            }
            return false;
        });
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
}
