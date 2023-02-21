package com.example.demo;

public class adapt2 {

    private String currentUserId,comment;

    public adapt2(String currentUserId, String comment) {
        this.currentUserId = currentUserId;
        this.comment = comment;
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
}
