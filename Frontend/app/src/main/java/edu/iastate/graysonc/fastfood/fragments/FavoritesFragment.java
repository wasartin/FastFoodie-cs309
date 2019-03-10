package edu.iastate.graysonc.fastfood.fragments;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import edu.iastate.graysonc.fastfood.App;
import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.recyclerClasses.FoodListAdapter;
import edu.iastate.graysonc.fastfood.recyclerClasses.RecyclerAdapter;
import edu.iastate.graysonc.fastfood.recyclerClasses.recycler_card;
import edu.iastate.graysonc.fastfood.view_models.FavoritesViewModel;
import edu.iastate.graysonc.fastfood.view_models.ProfileViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {
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
        /*Log.v("AddToFavoritesDebug",checkList().toString());
        Context context = getContext();
        Toast.makeText(context, "Removed " + checkList().toString(), Toast.LENGTH_LONG).show();*/
    }

    /**
     * Removes item at @pos from recycler
     * @param pos Position of item to be removed
     */
    public void removeItem(int pos) {
        favList.remove(pos);
        mAdapter.notifyDataSetChanged();
    }


    public void buildList() {
        //TODO populate with our data!
        favList = new ArrayList<>();
        favList.add(new recycler_card("Tripple Sub", "Ihop"));
        favList.add(new recycler_card("Pancake Double Stack", "Ihop"));
        favList.add(new recycler_card("sad Sub", "McDanks"));
        favList.add(new recycler_card("Moms Sub", "Subway"));
        favList.add(new recycler_card("Large Shake", "McDanks"));
        favList.add(new recycler_card("Double Burger", "McDanks"));
        favList.add(new recycler_card("McDouble", "McDanks"));
        favList.add(new recycler_card("Spicy Italian Sub", "Subway"));
        favList.add(new recycler_card("Chicken Parm  Sub", "Subway"));
        favList.add(new recycler_card("Marinara Sub", "Subway"));
        favList.add(new recycler_card("Pineapple Pizza", "Jeffs"));
        favList.add(new recycler_card("Vegan Sub", "Subway"));
        favList.add(new recycler_card("Soup Pizza", "Jeffs"));
        favList.add(new recycler_card("Salad", "Subway"));
        favList.add(new recycler_card("BMT Sub", "Subway"));
    }

    /**
     * Sorts using custom comparators
     *
     * @param restaurant Sort by restaurant
     */
    private void sortList(boolean restaurant) {
        if (restaurant) {
            Collections.sort(favList, (o1, o2) -> o1.getData().compareToIgnoreCase(o2.getData()));
        } else {
            Collections.sort(favList, (o1, o2) -> o1.getFood().compareToIgnoreCase(o2.getFood()));
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Foods Removed from favorites
     * @return A list of foods removed
     */
    private ArrayList<String> checkList() {
        ArrayList<String> removed = new ArrayList<>();
        for (recycler_card item : favList) {
            if (!item.isFavored()) {
                removed.add(item.getFood());
            }
        }
        return removed;
    }

    /**
     * Actually creates the Recycler View
     */
    public void buildView() {
        recyclerView = getView().findViewById(R.id.Favorites_Recycler);
        mAdapter = new FoodListAdapter(App.context);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(App.context));

        /*mAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //TODO actually open this
                Context context = getContext();
                Toast.makeText(context, "Open Data About " + favList.get(position).getFood(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFaveClick(int position) {
                //removeItem(position);
                recycler_card temp = favList.get(position);
                if (temp.isFavored()) {
                    temp.setFavored(false);
                } else {
                    temp.setFavored(true);
                }

            }
        });*/
    }


}

