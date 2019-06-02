package com.Provendor.Provendor;

import com.google.firebase.firestore.PropertyName;

public class ProfileClass {

    private String UserID;
    private int friends;
    private int followers;
    private String UserName;
    private String profileImageUrl;
    private int vids;
    private int questions;

    public ProfileClass(){
        UserID = "";
        friends = 0;
        followers = 0;
        UserName = "";
        profileImageUrl = null;
        vids = 0;
        questions = 0;
    }
    public ProfileClass(String ID, int friend, int follower, String Name,String Url, int videos, int qs){
        UserID = ID;
        friends = friend;
        followers = follower;
        UserName  =  Name;
        profileImageUrl = Url;
        vids = videos;
        questions = qs;
    }
    @PropertyName("User")
    public String getUserID(){return UserID;}

    @PropertyName("UserName")
    public String getUserName(){return UserName;}

    @PropertyName("Friends")
    public int getFriends(){return friends;}

    @PropertyName("Followers")
    public int getFollowers(){return followers;}

    @PropertyName("ImageUrl")
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    @PropertyName("Videos")
    public int getVids(){
        return vids;
    }

    @PropertyName("Questions")
    public int getQuestions(){
        return questions;
    }

    public void setFollowers(int follower) {
        this.followers = follower;
    }

    public void setFriends(int friend) {
        this.friends = friend;
    }

    public void setUserID(String ID) {
        UserID = ID;
    }

    public void setUserName(String user) {
        UserName = user;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setQuestions(int questions) {
        this.questions = questions;
    }

    public void setVids(int vids) {
        this.vids = vids;
    }
}

