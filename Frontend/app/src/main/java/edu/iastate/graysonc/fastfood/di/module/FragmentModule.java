package edu.iastate.graysonc.fastfood.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import edu.iastate.graysonc.fastfood.fragments.FavoritesFragment;
import edu.iastate.graysonc.fastfood.fragments.FoodProfileFragment;
import edu.iastate.graysonc.fastfood.fragments.HomeFragment;
import edu.iastate.graysonc.fastfood.fragments.ProfileFragment;
import edu.iastate.graysonc.fastfood.fragments.SearchResultsFragment;
import edu.iastate.graysonc.fastfood.fragments.SignInFragment;

@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract HomeFragment contributeHomeFragment();

    @ContributesAndroidInjector
    abstract FavoritesFragment contributeFavoritesFragment();

    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();

    @ContributesAndroidInjector
    abstract SearchResultsFragment contributeSearchResultsFragment();

    @ContributesAndroidInjector
    abstract FoodProfileFragment contributeFoodProfileFragment();

    @ContributesAndroidInjector
    abstract SignInFragment contributeSignInFragment();

}
