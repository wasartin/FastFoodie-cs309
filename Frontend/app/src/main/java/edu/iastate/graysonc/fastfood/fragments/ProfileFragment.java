package edu.iastate.graysonc.fastfood.fragments;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.Random;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.android.support.AndroidSupportInjection;
import edu.iastate.graysonc.fastfood.DownloadImageTask;
import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.database.entities.User;
import edu.iastate.graysonc.fastfood.view_models.ProfileViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {
    private GoogleSignInAccount account;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ProfileViewModel viewModel;

    Button signOutButton;
    ImageView avatarImageView;
    TextView nameTextView;

    private boolean toggled;
    TextView mUserInfoDisp;
    TextView mUserDietaryDisp;
    Button mMenuTicket;
    Button mMenuEdit;
    ImageButton mMenuExpand;
    private RequestQueue r;

    /**
     * Required empty constructor
     */
    public ProfileFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AndroidSupportInjection.inject(this);

        signOutButton = getView().findViewById(R.id.sign_out_button);
        avatarImageView = getView().findViewById(R.id.avatar_image_view);
        nameTextView = getView().findViewById(R.id.name_text_view);
        mUserInfoDisp = getView().findViewById(R.id.user_info_display);
        mUserDietaryDisp = getView().findViewById(R.id.user_dietary_display);
        mMenuTicket = getView().findViewById(R.id.TicketButton);
        mMenuEdit = getView().findViewById(R.id.ButtonEdit);
        mMenuExpand = getView().findViewById(R.id.MenuButton);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProfileViewModel.class);
        account = getArguments().getParcelable("ACCOUNT");
        if (account != null) {
            initUI(account);
            viewModel.init(account.getEmail());
            viewModel.getUser().observe(this, user -> {
                updateUI(user);
            });
        }


        //Initialize Variables and point to correct XML objects
        toggled = false;
        r = Volley.newRequestQueue(getContext());

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

    private void updateUI(User user) {
        mUserInfoDisp.setText("Email: " + user.getEmail() + "\n");
        mUserInfoDisp.append("User type: " + user.getType());
    }

    /**
     * Toggles between signed in and signed out GUI's
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
     * Opens SignInActivity
     */
    public void signIn() {
        // TODO
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
    public void toggleMenuVisible(){
        if(toggled){
            mMenuTicket.setVisibility(View.INVISIBLE);
            mMenuEdit.setVisibility(View.INVISIBLE);
            signOutButton.setVisibility(View.INVISIBLE);
        }else{
            mMenuTicket.setVisibility(View.VISIBLE);
            mMenuEdit.setVisibility(View.VISIBLE);
            signOutButton.setVisibility(View.VISIBLE);
        }
        toggled=!toggled;
    }

    /**
     * Creates A Toast Message With Content @message
     * @param message The message to be displayed
     */
    public void createWarning(String message){
        Context context = getContext();
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Creates a new user
     * @param UUID
     */
    private void createUserData(String UUID){
        //TODO
    }

    /**
     * Creates a request to a page and formats it, appending results to the main screen
     * @param URL The Page To Process
     */
    private void jsonParse(String URL) {
        //Creates a new Json Request with volley, handles it and appends it.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    mUserInfoDisp.setText("");
                    mUserDietaryDisp.setText("");
                    JSONArray jsonArray = response.getJSONArray("users"); //Retrieves data from employees section of json
                    int i = new Random(System.currentTimeMillis()).nextInt(jsonArray.length());
                    JSONObject user = jsonArray.getJSONObject(i); //Each individual user in the json file
                    //Pulling data from json to variables
                    String firstName = user.getString("firstname"); //pull data out to a string
                    int age = user.getInt("age");
                    mUserInfoDisp.append("Age: " + age +".\n");
                    String mail = user.getString("mail");
                    mUserInfoDisp.append("Email: " +mail + ", \n");
                    String dietary = user.getString("dietary");
                    mUserDietaryDisp.append(dietary + "\n\n\n\n\n");
                    String fact = user.getString("fact");
                    mUserInfoDisp.append("My Fun Fact:" +fact + "\n");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        r.add(request); //Actually processes request
    }


}
