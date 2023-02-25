package com.example.demo;

public class userhelper {

    String name, pic, currentuser;

    public userhelper(String name,  String pic, String currentuser) {
        this.name = name;
        this.currentuser=currentuser;
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getCurrentuser() {
        return currentuser;
    }

    public void setCurrentuser(String currentuser) {
        this.currentuser = currentuser;
    }
}
