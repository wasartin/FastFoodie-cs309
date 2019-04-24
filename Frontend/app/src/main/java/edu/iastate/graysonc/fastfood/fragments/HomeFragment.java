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
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
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

import static androidx.constraintlayout.motion.widget.MotionScene.TAG;

public class HomeFragment extends Fragment implements FoodListAdapter.OnItemClickListener, View.OnClickListener {
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private HomeViewModel mViewModel;

    private RecyclerView mRecyclerView1, mRecyclerView2;
    private FoodListAdapter mAdapter1, mAdapter2;
    private RecyclerView.LayoutManager mLayoutManager;

    private View categoriesViewGroup;
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
                mViewModel.getFoods().observe(lifecycleOwner, f -> {
                    if (f != null) {
                        buildRecyclerView();
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
    }

    public void buildRecyclerView() {
        mRecyclerView1 = getView().findViewById(R.id.recyclerView1);
        mRecyclerView1.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(App.context);
        mAdapter1 = new FoodListAdapter(mViewModel.getFoods().getValue());
        mAdapter1.setOnItemClickListener(this);
        ((SimpleItemAnimator) mRecyclerView1.getItemAnimator()).setSupportsChangeAnimations(false);
        mRecyclerView1.setLayoutManager(mLayoutManager);
        mRecyclerView1.setAdapter(mAdapter1);
    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("foodId", mViewModel.getFoods().getValue().get(position).getId());
        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_homeFragment_to_foodProfileFragment, bundle);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.filter_button:

                break;
        }
    }
}
