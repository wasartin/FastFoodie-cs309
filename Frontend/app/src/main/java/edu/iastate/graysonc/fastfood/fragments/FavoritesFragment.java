package edu.iastate.graysonc.fastfood.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import dagger.android.support.AndroidSupportInjection;
import edu.iastate.graysonc.fastfood.App;
import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.recycler_classes.FavoritesListAdapter;
import edu.iastate.graysonc.fastfood.recycler_classes.FoodListAdapter;
import edu.iastate.graysonc.fastfood.view_models.FavoritesViewModel;

public class FavoritesFragment extends Fragment implements FoodListAdapter.OnItemClickListener {
    private static final String TAG = "FavoritesFragment";

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private FavoritesViewModel mViewModel;

    private RecyclerView mRecyclerView;
    private FoodListAdapter mAdapter;
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
        } else {
            Fragment newFragment = new SignInFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.MasterLayoutFave, newFragment).commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    public void removeItem(int position) {
        Food selectedItem = mViewModel.getFavorites().getValue().get(position);
        Log.d(TAG, "removeItem: " + selectedItem.getName());
        mViewModel.removeFavorite(App.account.getEmail(), selectedItem.getId());
        mAdapter.notifyItemRemoved(position);
    }

    public void buildRecyclerView() {
        mRecyclerView = getView().findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(App.context);
        mAdapter = new FoodListAdapter(mViewModel.getFavorites().getValue());

        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("foodId", mViewModel.getFavorites().getValue().get(position).getId());
        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_favoritesFragment_to_foodProfileFragment, bundle);
    }
}