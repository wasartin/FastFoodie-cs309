package edu.iastate.graysonc.fastfood.api;

import java.util.List;

import edu.iastate.graysonc.fastfood.database.entities.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Webservice {
    /**
     * @GET declares an HTTP GET request
     * @Path("user_email") annotation on the userId parameter marks it as a
     * replacement for the {user_email} placeholder in the @GET path
     */
    @GET("users/old/{user_email}")
    Call<User> getUser(@Path("user_email") String email);

    @GET("users/all")
    Call<List<User>> getAllUsers();

    @POST("users/create")
    Call<User> createUser(@Body User user);

    @DELETE("users/delete/{user_email}")
    Call<User> deleteUser(@Path("user_email") String email);

    @PUT("users/edit/{user_email}")
    Call<User> editUser(@Path("user_email") String email);
}
