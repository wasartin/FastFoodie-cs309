package edu.iastate.graysonc.fastfood.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import edu.iastate.graysonc.fastfood.App;
import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.recyclerClasses.FoodListAdapter;
import edu.iastate.graysonc.fastfood.recyclerClasses.RecyclerAdapter;
import edu.iastate.graysonc.fastfood.recyclerClasses.recycler_card;
import edu.iastate.graysonc.fastfood.view_models.FavoritesViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment implements View.OnClickListener {
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private FavoritesViewModel viewModel;

    private RecyclerView recyclerView;

    private android.support.v7.widget.RecyclerView mainList;
    private FoodListAdapter mAdapter;
    private android.support.v7.widget.RecyclerView.LayoutManager mlayoutManager;
    ArrayList<recycler_card> favList;
    RadioGroup mSortBy;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AndroidSupportInjection.inject(this);

        buildView();

        // Configure ViewModel
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FavoritesViewModel.class);
        viewModel.init(getArguments().getString("USER_EMAIL"));
        mAdapter.setFoods(viewModel.getFavorites().getValue());
        viewModel.getFavorites().observe(this, foods -> {
            mAdapter.setFoods(foods);
        });
        recyclerView.setOnClickListener(this);


        //Assign a radio group and handle Changes
        mSortBy = Objects.requireNonNull(getView()).findViewById(R.id.SortByRadioGroup);
        mSortBy.setOnCheckedChangeListener((group, checkedId) -> {
            if (mSortBy.getCheckedRadioButtonId() == R.id.sortByFood) { //Search by food group
                sortList(false);
            } else if (mSortBy.getCheckedRadioButtonId() == R.id.sortByRes) { //Search by restaurant
                sortList(true);
            } else {
                Log.e("RadioButtonErr", "Expected 2131230907/8, got: " + checkedId);
            }
        });

        //sortList(false); //Sorts view by list type
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onDestroy() {
        //Logs foods to remove
        //TODO Remove this food from Favorites
        super.onDestroy();
        removeNonFavorites();
    }

    /**
     * Removes item at @pos from recycler
     * @param pos Position of item to be removed
     */
    public void removeItem(int pos) {
        favList.remove(pos);
        mAdapter.notifyDataSetChanged();
    }

//    /**
//     * Sorts using custom comparators
//     *
//     * @param restaurant Sort by restaurant
//     */
//    private void sortList(boolean restaurant) {
//        if (restaurant) {
//            Collections.sort(favList, (o1, o2) -> o1.getData().compareToIgnoreCase(o2.getData()));
//        } else {
//            Collections.sort(favList, (o1, o2) -> o1.getFood().compareToIgnoreCase(o2.getFood()));
//        }
//        mAdapter.notifyDataSetChanged();
//    }

    private void sortList(boolean restaurant){
        if (restaurant) {
            Collections.sort(viewModel.getFavorites().getValue(), (o1, o2) -> o1.getLocation()-(o2.getLocation()));
        } else {
            Collections.sort(viewModel.getFavorites().getValue(), (o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
        }
        mAdapter.notifyDataSetChanged();
    }
    //viewModel.getFavorites().getValue()

    /**
     * Foods Removed from favorites
     * @return A list of foods removed
     */
    private void removeNonFavorites() {
        for (Food f : viewModel.getFavorites().getValue()) {
            if (!f.isFavorite()) {
                viewModel.removeFavorite(getArguments().getString("USER_EMAIL"), f.getId());
            }
        }
    }

    /**
     * Actually creates the Recycler View
     */
    public void buildView() {
        recyclerView = getView().findViewById(R.id.Favorites_Recycler);
        mAdapter = new FoodListAdapter(App.context);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(App.context));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.favorite_button: // TODO: Enable user's to remove favorites. This block doesn't work.
                viewModel.getFavorites().getValue().get(recyclerView.getChildAdapterPosition(v)).setFavorite(((CheckBox) v).isChecked());
                break;
            case R.layout.recycler_card:
                Toast.makeText(App.context, "Food info page coming soon", Toast.LENGTH_SHORT);
                break;
        }
    }
}

