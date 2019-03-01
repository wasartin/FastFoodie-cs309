package edu.iastate.graysonc.fastfood.repositories;

import android.arch.lifecycle.LiveData;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.iastate.graysonc.fastfood.App;
import edu.iastate.graysonc.fastfood.api.Webservice;
import edu.iastate.graysonc.fastfood.database.dao.UserDAO;
import edu.iastate.graysonc.fastfood.database.entities.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class Repository {

    private static int FRESH_TIMEOUT_IN_MINUTES = 1;

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

    private void refreshUser(final String email) {
        executor.execute(() -> {
            // Check if user was fetched recently
            boolean userExists = (userDAO.hasUser(email, getMaxRefreshTime(new Date())) != null);
            // If user have to be updated
            if (!userExists) {
                webservice.getUser(email).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.e("TAG", "DATA REFRESHED FROM NETWORK");
                        Toast.makeText(App.context, "Data refreshed from network !", Toast.LENGTH_LONG).show();
                        executor.execute(() -> {
                            User user = response.body();
                            user.setLastRefresh(new Date());
                            userDAO.save(user);
                        });
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) { }
                });
            }
        });
    }

    private Date getMaxRefreshTime(Date currentDate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.MINUTE, -FRESH_TIMEOUT_IN_MINUTES);
        return cal.getTime();
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
