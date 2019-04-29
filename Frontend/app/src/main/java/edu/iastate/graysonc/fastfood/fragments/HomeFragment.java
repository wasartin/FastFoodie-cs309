package edu.iastate.graysonc.fastfood.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import edu.iastate.graysonc.fastfood.App;
import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.recycler_classes.FoodListAdapter;
import edu.iastate.graysonc.fastfood.view_models.FoodViewModel;

public class HomeFragment extends Fragment implements FoodListAdapter.OnItemClickListener, View.OnClickListener {
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private FoodViewModel mViewModel;

    private FoodListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;
    private SearchView searchView;

    private FilterDialogFragment filterFragment;
    private SortDialogFragment sortDialogFragment;

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
        mViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(FoodViewModel.class);
        progressBar = getView().findViewById(R.id.progressBar);
        mViewModel.getSearchResults().observe(this, f -> {
            buildRecyclerView(f);
            mAdapter.notifyDataSetChanged();
        });

        // Configure SearchView
        searchView = getView().findViewById(R.id.search_bar);
        LifecycleOwner lifecycleOwner = this;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressBar.setVisibility(View.VISIBLE);
                mViewModel.doSearch(searchView.getQuery().toString());
                mViewModel.getSearchResults().observe(lifecycleOwner, f -> {
                    if (f != null) {
                        buildRecyclerView(f);
                        mAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
                });
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        getView().findViewById(R.id.filter_button).setOnClickListener(this);
        getView().findViewById(R.id.sort_button).setOnClickListener(this);

        filterFragment = new FilterDialogFragment();
        sortDialogFragment = new SortDialogFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onItemClick(int position) {
        if (mViewModel.getSearchResults().getValue() == null) {
            mViewModel.getSearchResults().observeForever(f -> {
                if (position < f.size()) {
                    mViewModel.setSelectedFood(f.get(position));
                    mViewModel.getSearchResults().removeObservers(this);
                }
            });
        } else {
            mViewModel.setSelectedFood(mViewModel.getSearchResults().getValue().get(position));
        }
        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_homeFragment_to_foodDetailFragment);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.filter_button:
                filterFragment.show(getActivity().getSupportFragmentManager(), "filter_fragment");
                break;
            case R.id.sort_button:
                sortDialogFragment.show(getActivity().getSupportFragmentManager(), "sort_fragment");
                break;
        }
    }

    public void buildRecyclerView(List<Food> f) {
        mRecyclerView = getView().findViewById(R.id.recyclerView1);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(App.context);
        mAdapter = new FoodListAdapter(f);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }
}
