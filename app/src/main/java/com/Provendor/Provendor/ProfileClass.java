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
    private int xp;
    private int level;
    private int gold;

    public ProfileClass(){
        user = "";
        friends = 0;
        followers = 0;
        userName = "";
        profileImageUrl = null;
        vids = 0;
        questions = 0;
        xp = 0;
        level = 0;
        gold = 0;
    }
    public ProfileClass(String ID, int friend, int follower, String Name,String Url, int videos, int qs, int xps, int lvl, int cash){
        user = ID;
        friends = friend;
        followers = follower;
        userName  =  Name;
        profileImageUrl = Url;
        vids = videos;
        questions = qs;
        xp  = xps;
        level = lvl;
        gold = cash;
    }
    @PropertyName("user")
    public String getUser(){return user;}

    @PropertyName("userName")
    public String getUserName(){return userName;}

    @PropertyName("friends")
    public int getFriends(){return friends;}

    @PropertyName("followers")
    public int getFollowers(){return followers;}

    @PropertyName("profileImageUrl")
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    @PropertyName("vids")
    public int getVids(){
        return vids;
    }

    @PropertyName("questions")
    public int getQuestions(){
        return questions;
    }

    @PropertyName("level")
    public int getLevel(){
        return level;
    }

    @PropertyName("xp")
    public int getXp(){
        return xp;
    }
    @PropertyName("gold")
    public int getGold() {
        return gold;
    }

    public void setFollowers(int follower) {
        this.followers = follower;
    }

    public void setFriends(int friend) {
        this.friends = friend;
    }

    public void setUser(String ID) {
        user = ID;
    }

    public void setUserName(String user) {
        userName = user;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setVuestions(int questions) {
        this.questions = questions;
    }

    public void setVids(int vids) {
        this.vids = vids;
    }

    public void setxp(int xp) {
        this.xp = xp;
    }

    public void setgold(int gold) {
        this.gold = gold;
    }

    public void setlevel(int level) {
        this.level =  level;
    }
}

