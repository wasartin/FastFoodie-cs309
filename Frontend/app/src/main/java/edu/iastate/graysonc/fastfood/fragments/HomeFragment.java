package edu.iastate.graysonc.fastfood.fragments;


import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import edu.iastate.graysonc.fastfood.App;
import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.activities.MainActivity;
import edu.iastate.graysonc.fastfood.recycler_classes.FoodListAdapter;
import edu.iastate.graysonc.fastfood.view_models.HomeViewModel;

public class HomeFragment extends Fragment implements FoodListAdapter.OnItemClickListener {
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private HomeViewModel mViewModel;

    private RecyclerView mRecyclerView1, mRecyclerView2;
    private FoodListAdapter mAdapter1, mAdapter2;
    private RecyclerView.LayoutManager mLayoutManager;

    private SearchView searchView;


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

        searchView = getView().findViewById(R.id.search_bar);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        ComponentName componentName = new ComponentName(getActivity(), MainActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));


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
        mAdapter1.setOnItemClickListener(this);
        mAdapter2.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("foodId", mViewModel.getFoods().getValue().get(position).getId());
        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_homeFragment_to_foodProfileFragment, bundle);
    }
}
