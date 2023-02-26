package com.example.demo.model;

public class Mcomment {
    String comments, currentuserid, name, image;

    public Mcomment() {
    }

    public Mcomment(String comments, String currentuserid, String name, String image) {
        this.comments = comments;
        this.currentuserid = currentuserid;
        this.name = name;
        this.image = image;
    }


    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCurrentuserid() {
        return currentuserid;
    }

    public void setCurrentuserid(String currentuserid) {
        this.currentuserid = currentuserid;
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
}
