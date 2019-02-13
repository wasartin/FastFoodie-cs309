package android.example.usingvolley;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView mUserNameDisp;
    private TextView mUserInfoDisp;
    private TextView mUserDietaryDisp;
    Button but;
    RequestQueue r;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserNameDisp = (TextView) findViewById(R.id.user_name_display);
        mUserInfoDisp = (TextView) findViewById(R.id.user_info_display);
        mUserDietaryDisp = (TextView) findViewById(R.id.user_dietary_display);//Refers to the textbox in activity main
        but=findViewById(R.id.button); //Points to the button
        r = Volley.newRequestQueue(this); //Volley class
        but.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                jsonParse();
            }
        }); //When button clicked call json parse
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Creates a menu, note main refers to a menu created in the menus resource folder
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //When menu selected do this
        int itemThatWasClickedId = item.getItemId(); //Get clicked button
        if (itemThatWasClickedId == R.id.action_ticket) { //Refers to a menu item in the 'main' menu file
            Context context = MainActivity.this;
            String textToShow = "Open Submit Ticket Page";
            Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();
            // Creates a popup in the context of main page,
            // with the string set, and a short length
            return true;
        }

        //All this is the same but for the other page/button
        if (itemThatWasClickedId == R.id.action_edit) {
            Context context = MainActivity.this;
            String textToShow = "Open Edit User Page";
            Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void jsonParse(){
        String url = "https://api.myjson.com/bins/ft6se"; //We would change this to our API
        //Creates a new Json Request with volley, handles it and appends it.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    mUserInfoDisp.setText("");
                    mUserNameDisp.setText("");
                    mUserDietaryDisp.setText("");
                    JSONArray  jsonArray = response.getJSONArray("users"); //Retrieves data from employees section of json
                    int i = new Random(System.currentTimeMillis()).nextInt(jsonArray.length());
                    JSONObject user = jsonArray.getJSONObject(i); //Each individual user in the json file
                    //Pulling data from json to variables
                    String firstName = user.getString("firstname"); //pull data out to a string
                    mUserNameDisp.append(firstName+", "); //add to the correct box
                    int age = user.getInt("age");
                    mUserInfoDisp.append(""+age+", ");
                    String mail = user.getString("mail");
                    mUserInfoDisp.append(mail+", \n");
                    String dietary = user.getString("dietary");
                    mUserDietaryDisp.append(dietary + "\n\n\n\n\n");
                    String fact = user.getString("fact");
                    mUserInfoDisp.append(fact+"\n");
                    mUserDietaryDisp.append("\n JSON Provided by https://api.myjson.com/bins/biv8e");   //citing my sources
                } catch (JSONException e) { //incase of error
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
