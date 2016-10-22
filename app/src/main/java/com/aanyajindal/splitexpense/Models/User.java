package com.aanyajindal.splitexpense.Models;

/**
 * Created by aanyajindal on 22/10/16.
 */

public class User {

    String name;
    String email;
    String contact;
    String dp;

    public User() {
    }

    public User(String name, String email, String contact, String dp) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.dp = dp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

}
