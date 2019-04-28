package edu.iastate.graysonc.fastfood.fragments;

import android.app.SearchManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import dagger.android.support.AndroidSupportInjection;
import edu.iastate.graysonc.fastfood.App;
import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.recycler_classes.FoodListAdapter;
import edu.iastate.graysonc.fastfood.view_models.SearchResultsViewModel;

import static com.android.volley.VolleyLog.TAG;

public class SearchResultsFragment extends Fragment implements View.OnClickListener {
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private SearchResultsViewModel mViewModel;

    private RecyclerView mRecyclerView;
    private FoodListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private View searchBar;
    private TextView searchText;
    private View backButton;

    public SearchResultsFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_results, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Configure Dagger 2
        AndroidSupportInjection.inject(this);

        // Configure ViewModel
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchResultsViewModel.class);

        searchBar = getView().findViewById(R.id.search_bar);
        searchBar.setOnClickListener(this);
        backButton = getView().findViewById(R.id.back_button);
        backButton.setOnClickListener(this);
        searchText = getView().findViewById(R.id.search_input);
    }

    @Override
    public void onResume() {
        super.onResume();
        String query = getArguments().getString(SearchManager.QUERY);
        searchText.setText(query);
        mViewModel.init(query);
        mViewModel.getFoods().observe(this, f -> {
            if (f != null) {
                buildRecyclerView();
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button: // TODO
                Log.d(TAG, "onClick: Back button selected");
                break;
        }
    }

    public void buildRecyclerView() {
        mRecyclerView = getView().findViewById(R.id.recyclerView1);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(App.context);
        mAdapter = new FoodListAdapter(mViewModel.getFoods().getValue());
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new FoodListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d(TAG, "onItemClick: " + mViewModel.getFoods().getValue().get(position).getName());
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
    }

}
