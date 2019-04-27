package edu.iastate.graysonc.fastfood.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import edu.iastate.graysonc.fastfood.App;
import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.view_models.FoodViewModel;

public class FoodDetailFragment extends Fragment implements View.OnClickListener {
    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    private FoodViewModel mViewModel;

    private TextView name, price, calories, protein, carbs, fat;
    private RatingBar ratingBar;
    private Button submitRatingButton;
    private View backButton;
    private ImageView favoriteButton;

    private RatingDialogFragment ratingDialogFragment;

    public FoodDetailFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_food_detail, container, false);
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

        submitRatingButton = getView().findViewById(R.id.submit_rating_button);
        submitRatingButton.setOnClickListener(this);
        ratingBar = getView().findViewById(R.id.rating_bar);

        backButton = getView().findViewById(R.id.back_button);
        backButton.setOnClickListener(this);
        favoriteButton = getView().findViewById(R.id.favorite_icon);
        if (GoogleSignIn.getLastSignedInAccount(App.context) == null) {
            favoriteButton.setVisibility(View.GONE);
        } else {
            favoriteButton.setOnClickListener(this);
        }


        // Configure ViewModel
        mViewModel = ViewModelProviders.of(getActivity(), mViewModelFactory).get(FoodViewModel.class);

        ratingDialogFragment = new RatingDialogFragment();

    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.getSelectedFood().observe(this, f -> {
            if (f != null) {
                name.setText(f.getName());
                price.setText(f.getPrice());
                calories.setText("" + f.getCalorieTotal());
                protein.setText(f.getProteinTotal() + "g");
                carbs.setText("" + f.getCarbTotal());
                fat.setText(f.getFatTotal() + "g");
                ratingBar.setRating((float)f.getRating());
                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        submitRatingButton.setVisibility(View.VISIBLE);
                        ratingBar.setStepSize(1);
                    }
                });
                if (f.getIsFavorite() == 1) {
                    favoriteButton.setImageResource(R.drawable.ic_favorite_filled);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).popBackStack();
                break;
            case R.id.submit_rating_button:
                mViewModel.submitRating(mViewModel.getSelectedFood().getValue().getId(), (int)ratingBar.getRating()); // TODO: Uncomment this when able to test
                ratingBar.setRating((float)mViewModel.getSelectedFood().getValue().getRating());
                submitRatingButton.setVisibility(View.GONE);
                break;
            case R.id.favorite_icon:
                Food f = mViewModel.getSelectedFood().getValue();
                String email = GoogleSignIn.getLastSignedInAccount(App.context).getEmail();
                if (f.getIsFavorite() == 0) {
                    mViewModel.addToFavorites(email, f.getId());
                    favoriteButton.setImageResource(R.drawable.ic_favorite_filled);
                } else if (f.getIsFavorite() == 1) {
                    mViewModel.removeFromFavorites(email, f.getId());
                    favoriteButton.setImageResource(R.drawable.ic_favorite_empty);
                }
                break;
        }
    }
}