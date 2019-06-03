package com.Provendor.Provendor;

import com.google.firebase.firestore.PropertyName;

public class ProfileClass {

    private String user;
    private int friends;
    private int followers;
    private String userName;
    private String profileImageUrl;
    private int vids;
    private int questions;

    public ProfileClass(){
        user = "";
        friends = 0;
        followers = 0;
        userName = "";
        profileImageUrl = null;
        vids = 0;
        questions = 0;
    }
    public ProfileClass(String ID, int friend, int follower, String Name,String Url, int videos, int qs){
        user = ID;
        friends = friend;
        followers = follower;
        userName  =  Name;
        profileImageUrl = Url;
        vids = videos;
        questions = qs;
    }
    @PropertyName("user")
    public String getuser(){return user;}

    @PropertyName("userName")
    public String getuserName(){return userName;}

    @PropertyName("friends")
    public int getfriends(){return friends;}

    @PropertyName("followers")
    public int getfollowers(){return followers;}

    @PropertyName("profileImageUrl")
    public String getprofileImageUrl() {
        return profileImageUrl;
    }

    @PropertyName("vids")
    public int getvids(){
        return vids;
    }

    @PropertyName("questions")
    public int getquestions(){
        return questions;
    }

    public void setfollowers(int follower) {
        this.followers = follower;
    }

    public void setfriends(int friend) {
        this.friends = friend;
    }

    public void setuser(String ID) {
        user = ID;
    }

    public void setuserName(String user) {
        userName = user;
    }

    public void setprofileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setquestions(int questions) {
        this.questions = questions;
    }

    public void setvids(int vids) {
        this.vids = vids;
    }
}

