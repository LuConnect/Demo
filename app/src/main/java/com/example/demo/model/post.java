package com.example.demo.model;


import java.util.Date;

public class post {

    private String caption;

    public post(){

    }
    public post(String caption) {

        this.caption = caption;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
