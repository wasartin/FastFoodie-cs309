package edu.iastate.graysonc.fastfood.fragments;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import edu.iastate.graysonc.fastfood.App;
import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.activities.MainActivity;
import edu.iastate.graysonc.fastfood.recycler_classes.SearchResultsAdapter;
import edu.iastate.graysonc.fastfood.view_models.FoodViewModel;

public class HomeFragment extends Fragment implements SearchResultsAdapter.OnItemClickListener, View.OnClickListener {
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private FoodViewModel mViewModel;

    private SearchResultsAdapter adapter;
    private RecyclerView recyclerView;
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
        adapter = new SearchResultsAdapter();
        recyclerView = getView().findViewById(R.id.recyclerView1);
        progressBar = getView().findViewById(R.id.progressBar);


        // Configure SearchView
        searchView = getView().findViewById(R.id.search_bar);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        ComponentName componentName = new ComponentName(getActivity(), MainActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));
        // TODO: Set listener for searchView


        getView().findViewById(R.id.filter_button).setOnClickListener(this);
        getView().findViewById(R.id.sort_button).setOnClickListener(this);

        filterFragment = new FilterDialogFragment();
        sortDialogFragment = new SortDialogFragment();

        init();
    }

    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(App.context));
        SearchResultsAdapter adapter = new SearchResultsAdapter();
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        if (!App.checkInternetConnection(App.context)) {
            Toast.makeText(App.context, "Network error", Toast.LENGTH_SHORT).show();
        }

        mViewModel.getSearchResults().observe(this, adapter::submitList);
        mViewModel.getProgressLoadStatus().observe(this, status -> {
            if (Objects.requireNonNull(status).equalsIgnoreCase(App.LOADING)) {
                progressBar.setVisibility(View.VISIBLE);
            } else if (status.equalsIgnoreCase(App.LOADED)) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
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
