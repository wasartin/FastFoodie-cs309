package edu.iastate.graysonc.fastfood.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class User {
    @PrimaryKey
    @NonNull
    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("userType")
    @Expose
    private String type;

    public User() { }

    public User(@NonNull String email, String type) {
        this.email = email;
        this.type = type;
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
}
