package edu.iastate.graysonc.fastfood.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import edu.iastate.graysonc.fastfood.activities.MainActivity;

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract MainActivity contributeMainActivity();
}