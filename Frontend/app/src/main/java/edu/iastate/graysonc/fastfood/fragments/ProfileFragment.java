package edu.iastate.graysonc.fastfood.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
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
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import edu.iastate.graysonc.fastfood.App;
import edu.iastate.graysonc.fastfood.DownloadImageTask;
import edu.iastate.graysonc.fastfood.PopUps.submitPopUp;
import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.view_models.ProfileViewModel;

public class ProfileFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ProfileViewModel viewModel;

    private ImageView avatarImageView;
    private TextView nameTextView;
    private TextView mUserInfoDisp;
    private ImageButton mMenuExpand;
    private Animation fINAnim;
    private Animation fOUTAnim;
    private RadioGroup mProfileGroupRadioGroup;

    private boolean toggled;

    /**
     * Required empty constructor
     */
    public ProfileFragment() {
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    /**
     * Creates our fragment
     * Assigns on click handlers
     * Assigns button ids
     * Assigns other variables to different values
     */
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Configure Dagger 2
        AndroidSupportInjection.inject(this);

        //Initialize Variables and point to correct XML objects
        toggled = false;
        avatarImageView = getView().findViewById(R.id.avatar_image_view);
        nameTextView = getView().findViewById(R.id.name_text_view);
        mUserInfoDisp = getView().findViewById(R.id.user_info_display);
        mMenuExpand = getView().findViewById(R.id.MenuButton);
        mProfileGroupRadioGroup = getView().findViewById(R.id.ProfileGroupRadioGroup);

        fINAnim = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in);
        fINAnim.setDuration(600);
        fOUTAnim = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out);
        fOUTAnim.setDuration(300);

        // Configure ViewModel
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProfileViewModel.class);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(App.context);
        if (account != null) {
            viewModel.init(GoogleSignIn.getLastSignedInAccount(App.context));
            viewModel.getUser().observe(this, user -> {
                if (user != null) {
                    updateUI();
                }
            });
        }

        // Create Click Listeners
        mProfileGroupRadioGroup.setOnCheckedChangeListener(this);
        mMenuExpand.setOnClickListener(v -> toggleMenuVisible());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (GoogleSignIn.getLastSignedInAccount(App.context) == null) {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_profileFragment_to_signInFragment);
        }
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    /**
     * Is called automatically whenever data in ProfileViewModel is changed.
     */
    private void updateUI() {
        nameTextView.setText(viewModel.getGoogleSignInAccount().getDisplayName());
        Uri avatarUri = viewModel.getGoogleSignInAccount().getPhotoUrl();
        if (avatarUri != null) {
            DownloadImageTask imageDownloader = new DownloadImageTask(avatarImageView); // Downloads the user's avatar asynchronously
            imageDownloader.execute(avatarUri.toString());
        }
        mUserInfoDisp.setText("Email: " + viewModel.getUser().getValue().getEmail() + "\n");
        mUserInfoDisp.append("User type: " + viewModel.getUser().getValue().getType());
    }

    /**
     * Uses Google Api To Sign Out
     */
    public void signOut() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignIn.getClient(App.context, gso).signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_profileFragment_to_signInFragment);
                    }
                });
    }

    /**
     * Toggles Visibility Of Buttons
     */
    public void toggleMenuVisible() {
        mProfileGroupRadioGroup.check(R.id.emptyRadioButton);
        if (toggled) {
            mMenuExpand.setImageResource(R.drawable.drop_down_light);
            mProfileGroupRadioGroup.setVisibility(View.GONE);
            mProfileGroupRadioGroup.startAnimation(fOUTAnim);

        } else {
            mMenuExpand.setImageResource(R.drawable.drop_down_dark);
            mProfileGroupRadioGroup.setVisibility(View.VISIBLE);
            mProfileGroupRadioGroup.startAnimation(fINAnim);
        }
        toggled = !toggled;
    }

    /**
     * Creates A Toast Message With Content @message
     *
     * @param message The message to be displayed
     */
    public void createWarning(String message) {
        Context context = getContext();
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    /**
     * Handles different button groups and what to process in a click
     */
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.ticketRadioButton:
                toggleMenuVisible();
                startActivity(new Intent(getContext(), submitPopUp.class));
                break;
            case R.id.singOutRadioButton:
                signOut();
                break;
            case R.id.editDataRadioButton:
                toggleMenuVisible();
                createWarning("Open Edit Users Page");
                break;
        }
    }
}
