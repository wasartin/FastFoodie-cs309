package edu.iastate.graysonc.fastfood.fragments;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import edu.iastate.graysonc.fastfood.App;
import edu.iastate.graysonc.fastfood.DownloadImageTask;
import edu.iastate.graysonc.fastfood.PopUps.submitPopUp;
import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.database.entities.User;
import edu.iastate.graysonc.fastfood.view_models.ProfileViewModel;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ProfileViewModel viewModel;

    private ConstraintLayout mMasterLayout;

    private ImageView avatarImageView;
    private TextView nameTextView;
    private TextView mUserInfoDisp;
    private ImageButton mMenuExpand;
    private ScrollView mHorizontalScroller;
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
        mHorizontalScroller = getView().findViewById(R.id.HorizontalScroller);
        mProfileGroupRadioGroup = getView().findViewById(R.id.ProfileGroupRadioGroup);
       mMasterLayout = getView().findViewById(R.id.MasterLayout);

        fINAnim = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in);
        fINAnim.setDuration(600);
        fOUTAnim = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out);
        fOUTAnim.setDuration(300);

        // Configure ViewModel
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProfileViewModel.class);
        if (App.account != null) {
            viewModel.init(App.account.getEmail());
            initUI(App.account);
            viewModel.getUser().observe(this, user -> {
                if (user != null) {
                    updateUI(user);
                }
            });
        }else{
            mHorizontalScroller.setVisibility(View.GONE);
            Fragment newFragment = new SignInFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.MasterLayout, newFragment).commit();
        }
        // Create Click Listeners
        mProfileGroupRadioGroup.setOnCheckedChangeListener(this);
        mMenuExpand.setOnClickListener(v -> toggleMenuVisible());


        mHorizontalScroller.setOnTouchListener((v, event) -> {
            //If rotation = sideways
            return getResources().getConfiguration().orientation == 1;
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    /**
     * Is called automatically whenever data in ProfileViewModel is changed.
     *
     * @param user
     */
    private void updateUI(User user) {
        mUserInfoDisp.setText("Email: " + user.getEmail() + "\n");
        mUserInfoDisp.append("User type: " + user.getType());
    }

    /**
     * Adds user's name and profile picture to GUI
     */
    public void initUI(GoogleSignInAccount account) {
        nameTextView.setText(account.getDisplayName());
        Uri avatarUri = account.getPhotoUrl();
        if (avatarUri != null) {
            DownloadImageTask imageDownloader = new DownloadImageTask(avatarImageView); // Downloads the user's avatar asynchronously
            imageDownloader.execute(avatarUri.toString());
        }
    }

    /**
     * Uses Google Api To Sign Out
     */
    public void signOut() {

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
                toggleMenuVisible();
                createWarning("Sign user out");
                break;
            case R.id.editDataRadioButton:
                toggleMenuVisible();
                createWarning("Open Edit Users Page");
                break;
        }
    }
}
