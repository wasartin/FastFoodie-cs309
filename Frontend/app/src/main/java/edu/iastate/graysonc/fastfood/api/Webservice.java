package edu.iastate.graysonc.fastfood.api;

import edu.iastate.graysonc.fastfood.database.entities.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Webservice {
    /**
     * @GET declares an HTTP GET request
     * @Path("user") annotation on the userId parameter marks it as a
     * replacement for the {user} placeholder in the @GET path
     */
    @GET("users/{email}")
    Call<User> getUser(@Path("email") String email);
}
