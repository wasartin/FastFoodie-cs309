package edu.iastate.graysonc.fastfood.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import edu.iastate.graysonc.fastfood.di.key.ViewModelKey;
import edu.iastate.graysonc.fastfood.view_models.FactoryViewModel;
import edu.iastate.graysonc.fastfood.view_models.FavoritesViewModel;
import edu.iastate.graysonc.fastfood.view_models.FoodProfileViewModel;
import edu.iastate.graysonc.fastfood.view_models.HomeViewModel;
import edu.iastate.graysonc.fastfood.view_models.ProfileViewModel;
import edu.iastate.graysonc.fastfood.view_models.SearchResultsViewModel;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel bindHomeViewModel(HomeViewModel repoViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SearchResultsViewModel.class)
    abstract ViewModel bindSearchResultsViewModel(SearchResultsViewModel repoViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel.class)
    abstract ViewModel bindFavoritesViewModel(FavoritesViewModel repoViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    abstract ViewModel bindProfileViewModel(ProfileViewModel repoViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FoodProfileViewModel.class)
    abstract ViewModel bindFoodProfileViewModel(FoodProfileViewModel repoViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(FactoryViewModel factory);
}