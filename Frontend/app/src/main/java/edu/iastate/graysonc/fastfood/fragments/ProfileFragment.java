package edu.iastate.graysonc.fastfood.fragments;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import edu.iastate.graysonc.fastfood.DownloadImageTask;
import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.database.entities.User;
import edu.iastate.graysonc.fastfood.view_models.ProfileViewModel;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ProfileViewModel viewModel;

    private Button signOutButton;
    private ImageView avatarImageView;
    private TextView nameTextView;
    private boolean toggled;
    private TextView mUserInfoDisp;
    private TextView mUserDietaryDisp;
    private Button mMenuTicket;
    private Button mMenuEdit;
    private ImageButton mMenuExpand;

    /**
     * Required empty constructor
     */
    public ProfileFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Configure Dagger 2
        AndroidSupportInjection.inject(this);

        //Initialize Variables and point to correct XML objects
        toggled = false;
        signOutButton = getView().findViewById(R.id.sign_out_button);
        avatarImageView = getView().findViewById(R.id.avatar_image_view);
        nameTextView = getView().findViewById(R.id.name_text_view);
        mUserInfoDisp = getView().findViewById(R.id.user_info_display);
        mUserDietaryDisp = getView().findViewById(R.id.user_dietary_display);
        mMenuTicket = getView().findViewById(R.id.TicketButton);
        mMenuEdit = getView().findViewById(R.id.ButtonEdit);
        mMenuExpand = getView().findViewById(R.id.MenuButton);

        // Get profile picture and name from Google Signin
        GoogleSignInAccount account = getArguments().getParcelable("ACCOUNT");
        Log.e(TAG, "onActivityCreated: Account recieved from " + account.getDisplayName());
        initUI(account);

        // Configure ViewModel
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProfileViewModel.class);
        viewModel.init(account.getEmail());
        viewModel.getUser().observe(this, user -> {
            if (user != null) {
                updateUI(user);
            }
        });

        //Create Click Listeners
        signOutButton.setOnClickListener(this);
        mMenuEdit.setOnClickListener(this);
        mMenuTicket.setOnClickListener(this);
        mMenuExpand.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    /**
     * Is called automatically whenever data in ProfileViewModel is changed.
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
        // TODO
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_out_button:
                signOut();
                break;
            case R.id.ButtonEdit:
                createWarning("Open Edit Users Page");
                break;
            case R.id.TicketButton:
                createWarning("Open Submit Ticket");
                break;
            case R.id.MenuButton:
                toggleMenuVisible();
                break;
        }
    }

    /**
     * Toggles Visibility Of Buttons
     */
    public void toggleMenuVisible() {
        if (toggled) {
            mMenuTicket.setVisibility(View.INVISIBLE);
            mMenuEdit.setVisibility(View.INVISIBLE);
            signOutButton.setVisibility(View.INVISIBLE);
        } else {
            mMenuTicket.setVisibility(View.VISIBLE);
            mMenuEdit.setVisibility(View.VISIBLE);
            signOutButton.setVisibility(View.VISIBLE);
        }
        toggled = !toggled;
    }

    /**
     * Creates A Toast Message With Content @message
     * @param message The message to be displayed
     */
    public void createWarning(String message) {
        Context context = getContext();
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
