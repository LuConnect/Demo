package com.example.demo.model;

public class Mcomment {
    String comments, currentuserid;

    public Mcomment() {
    }

    public Mcomment(String comments, String currentuserid) {
        this.comments = comments;
        this.currentuserid = currentuserid;
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
}

