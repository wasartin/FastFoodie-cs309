package edu.iastate.graysonc.fastfood.api;

import androidx.paging.DataSource;
import androidx.paging.PagedList;

import com.google.gson.JsonElement;

import java.util.List;

import edu.iastate.graysonc.fastfood.database.entities.Favorite;
import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.database.entities.ResultList;
import edu.iastate.graysonc.fastfood.database.entities.Ticket;
import edu.iastate.graysonc.fastfood.database.entities.User;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Webservice {

    @GET("users/{user_email}")
    Call<User> getUser(@Path("user_email") String userEmail);

    @GET("users/all")
    Call<List<User>> getAllUsers();

    @POST("users/create")
    Call<User> createUser(@Body User user);

    @DELETE("users/delete/{user_email}")
    Call<User> deleteUser(@Path("user_email") String userEmail);

    @PUT("users/edit/{user_email}")
    Call<User> editUser(@Path("user_email") String userEmail);

    @GET("foods/{food_id}")
    Call<Food> getFood(@Path("food_id") int foodId);

    @GET("foods/all")
    Call<DataSource.Factory<Integer, Food>> getAllFoods();

    @GET("api/favorites/{user_email}")
    Call<List<Food>> getFavoriteFoodsForUser(@Path("user_email") String userEmail);

    @GET("favorites/all") // Might be deleted soon
    Call<List<Favorite>> getAllFavorites();

    @POST("favorites/create/{user_email}/{food_id}")
    Call<String> createFavorite(@Path("user_email") String userEmail, @Path("food_id") int foodId);

    @DELETE("favorites/delete/{user_email}/{food_id}")
    Call<String> deleteFavorite(@Path("user_email") String userEmail, @Path("food_id") int foodId);

    @GET("foodRatings/average/{food_id}")
    Call<String> getAverageRating(@Path("food_id") int foodId);

    @POST("foodRatings/create/{user_email}/{food_id}/{rating}")
    Call<Object> submitRating(@Path("user_email") String userEmail, @Path("food_id") int foodId, @Path("rating") int rating);

    @PUT("foodRatings/edit/{user_email}/{food_id}/{rating}")
    Call<Object> editRating(@Path("user_email") String userEmail, @Path("food_id") int foodId, @Path("rating") int rating);

    @DELETE("foodRatings/delete/{user_email}/{food_id}")
    Call<Double> deleteRating(@Path("user_email") String userEmail, @Path("food_id") int foodId);

    @POST("tickets/create")
    Call<Ticket> submitTicket(@Body Ticket ticket);

    @GET("foods/search/keyword/{keyword}?size=300")
    Call<ResultList> doSearch(@Path("keyword") String query);
}
