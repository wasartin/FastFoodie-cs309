package edu.iastate.graysonc.fastfood.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import edu.iastate.graysonc.fastfood.App;
import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.recycler_classes.FoodListAdapter;
import edu.iastate.graysonc.fastfood.view_models.FoodViewModel;

public class FavoritesFragment extends Fragment implements FoodListAdapter.OnItemClickListener {
    private static final String TAG = "FavoritesFragment";

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private FoodViewModel mViewModel;

    private RecyclerView mRecyclerView;
    private FoodListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private SwipeRefreshLayout refreshLayout;

    public FavoritesFragment() {}

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Configure Dagger 2
        AndroidSupportInjection.inject(this);

        // Configure ViewModel
        mViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(FoodViewModel.class);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(App.context);
        if (account != null) {
            mViewModel.initFavoriteFoods(account.getEmail());
            mViewModel.getFavoriteFoods().observe(this, f -> {
                if (f != null) {
                    for (Food tmp : f) {
                        Log.d(TAG, "onActivityCreated: Current favorites: " + tmp.getName());
                    }
                    refreshLayout.setRefreshing(false);
                    buildRecyclerView(f);
                    mAdapter.notifyDataSetChanged();
                }
            });
        }

        refreshLayout = getView().findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.loadFavoriteFoods();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (GoogleSignIn.getLastSignedInAccount(App.context) == null) {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_favoritesFragment_to_signInFragment);
        }
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    /*public void removeItem(int position) {
        Food selectedItem = mViewModel.getFavorites().getValue().get(position);
        Log.d(TAG, "removeItem: " + selectedItem.getName());
        mViewModel.removeFavorite(App.account.getEmail(), selectedItem.getId());
        mAdapter.notifyItemRemoved(position);
    }*/

    public void buildRecyclerView(List<Food> f) {
        mRecyclerView = getView().findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(App.context);
        mAdapter = new FoodListAdapter(f);

        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
        mViewModel.setSelectedFood(mViewModel.getFavoriteFoods().getValue().get(position));
        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_favoritesFragment_to_foodDetailFragment);
    }
}