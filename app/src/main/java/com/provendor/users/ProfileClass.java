package com.provendor.users;

import com.google.firebase.firestore.PropertyName;

import java.util.HashMap;

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
    private HashMap<String, Object> inventory;

    public ProfileClass() {
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
        inventory = new HashMap<>();
    }

    public ProfileClass(String ID, int friend, int follower, String Name, String Url, int videos, int qs, int xps, int lvl, int cash, HashMap<String,Object> items) {
        user = ID;
        friends = friend;
        followers = follower;
        userName = Name;
        profileImageUrl = Url;
        vids = videos;
        questions = qs;
        xp = xps;
        level = lvl;
        gold = cash;
        inventory = items;
    }

    @PropertyName("user")
    public String getUser() {
        return user;
    }

    public void setUser(String ID) {
        user = ID;
    }

    @PropertyName("userName")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String user) {
        userName = user;
    }

    @PropertyName("friends")
    public int getFriends() {
        return friends;
    }

    public void setFriends(int friend) {
        this.friends = friend;
    }

    @PropertyName("followers")
    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int follower) {
        this.followers = follower;
    }

    @PropertyName("profileImageUrl")
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    @PropertyName("vids")
    public int getVids() {
        return vids;
    }

    public void setVids(int vids) {
        this.vids = vids;
    }

    @PropertyName("questions")
    public int getQuestions() {
        return questions;
    }

    @PropertyName("level")
    public int getLevel() {
        return level;
    }

    @PropertyName("xp")
    public int getXp() {
        return xp;
    }

    @PropertyName("gold")
    public int getGold() {
        return gold;
    }

    @PropertyName("inventory")
    public HashMap<String,Object> getInventory ()
    {
        return inventory;
    }

    public void setQuestions(int questions) {
        this.questions = questions;
    }

    public void setXP(int xp) {
        this.xp = xp;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setlevel(int level) {
        this.level = level;
    }

    public void setInventory (HashMap<String,Object> items)
    {
        this.inventory = items;
    }

    public void addToInventory (String name, Object item)
    {
        this.inventory.put(name, item);
    }

    public void removeFromInventory (String name)
    {
        this.inventory.remove(name);
    }

    public Object getFromInventory (String name)
    {
        return inventory.get(name);
    }
}

