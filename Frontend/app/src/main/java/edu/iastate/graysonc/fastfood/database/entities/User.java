package edu.iastate.graysonc.fastfood.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

@Entity
public class User {
    @PrimaryKey
    @NonNull
    @SerializedName("user_email")
    @Expose
    private String email;

    @SerializedName("user_type")
    @Expose
    private String type;

    private Date lastRefresh;

    public User(@NonNull String email, String type, Date lastRefresh) {
        this.email = email;
        this.type = type;
        this.lastRefresh = lastRefresh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getLastRefresh() {
        return lastRefresh;
    }

    public void setLastRefresh(Date lastRefresh) {
        this.lastRefresh = lastRefresh;
    }
}
