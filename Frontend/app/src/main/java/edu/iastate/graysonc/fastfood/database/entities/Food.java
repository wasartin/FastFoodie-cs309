package edu.iastate.graysonc.fastfood.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity
public class Food {
    @PrimaryKey
    @NonNull
    @SerializedName("food_id")
    @Expose
    private int food_id;

    @SerializedName("food_name")
    @Expose
    private String food_name;

    @SerializedName("protein_total")
    @Expose
    private int protein_total;

    @SerializedName("carb_total")
    @Expose
    private int carb_total;

    @SerializedName("fat_total")
    @Expose
    private int fat_total;

    @SerializedName("calorie_total")
    private int calorie_total;

    @SerializedName("located_at")
    private int located_at;

    private Date lastRefresh;

    public Food(@NonNull int food_id, String food_name, int protein_total, int carb_total, int fat_total, int calorie_total, int located_at) {
        this.food_id = food_id;
        this.food_name = food_name;
        this.protein_total = protein_total;
        this.carb_total = carb_total;
        this.fat_total = fat_total;
        this.calorie_total = calorie_total;
        this.located_at = located_at;
    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public int getProtein_total() {
        return protein_total;
    }

    public void setProtein_total(int protein_total) {
        this.protein_total = protein_total;
    }

    public int getCarb_total() {
        return carb_total;
    }

    public void setCarb_total(int carb_total) {
        this.carb_total = carb_total;
    }

    public int getFat_total() {
        return fat_total;
    }

    public void setFat_total(int fat_total) {
        this.fat_total = fat_total;
    }

    public int getCalorie_total() {
        return calorie_total;
    }

    public void setCalorie_total(int calorie_total) {
        this.calorie_total = calorie_total;
    }

    public int getLocated_at() {
        return located_at;
    }

    public void setLocated_at(int located_at) {
        this.located_at = located_at;
    }

    public Date getLastRefresh() {
        return lastRefresh;
    }

    public void setLastRefresh(Date lastRefresh) {
        this.lastRefresh = lastRefresh;
    }
}