package android.example.usingvolley;

import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.health.UidHealthStats;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private boolean toggled;
    private TextView mUserNameDisp;
    private TextView mUserInfoDisp;
    private TextView mUserDietaryDisp;
    private TextView mJsonDisp;
    private Button but;
    private Button mMenuTicket;
    private Button mMenuEdit;
    private ImageButton mMenuExpand;
    private RequestQueue r;
    private String base = "cs309-bs-1.misc.iastate.edu:8080/users/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Variables and point to correct XML objects
        toggled = false;
        mUserNameDisp = (TextView) findViewById(R.id.user_name_display);
        mUserInfoDisp = (TextView) findViewById(R.id.user_info_display);
        mUserDietaryDisp = (TextView) findViewById(R.id.user_dietary_display);
        mJsonDisp =(TextView) findViewById(R.id.DisplayJson);
        but = findViewById(R.id.button);
        r = Volley.newRequestQueue(this);
        mMenuTicket =(Button) findViewById(R.id.TicketButton);
        mMenuEdit = (Button) findViewById(R.id.ButtonEdit);
        mMenuExpand = (ImageButton) findViewById(R.id.MenuButton);


        //Create Click Listeners
        mMenuEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                createWarning("Open Edit Users Page");
            }
        });
        mMenuTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createWarning("Open Submit Ticket");
            }
        });
        mMenuExpand.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                toggleMenuVisible();
            }
        });
        but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                  //jsonParse("https://api.myjson.com/bins/ft6se"); //We should change this to our site
                    fetchUser("cowman@Springs.com");
                    //createUser("newunusedname@gmail.com");
            }
        }); //When button clicked call json parse



        //jsonParse("https://api.myjson.com/bins/ft6se"); //Fill table
    }

    /**
     * Toggles Visibility Of Buttons
     */
    public void toggleMenuVisible(){
        if(toggled){
            mMenuTicket.setVisibility(View.INVISIBLE);
            mMenuEdit.setVisibility(View.INVISIBLE);
            but.setVisibility(View.INVISIBLE);
        }else{
            mMenuTicket.setVisibility(View.VISIBLE);
            mMenuEdit.setVisibility(View.VISIBLE);
            but.setVisibility(View.VISIBLE);
        }
        toggled=!toggled;
    }

    /**
     * Creates A Toast Message With Content @message
     * @param message The message to be displayed
     */
    public void createWarning(String message){
        Context context = MainActivity.this;
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        //Creates a menu, note main refers to a menu created in the menus resource folder
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) { //When menu selected do this
//        int itemThatWasClickedId = item.getItemId(); //Get clicked button
//        if (itemThatWasClickedId == R.id.action_ticket) { //Refers to a menu item in the 'main' menu file
//            String textToShow = "Open Submit Ticket Page";
//            createWarning(textToShow);
//            // Creates a popup in the context of main page,
//            // with the string set, and a short length
//            return true;
//        }
//
//        //All this is the same but for the other page/button
//        if (itemThatWasClickedId == R.id.action_edit) {
//            String textToShow = "Open Edit User Page";
//            createWarning(textToShow);
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }


    /**
     * Fetches user data
     * @param UID The Unique Id to poll for
     */
    private void fetchUserData(String UID){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "http://cs309-bs-1.misc.iastate.edu:8080/users/" + UID  , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    mUserInfoDisp.setText("");
                    mUserNameDisp.setText("");
                    mUserDietaryDisp.setText("");
                    mJsonDisp.setText("");
                    mUserDietaryDisp.append(""+response.toString());
                } catch (Exception e) {
                    mUserDietaryDisp.append(e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mUserDietaryDisp.append(error.toString() + "\n");
            }
        });

        r.add(request); //Actually processes request
    }


    private void createUser(String UID) {
        createWarning("Made call");
        JSONObject js = new JSONObject();
        try {
            js.put("email", UID);
            js.put("userType", "registered");
        } catch (Exception e) {
            createWarning(e.getMessage());
        }
        createWarning("Made " + js.toString());

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, "http://cs309-bs-1.misc.iastate.edu:8080/users/create", js, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    mUserInfoDisp.setText(response.toString());
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    mUserDietaryDisp.setText(error.getMessage());
                }
            });
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
                    mUserNameDisp.setText("");
                    mUserDietaryDisp.setText("");
                    mJsonDisp.setText("");
                    JSONArray jsonArray = response.getJSONArray("users"); //Retrieves data from employees section of json
                    int i = new Random(System.currentTimeMillis()).nextInt(jsonArray.length());
                    JSONObject user = jsonArray.getJSONObject(i); //Each individual user in the json file
                    //Pulling data from json to variables
                    String firstName = user.getString("firstname"); //pull data out to a string
                    mUserNameDisp.append(firstName); //add to the correct box
                    int age = user.getInt("age");
                    mUserInfoDisp.append("Age: " + age +".\n");
                    String mail = user.getString("mail");
                    mUserInfoDisp.append("Email: " +mail + ", \n");
                    String dietary = user.getString("dietary");
                    mUserDietaryDisp.append(dietary + "\n\n\n\n\n");
                    String fact = user.getString("fact");
                    mUserInfoDisp.append("My Fun Fact:" +fact + "\n");
                    mJsonDisp.append("\n JSON Provided by https://api.myjson.com/bins/biv8e");   //citing my sources
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

        /*
        @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        */
        r.add(request); //Actually processes request
    }


}
