package edu.iastate.graysonc.fastfood;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

@Singleton
public class Repository {
    // Instantiate the cache
    //Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

    // Set up the network to use HttpURLConnection as the HTTP client.
    //Network network = new BasicNetwork(new HurlStack());

    // Instantiate the RequestQueue with the cache and network.
    //RequestQueue requestQueue = new RequestQueue(cache, network);
    RequestQueue requestQueue;


    public Repository(Application application) {
        requestQueue = Volley.newRequestQueue(application);
    }

//    public LiveData<User> getUser(int userId) {
//        // This isn't an optimal implementation. We'll fix it later.
//        final MutableLiveData<User> data = new MutableLiveData<>();
//        webservice.getUser(userId).enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                data.setValue(response.body());
//            }
//
//            // Error case is left out for brevity.
//        });
//        return data;
//    }

    private void fetchUserData(final String UID) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "http://cs309-bs-1.misc.iastate.edu:8080/users/" + UID  , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    System.out.println(response.toString());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.toString().startsWith("com.android.volley.ParseError:")){
                    //createUser(UID);
                    try {
                        TimeUnit.SECONDS.sleep(1); //TODO Not this
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    fetchUserData(UID);
                }
                System.out.println(error.toString());
            }
        });
        requestQueue.add(request); //Actually processes request
    }

    private void addUser(String UID) {
        JSONObject js = new JSONObject();
        try {
            js.put("email", UID);
            js.put("userType", "registered");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(js.toString());
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, "http://cs309-bs-1.misc.iastate.edu:8080/users/create",js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        System.out.println(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        requestQueue.add(postRequest);
    }
}
