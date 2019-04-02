package edu.iastate.graysonc.fastfood.database.entities;

import java.util.Date;

public class Ticket {
    private String issue;
    private String userID;
    private Date date;
    private String category;

    public Ticket(String email, String issue, String category){
        userID = email;
        this.issue = issue;
        this.category = category;
        date = new Date();
    }


    public String getIssue() {
        return issue;
    }

    public String getUserID() {
        return userID;
    }

    public Date getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }
}
