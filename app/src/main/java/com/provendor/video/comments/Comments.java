package com.provendor.video.comments;

import com.google.firebase.firestore.PropertyName;

public class Comments {
    private int likes;
    private int dislikes;
    private String message;
    private String date;
    private String username;
    private String userid;
    private String userprofileiamgeurl;

    public Comments() {

    }

    @PropertyName("likes")
    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @PropertyName("dislikes")
    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    @PropertyName("message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @PropertyName("date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @PropertyName("username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @PropertyName("userid")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @PropertyName("userprofileiamgeurl")
    public String getUserprofileiamgeurl() {
        return userprofileiamgeurl;
    }

    public void setUserprofileiamgeurl(String userprofileiamgeurl) {
        this.userprofileiamgeurl = userprofileiamgeurl;
    }
}
