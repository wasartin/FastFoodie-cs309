package edu.iastate.graysonc.fastfood.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import edu.iastate.graysonc.fastfood.di.key.ViewModelKey;
import edu.iastate.graysonc.fastfood.view_models.FactoryViewModel;
import edu.iastate.graysonc.fastfood.view_models.FavoritesViewModel;
import edu.iastate.graysonc.fastfood.view_models.HomeViewModel;
import edu.iastate.graysonc.fastfood.view_models.ProfileViewModel;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel bindHomeViewModel(HomeViewModel repoViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel.class)
    abstract ViewModel bindFavoritesViewModel(FavoritesViewModel repoViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    abstract ViewModel bindProfileViewModel(ProfileViewModel repoViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(FactoryViewModel factory);
}