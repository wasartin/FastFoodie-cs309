package edu.iastate.graysonc.fastfood.database.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Food {
    @PrimaryKey
    @NonNull
    @SerializedName("food_id")
    private int id;

    @SerializedName("food_name")
    @Expose
    private String name;

    @SerializedName("protein_total")
    @Expose
    private int proteinTotal;

    @SerializedName("carb_total")
    @Expose
    private int carbTotal;

    @SerializedName("fat_total")
    @Expose
    private int fatTotal;

    @SerializedName("calorie_total")
    private int calorieTotal;

    @SerializedName("price")
    private String price;

    @SerializedName("category")
    private String category;

    @SerializedName("located_at")
    private int location;

    @SerializedName("food_rating")
    private double rating;

    private int isFavorite;

    private Date lastRefresh;

    public Food(@NonNull int id, String name, int proteinTotal, int carbTotal, int fatTotal, int calorieTotal, int location) {
        this.id = id;
        this.name = name;
        this.proteinTotal = proteinTotal;
        this.carbTotal = carbTotal;
        this.fatTotal = fatTotal;
        this.calorieTotal = calorieTotal;
        this.location = location;
        this.lastRefresh = getLastRefresh();
        isFavorite = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProteinTotal() {
        return proteinTotal;
    }

    public void setProteinTotal(int proteinTotal) {
        this.proteinTotal = proteinTotal;
    }

    public int getCarbTotal() {
        return carbTotal;
    }

    public int getFatTotal() {
        return fatTotal;
    }

    public void setFatTotal(int fatTotal) {
        this.fatTotal = fatTotal;
    }

    public int getCalorieTotal() {
        return calorieTotal;
    }

    public void setCalorieTotal(int calorieTotal) {
        this.calorieTotal = calorieTotal;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public Date getLastRefresh() {
        return lastRefresh;
    }

    public void setLastRefresh(Date lastRefresh) {
        this.lastRefresh = lastRefresh;
    }

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int favorite) {
        isFavorite = favorite;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}