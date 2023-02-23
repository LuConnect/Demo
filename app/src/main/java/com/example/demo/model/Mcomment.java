package com.example.demo.model;

public class Mcomment {
    String comments,time=String.valueOf(System.currentTimeMillis());

    public Mcomment() {
    }

    public Mcomment(String comments, String time) {
        this.comments = comments;
        this.time = time;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
