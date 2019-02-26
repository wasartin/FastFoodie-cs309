package edu.iastate.graysonc.fastfood;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();
}
