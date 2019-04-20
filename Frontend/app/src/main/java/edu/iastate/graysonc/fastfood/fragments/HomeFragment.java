package edu.iastate.graysonc.fastfood.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import edu.iastate.graysonc.fastfood.App;
import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.activities.SearchActivity;
import edu.iastate.graysonc.fastfood.recyclerClasses.FavoritesListAdapter;
import edu.iastate.graysonc.fastfood.recyclerClasses.FoodListAdapter;
import edu.iastate.graysonc.fastfood.view_models.FavoritesViewModel;
import edu.iastate.graysonc.fastfood.view_models.HomeViewModel;

import static android.app.Activity.RESULT_OK;
import static android.support.constraint.Constraints.TAG;

public class HomeFragment extends Fragment implements View.OnClickListener {
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private HomeViewModel mViewModel;

    private RecyclerView mRecyclerView1, mRecyclerView2;
    private FoodListAdapter mAdapter1, mAdapter2;
    private RecyclerView.LayoutManager mLayoutManager;

    private View searchBar;

    public HomeFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Configure Dagger 2
        AndroidSupportInjection.inject(this);

        // Configure ViewModel
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel.class);
        if (App.account != null) {
            mViewModel.init();
            mViewModel.getFoods().observe(this, f -> {
                if (f != null) {
                    buildRecyclerView();
                    mAdapter1.notifyDataSetChanged();
                    mAdapter2.notifyDataSetChanged();
                }
            });
        }
        searchBar = getView().findViewById(R.id.search_bar);
        searchBar.setOnClickListener(this);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 0) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_bar:
                startActivityForResult(new Intent(getActivity(), SearchActivity.class), 0);
                break;
        }
    }

    public void buildRecyclerView() {
        mRecyclerView1 = getView().findViewById(R.id.recyclerView1);
        mRecyclerView1.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(App.context);
        mAdapter1 = new FoodListAdapter(mViewModel.getFoods().getValue());
        ((SimpleItemAnimator) mRecyclerView1.getItemAnimator()).setSupportsChangeAnimations(false);
        mRecyclerView1.setLayoutManager(mLayoutManager);
        mRecyclerView1.setAdapter(mAdapter1);

        mRecyclerView2 = getView().findViewById(R.id.recyclerView2);
        mRecyclerView2.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(App.context);
        mAdapter2 = new FoodListAdapter(mViewModel.getFoods().getValue());
        ((SimpleItemAnimator) mRecyclerView2.getItemAnimator()).setSupportsChangeAnimations(false);
        mRecyclerView2.setLayoutManager(mLayoutManager);
        mRecyclerView2.setAdapter(mAdapter2);


        mAdapter1.setOnItemClickListener(new FoodListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d(TAG, "onItemClick: " + mViewModel.getFoods().getValue().get(position).getName());
            }
        });

        mAdapter2.setOnItemClickListener(new FoodListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d(TAG, "onItemClick: " + mViewModel.getFoods().getValue().get(position).getName());
            }
        });
    }
}
