package com.example.demo;

public class adapt1 {

    private String currentUserId,caption;
    String time = String.valueOf(System.currentTimeMillis());


    public adapt1(String currentUserId, String caption, String time) {
        this.currentUserId = currentUserId;
        this.caption = caption;
        this.time = time;
    }

    public adapt1(String currentUserId, String caption) {
        this.currentUserId = currentUserId;
        this.caption = caption;

    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

//    public String getTime() {
//        return Time;
//    }
//
//    public void setTime(String time) {
//        Time = time;
//    }
}