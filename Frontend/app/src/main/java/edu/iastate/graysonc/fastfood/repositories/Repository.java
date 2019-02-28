package edu.iastate.graysonc.fastfood.repositories;

import android.arch.lifecycle.LiveData;

import java.io.IOException;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.iastate.graysonc.fastfood.api.Webservice;
import edu.iastate.graysonc.fastfood.database.dao.UserDAO;
import edu.iastate.graysonc.fastfood.database.entities.User;
import retrofit2.Response;

@Singleton
public class Repository {
    private final Webservice webservice;
    private final UserDAO userDAO;
    private final Executor executor;

    @Inject
    public Repository(Webservice webservice, UserDAO userDAO, Executor executor) {
        this.webservice = webservice;
        this.userDAO = userDAO;
        this.executor = executor;
    }

    public LiveData<User> getUser(String userId) {
        refreshUser(userId);
        // Returns a LiveData object directly from the database.
        return userDAO.load(userId);
    }

    private void refreshUser(final String userId) {
        // Runs in a background thread.
        executor.execute(() -> {
            // Check if user data was fetched recently.
            boolean userExists = (webservice.getUser(userId) != null);
            if (!userExists) {
                // Refreshes the data.
                Response<User> response = null;
                try {
                    response = webservice.getUser(userId).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Check for errors here.

                // Updates the database. The LiveData object automatically
                // refreshes, so we don't need to do anything else here.
                userDAO.save(response.body());
            }
        });
    }

    /*private void fetchUserData(final String UID) {
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
    }*/
}
