package edu.iastate.graysonc.fastfood.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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
import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.view_models.recyclerClasses.RecyclerAdapter;
import edu.iastate.graysonc.fastfood.view_models.recyclerClasses.recycler_card;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private android.support.v7.widget.RecyclerView mainList;
    private RecyclerAdapter mAdapter;
    private android.support.v7.widget.RecyclerView.LayoutManager mlayoutManager;
    ArrayList<recycler_card> favList;
    RadioGroup mSortBy;
    //private HomeViewModel viewModel;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AndroidSupportInjection.inject(this);
        buildList(); //Creates a fake List

        //Assign a radio group and handle Changes
        mSortBy = Objects.requireNonNull(getView()).findViewById(R.id.SortByRadioGroup);
        mSortBy.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == 2131230907) {
                sortList(false);
            } else if (checkedId == 2131230908) {
                sortList(true);
            } else {
                Log.v("RadioButtonErr", "Expected 2131230907/8, got: " + checkedId);
            }
        });


        buildView();//Creates a recycle view

        sortList(false); //Sorts view by list type
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    public void removeItem(int pos) {
        //TODO actually defavorite this

        Context context = getContext();
        Toast.makeText(context, "Removing " + favList.get(pos).getFood() + " from favorites", Toast.LENGTH_SHORT).show();
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

    public void buildView() {
        mainList = Objects.requireNonNull(getView()).findViewById(R.id.Favorites_Recycler);
        mainList.setHasFixedSize(true); //Prevents dynamic resizing, improves performance
        mlayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new RecyclerAdapter(favList);
        mainList.setLayoutManager(mlayoutManager);
        mainList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //TODO actually open this
                Context context = getContext();
                Toast.makeText(context, "Open Data About " + favList.get(position).getFood(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
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

}

