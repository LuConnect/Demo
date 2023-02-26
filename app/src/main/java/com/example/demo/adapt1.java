package com.example.demo;

public class adapt1 {

    private String currentUserId,caption, name, image;
    String time = String.valueOf(System.currentTimeMillis());


    public adapt1(String currentUserId, String caption, String name, String image, String time) {
        this.currentUserId = currentUserId;
        this.caption = caption;
        this.name = name;
        this.image = image;
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
}
