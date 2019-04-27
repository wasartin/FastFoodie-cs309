package edu.iastate.graysonc.fastfood.fragments;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import androidx.recyclerview.widget.SimpleItemAnimator;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import edu.iastate.graysonc.fastfood.App;
import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.activities.MainActivity;
import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.recycler_classes.FoodListAdapter;
import edu.iastate.graysonc.fastfood.view_models.FoodViewModel;

import static androidx.constraintlayout.motion.widget.MotionScene.TAG;

public class HomeFragment extends Fragment implements FoodListAdapter.OnItemClickListener, View.OnClickListener {
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private FoodViewModel mViewModel;

    private RecyclerView mRecyclerView1, mRecyclerView2;
    private FoodListAdapter mAdapter1, mAdapter2;
    private RecyclerView.LayoutManager mLayoutManager;

    private View categoriesViewGroup;
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

        // Configure SearchView
        //categoriesViewGroup = getView().findViewById(R.id.categories_view_group);
        searchView = getView().findViewById(R.id.search_bar);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        ComponentName componentName = new ComponentName(getActivity(), MainActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));
        LifecycleOwner lifecycleOwner = this;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onClick: Search submitted");
                mViewModel.doSearch(searchView.getQuery().toString());
                mViewModel.getSearchResults().observe(lifecycleOwner, f -> {
                    if (f != null) {
                        buildRecyclerView(f);
                        mAdapter1.notifyDataSetChanged();
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
        mViewModel.getSearchResults().observe(this, f -> {
            if (f != null) {
                buildRecyclerView(f);
                mAdapter1.notifyDataSetChanged();
            }
        });
    }

    public void buildRecyclerView(List<Food> f) {
        mRecyclerView1 = getView().findViewById(R.id.recyclerView1);
        mRecyclerView1.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(App.context);
        mAdapter1 = new FoodListAdapter(f);
        mAdapter1.setOnItemClickListener(this);
        ((SimpleItemAnimator) mRecyclerView1.getItemAnimator()).setSupportsChangeAnimations(false);
        mRecyclerView1.setLayoutManager(mLayoutManager);
        mRecyclerView1.setAdapter(mAdapter1);
    }

    @Override
    public void onItemClick(int position) {
        mViewModel.setSelectedFood(mViewModel.getSearchResults().getValue().get(position));
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
}
