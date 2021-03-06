package edu.iastate.graysonc.fastfood.view_models;


import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import java.util.List;

import javax.inject.Inject;

import edu.iastate.graysonc.fastfood.App;
import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.database.entities.ResultList;
import edu.iastate.graysonc.fastfood.repositories.Repository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.motion.widget.MotionScene.TAG;

public class FoodDataSource extends PageKeyedDataSource<Integer, Food> {
    private Repository repository;
    private int sourceIndex = 0;
    private MutableLiveData<String> progressLiveStatus;

    private String query;

    @Inject
    FoodDataSource(Repository repository, String query) {
        this.repository = repository;
        progressLiveStatus = new MutableLiveData<>();
        this.query = query;
    }


    public MutableLiveData<String> getProgressLiveStatus() {
        return progressLiveStatus;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Food> callback) {
        progressLiveStatus.postValue(App.LOADING);
        repository.getFoodMatches(query, sourceIndex).enqueue(new Callback<ResultList>() {
            @Override
            public void onResponse(Call<ResultList> call, Response<ResultList> response) {
                if (response.body() != null) {
                    progressLiveStatus.postValue(App.LOADED);
                    List<Food> foods = response.body().getContent();
                    for (int i = 0; i < foods.size(); i++) {
                        int finalI = i;
                        repository.getAverageRating(foods.get(i).getId()).enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (response.body() != null) {
                                    double r = 0.0;
                                    if (!response.body().equals("NaN")) {
                                        r = Double.parseDouble(response.body());
                                    }
                                    Log.d(TAG, "onResponse: Rating = " + r);
                                    foods.get(finalI).setRating(r);
                                }
                                if (finalI == foods.size() - 1) {
                                    sourceIndex++;
                                    callback.onResult(foods, null, sourceIndex);
                                }
                            }
                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ResultList> call, Throwable t) {
                progressLiveStatus.postValue(App.LOADED);
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Food> callback) {

    }

    @SuppressLint("CheckResult")
    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Food> callback) {

        repository.getFoodMatches(query, params.key).enqueue(new Callback<ResultList>() {
            @Override
            public void onResponse(Call<ResultList> call, Response<ResultList> response) {
                if (response.body() != null) {
                    progressLiveStatus.postValue(App.LOADED);
                    sourceIndex++;
                    ResultList resultList = response.body();
                    List<Food> foods = response.body().getContent();
                    for (int i = 0; i < foods.size(); i++) {
                        int finalI = i;
                        repository.getAverageRating(foods.get(i).getId()).enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (response.body() != null) {
                                    double r = 0.0;
                                    if (!response.body().equals("NaN")) {
                                        r = Double.parseDouble(response.body());
                                    }
                                    Log.d(TAG, "onResponse: Rating = " + r);
                                    foods.get(finalI).setRating(r);
                                }
                                if (finalI == foods.size() - 1) {
                                    callback.onResult(foods, resultList.isLast() ? null : params.key + 1);
                                }
                            }
                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<ResultList> call, Throwable t) {
                progressLiveStatus.postValue(App.LOADED);
            }
        });
    }
}

