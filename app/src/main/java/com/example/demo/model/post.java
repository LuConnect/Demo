package com.example.demo.model;


import java.util.Date;

public class post {

    private String image, user, caption;
    private Date time;

    post(){

    }

    public post(String image, String user, String caption, Date time) {
        this.image = image;
        this.user = user;
        this.caption = caption;
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
