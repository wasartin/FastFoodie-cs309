package edu.iastate.graysonc.fastfood.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/*@Entity(foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "email",
                        childColumns = "userEmail"),
                @ForeignKey(entity = Food.class,
                        parentColumns = "id",
                        childColumns = "foodId")
        })*/
@Entity
public class Favorite {
    @PrimaryKey
    @NonNull
    @SerializedName("favorites_id")
    @Expose
    private final int id;

    @SerializedName("user_id")
    private final String userEmail;

    @SerializedName("fid")
    private final int foodId;

    private Date lastRefresh;

    public Favorite(@NonNull int id, String userEmail, int foodId, Date lastRefresh) {
        this.userEmail = userEmail;
        this.foodId = foodId;
        this.id = id;
        this.lastRefresh = lastRefresh;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public int getFoodId() {
        return foodId;
    }

    public int getId() {
        return id;
    }

    public Date getLastRefresh() {
        return lastRefresh;
    }

    public void setLastRefresh(Date lastRefresh) {
        this.lastRefresh = lastRefresh;
    }
}
