package edu.iastate.graysonc.fastfood.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.RecentSearchProvider;
import edu.iastate.graysonc.fastfood.fragments.FavoritesFragment;
import edu.iastate.graysonc.fastfood.fragments.HomeFragment;
import edu.iastate.graysonc.fastfood.fragments.ProfileFragment;
import edu.iastate.graysonc.fastfood.fragments.SearchResultsFragment;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector, BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    private static final String BACK_STACK_ROOT_TAG = "root_fragment";
    private FragmentManager fragmentManager;
    private Fragment homeFragment;
    private Fragment favoritesFragment;
    private Fragment profileFragment;
    private Fragment searchResultsFragment;
    private Fragment currentFragment;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);



        // Instantiate all fragments
        homeFragment = new HomeFragment();
        favoritesFragment = new FavoritesFragment();
        profileFragment = new ProfileFragment();
        searchResultsFragment = new SearchResultsFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.main_frame, profileFragment, "profile").hide(profileFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_frame, favoritesFragment, "favorites").hide(favoritesFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_frame, homeFragment, "home").commit();
        currentFragment = homeFragment;

        // Start in home fragment
        if (savedInstanceState == null) {
            setFragment(homeFragment);
        }

        ((BottomNavigationView)findViewById(R.id.main_navigation)).setOnNavigationItemSelectedListener(this);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // Save the query for future suggestions
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    RecentSearchProvider.AUTHORITY, RecentSearchProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            Log.d(TAG, "onCreate: Recieved query: " + query);

            // Create and show SearchResultsFragment
            Bundle bundle = new Bundle();
            bundle.putString("QUERY", query);
            searchResultsFragment.setArguments(bundle);
            setFragment(searchResultsFragment);
        }

    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    private void setFragment(Fragment fragment) {
        fragmentManager.beginTransaction().hide(currentFragment).show(fragment).commit();
        currentFragment = fragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
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
        menuItem.setChecked(true);
        return false;
    }
}
