package com.example.demo.model;


import org.checkerframework.common.returnsreceiver.qual.This;

import java.util.Date;

public class post {

    private String caption, time=String.valueOf(System.currentTimeMillis());
    String currentUser, name, image;

    public post(){

    }

    public post(String caption, String time, String currentUser, String name, String image) {
        this.caption = caption;
        this.time = time;
        this.currentUser=currentUser;
        this.name=name;
        this.image=image;
    }

    public post(String caption) {

        this.caption = caption;
    }

    public post(String caption, String name, String image) {
        this.caption = caption;
        this.name=name;
        this.image=image;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
