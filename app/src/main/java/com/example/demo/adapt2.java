package com.example.demo;

public class adapt2 {

    private String currentUserId,comment, name, image;

    public adapt2(String currentUserId, String comment, String name, String image) {
        this.currentUserId = currentUserId;
        this.comment = comment;
        this.name = name;
        this.image = image;
    }
    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
