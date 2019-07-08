package com.Provendor.Provendor;

import com.google.firebase.firestore.PropertyName;

public class Answer {

    private String date;
    private String useruid;
    private String username;
    private String profileimageurl;
    private String text;
    private boolean isaccepted;
    private int likes;
    private int dislikes;

    public Answer() {

    }

    @PropertyName("date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @PropertyName("useruid")
    public String getUseruid() {
        return useruid;
    }

    public void setUseruid(String useruid) {
        this.useruid = useruid;
    }

    @PropertyName("username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @PropertyName("profileimageurl")
    public String getProfileimageurl() {
        return profileimageurl;
    }

    public void setProfileimageurl(String profileimageurl) {
        this.profileimageurl = profileimageurl;
    }

    @PropertyName("text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @PropertyName("isaccepted")
    public boolean getIsaccepted() {
        return isaccepted;
    }

    public void setIsaccepted(boolean isaccepted) {
        this.isaccepted = isaccepted;
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
}
