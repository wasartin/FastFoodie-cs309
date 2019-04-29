package edu.iastate.graysonc.fastfood.database.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Ticket {
    @SerializedName("ticket_id")
    private int ticketId;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("admin_id")
    private String adminId;

    @SerializedName("text")
    private String issue;

    @SerializedName("category")
    private String category;

    @SerializedName("date")
    private Date date;

    public Ticket(String email, String issue, String category){
        userId = email;
        this.issue = issue;
        this.category = category;
        ticketId = 0;
        adminId = null;
        date = null;
    }

    public String getIssue() {
        return issue;
    }

    public String getUserID() {
        return userId;
    }

    public String getAdminId(){return adminId;}

    public Date getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public int getTicketId(){return ticketId;}
}
