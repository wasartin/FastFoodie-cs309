package edu.iastate.graysonc.fastfood.view_models;


import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import javax.inject.Inject;

import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.repositories.Repository;

public class FoodDataSourceFactory extends DataSource.Factory<Integer, Food> {
    private MutableLiveData<FoodDataSource> liveData;
    private Repository repository;

    @Inject
    public FoodDataSourceFactory(Repository repository) {
        this.repository = repository;
        liveData = new MutableLiveData<>();
    }

    public MutableLiveData<FoodDataSource> getMutableLiveData() {
        return liveData;
    }

    @Override
    public DataSource<Integer, Food> create() {
        FoodDataSource dataSourceClass = new FoodDataSource(repository);
        liveData.postValue(dataSourceClass);
        return dataSourceClass;
    }
}
