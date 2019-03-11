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
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.view_models.HomeViewModel;
import edu.iastate.graysonc.fastfood.recyclerClasses.RecyclerAdapter;
import edu.iastate.graysonc.fastfood.recyclerClasses.recycler_card;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private HomeViewModel viewModel;
    private SearchView mSearchView;
    private RecyclerView mainList;
    ArrayList<recycler_card> foodList;
    RadioGroup mSortBy;
    RequestQueue requestQueue;
    RecyclerAdapter mAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Configure Dagger 2
        AndroidSupportInjection.inject(this);

        // Configure ViewModel
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel.class);
        foodList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        mAdapter = new RecyclerAdapter(foodList);
        mSortBy = getView().findViewById(R.id.searchByRadioGroup);
        mSortBy.setOnCheckedChangeListener((group, checkedId) -> {
            //2131230888 restaurant
            //2131230886 food
            Log.v("SearchByRadio", "Changed to " + checkedId);
        });
        mSearchView = getView().findViewById(R.id.SearchView);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                updateFaves();
                buildList(query);
                buildView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mSearchView.setIconifiedByDefault(true);
        mSearchView.setFocusable(true);
        mSearchView.setIconified(false);
        mSearchView.requestFocusFromTouch();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onDestroy() {
        //Logs foods to remove
        //TODO Remove this food from Favorites
        super.onDestroy();
        updateFaves();
    }

    private void buildList(String query) {
        RadioGroup mSortBy = getView().findViewById(R.id.searchByRadioGroup);
        //2131230888 restaurant
        //2131230886 food
        String url="";
        if (mSortBy.getCheckedRadioButtonId() == R.id.searchByFood) {
            url = "http://cs309-bs-1.misc.iastate.edu:8080/foods/all";
        }else if (mSortBy.getCheckedRadioButtonId() == R.id.searchByRes){
            url = "http://cs309-bs-1.misc.iastate.edu:8080/restaurants/all";
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                foodList.clear();
                JSONArray resArr = response.getJSONArray("data");
                for (int i = 0; i < resArr.length(); i++) {
                    JSONObject food = resArr.getJSONObject(i);
                    if (mSortBy.getCheckedRadioButtonId() == R.id.searchByRes) { //if filter by res
                        if (food.getString("restaurant_name").contains(query)) {
                            foodList.add(new recycler_card(food.getInt("food_id"), food.getString("restaurant_name"), food.getString("last_updated"), false,food.getInt("restaurant_id") ));
                        }
                    } else if (mSortBy.getCheckedRadioButtonId() == R.id.searchByFood) { //filter by food
                        if (food.getString("food_name").contains(query)) {
                            foodList.add(new recycler_card(food.getInt("food_id"), food.getString("food_name"), "Calories = " + food.getInt("calorie_total"), false,food.getInt("food_id")));
                        }
                    } else {
                        Log.v("FilterJsonErr", "Invalid selection of radio button");
                    }

                } mAdapter.notifyDataSetChanged();
                Log.v("HomeRecycle", "Updated data Set");
            } catch (JSONException e) {
                Log.v("JSO to JSA Err", e.toString());
            }
        }, error -> Log.v("JSRQError", error.toString()));

        requestQueue.add(jsonObjectRequest);
    }

    /**
     * Creates a recycler view in as many lines as possible
     */
    private void buildView() {
        mainList = Objects.requireNonNull(getView()).findViewById(R.id.HomeRecyclerView);
        mainList.setHasFixedSize(true); //Prevents dynamic resizing, improves performance
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mainList.setLayoutManager(mLayoutManager);
        mainList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //TODO actually open this
                Context context = getContext();
                Toast.makeText(context, "Open Data About " + foodList.get(position).getFood(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFaveClick(int position) {
                //removeItem(position);
                recycler_card temp = foodList.get(position);
                if (temp.isFavored()) {
                    viewModel.removeFavorite(getArguments().getString("USER_EMAIL"), temp.getFoodId());
                    temp.setFavored(false);
                } else {
                    viewModel.addFavorite(getArguments().getString("USER_EMAIL"), temp.getFoodId());
                    temp.setFavored(true);
                }
            }
        });
    }

    /**
     * Foods Added to favorites
     * @return A list of foods removed
     */
    private ArrayList<Integer> checkList(){
        ArrayList<Integer> removed = new ArrayList<>();
        for(recycler_card item : foodList){
            if(item.isFavored()) removed.add(item.getFoodId());
        }
        return removed;
    }

    public void updateFaves(){
        //TODO Add this food from Favorites
        Log.v("AddToFavoritesDebug",checkList().toString());
        Context context = getContext();
        Toast.makeText(context, "Added " + checkList().toString(), Toast.LENGTH_LONG).show();
    }
}
