package edu.iastate.graysonc.fastfood.fragments;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import edu.iastate.graysonc.fastfood.App;
import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.recyclerClasses.FavoritesListAdapter;
import edu.iastate.graysonc.fastfood.view_models.FavoritesViewModel;

public class FavoritesFragment extends Fragment {
    private static final String TAG = "FavoritesFragment";

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private FavoritesViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private FavoritesListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public FavoritesFragment() {}

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Configure Dagger 2
        AndroidSupportInjection.inject(this);

        // Configure ViewModel
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(FavoritesViewModel.class);
        if (App.account != null) {
            mViewModel.init(App.account.getEmail());
            mViewModel.getFavorites().observe(this, f -> {
                if (f != null) {
                    buildRecyclerView();
                    mAdapter.notifyDataSetChanged();
                }
            });
        }else{
            Fragment newFragment = new SignInFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.MasterLayoutFave, newFragment).commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    /**
     * Remove a food item both from the food list and the recycler view
     * @param position Position of item to remove
     */
    public void removeItem(int position) {
        Food selectedItem = mViewModel.getFavorites().getValue().get(position);
        Log.d(TAG, "removeItem: " + selectedItem.getName());
        mViewModel.removeFavorite(App.account.getEmail(), selectedItem.getId());
        mAdapter.notifyItemRemoved(position);
    }

    /**
     * Opens all extra information about a food object
     * @param position the position of a food item in our recycler view
     */
    public void openFoodPage(int position) {
        Food selectedItem = mViewModel.getFavorites().getValue().get(position);
        Log.d(TAG, "openFoodPage: " + selectedItem.getName());
        mAdapter.notifyItemChanged(position);
    }

    /**
     * Creates a recycler view, assigns it a handler and gives it data
     */
    public void buildRecyclerView() {
        mRecyclerView = getView().findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(App.context);
        mAdapter = new FavoritesListAdapter(mViewModel.getFavorites().getValue());

        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new FavoritesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                openFoodPage(position);
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }
}