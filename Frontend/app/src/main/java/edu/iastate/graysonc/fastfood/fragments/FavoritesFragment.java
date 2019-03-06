package edu.iastate.graysonc.fastfood.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.view_models.recyclerClasses.RecyclerAdapter;
import edu.iastate.graysonc.fastfood.view_models.recyclerClasses.recyler_card;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private android.support.v7.widget.RecyclerView mainList;
    private RecyclerAdapter mAdapter;
    private android.support.v7.widget.RecyclerView.LayoutManager mlayoutManager;
    ArrayList<recyler_card> favList;
    Switch sortby;
    //private HomeViewModel viewModel;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AndroidSupportInjection.inject(this);
        sortby = getView().findViewById(R.id.sortBySwitch);

        buildList();
        sortby.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sortList(isChecked);
            }
        });
        buildView();
        sortList(false);
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
        favList.add(new recyler_card("Tripple Sub", "Ihop"));
        favList.add(new recyler_card("Pancake Double Stack", "Ihop"));
        favList.add(new recyler_card("sad Sub", "McDanks"));
        favList.add(new recyler_card("Moms Sub", "Subway"));
        favList.add(new recyler_card("Large Shake", "McDanks"));
        favList.add(new recyler_card("Double Burger", "McDanks"));
        favList.add(new recyler_card("McDouble", "McDanks"));
        favList.add(new recyler_card("Spicy Italian Sub", "Subway"));
        favList.add(new recyler_card("Chicken Parm  Sub", "Subway"));
        favList.add(new recyler_card("Marinara Sub", "Subway"));
        favList.add(new recyler_card("Pineapple Pizza", "Jeffs"));
        favList.add(new recyler_card("Vegan Sub", "Subway"));
        favList.add(new recyler_card("Soup Pizza", "Jeffs"));
        favList.add(new recyler_card("Salad", "Subway"));
        favList.add(new recyler_card("BMT Sub", "Subway"));
    }

    public void buildView() {
        mainList = getView().findViewById(R.id.Favorites_Recycler);
        mainList.setHasFixedSize(true);
        mlayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new RecyclerAdapter(favList);
        mainList.setLayoutManager(mlayoutManager);
        mainList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListner(new RecyclerAdapter.OnItemClickListener() {
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

    public void sortList(boolean resturant) {
        if (resturant) {
            Collections.sort(favList, (o1, o2) -> o1.getData().compareToIgnoreCase(o2.getData()));
        } else {
            Collections.sort(favList, (o1, o2) -> o1.getFood().compareToIgnoreCase(o2.getFood()));
        }
        mAdapter.notifyDataSetChanged();
    }

}


