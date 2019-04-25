package edu.iastate.graysonc.fastfood.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.view_models.FactoryViewModel;
import edu.iastate.graysonc.fastfood.view_models.FoodProfileViewModel;
import edu.iastate.graysonc.fastfood.view_models.FoodViewModel;

public class FoodProfileFragment extends Fragment implements View.OnClickListener {
    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    private FoodViewModel mViewModel;

    private TextView name, price, calories, protein, carbs, fat;
    private RatingBar ratingBar;
    private View backButton;

    public FoodProfileFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_food_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Configure Dagger 2
        AndroidSupportInjection.inject(this);

        // Bind views
        name = getView().findViewById(R.id.food_name);
        price = getView().findViewById(R.id.price_text);
        calories = getView().findViewById(R.id.calorie_text);
        protein = getView().findViewById(R.id.protein_text);
        carbs = getView().findViewById(R.id.carbohydrate_text);
        fat = getView().findViewById(R.id.fat_text);
        ratingBar = getView().findViewById(R.id.rating_bar);
        backButton = getView().findViewById(R.id.back_button);
        backButton.setOnClickListener(this);

        // Configure ViewModel
        mViewModel = ViewModelProviders.of(getActivity(), mViewModelFactory).get(FoodViewModel.class);

    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.getSelectedFood().observe(this, f -> {
            if (f != null) {
                name.setText(f.getName());
                price.setText(f.getPrice());
                calories.setText("" + f.getCalorieTotal());
                protein.setText("" + f.getProteinTotal());
                carbs.setText("" + f.getCarbTotal());
                fat.setText("" + f.getFatTotal());
                ratingBar.setRating((float)f.getRating());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).popBackStack();
                break;
        }
    }
}