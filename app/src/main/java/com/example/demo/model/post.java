package com.example.demo.model;


import java.util.Date;

public class post {

    private String caption, time=String.valueOf(System.currentTimeMillis());
    String currentUser, name, Image;

    public post(){

    }

    public post(String caption, String time, String currentUser) {
        this.caption = caption;
        this.time = time;
        this.currentUser=currentUser;
    }

    public post(String caption) {

        this.caption = caption;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }
}
