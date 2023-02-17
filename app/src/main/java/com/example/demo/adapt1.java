package com.example.demo;

public class adapt1 {

    private String currentUserId,caption,Time;

    public adapt1(String currentUserId, String caption) {
        this.currentUserId = currentUserId;
        this.caption = caption;
        //this.Time = String.valueOf(time);
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