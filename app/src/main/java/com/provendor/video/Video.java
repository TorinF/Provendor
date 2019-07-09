package com.provendor.video;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.PropertyName;

import java.util.Calendar;

public class Video implements Parcelable {
    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Creator<Video> CREATOR = new Creator<Video>() {
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        public Video[] newArray(int size) {
            return new Video[size];
        }
    };
    private String name;
    private String imageUrl;
    private String videoUrl;
    private String previewUrl;
    private String user;
    private String comment;
    private int likes;
    private int dislikes;
    private String date;
    private long time;
    private int views;
    private String category;
    private String currency;
    private int cost;
    private int length;

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Video(Parcel in) {
        views = in.readInt();
        dislikes = in.readInt();
        likes = in.readInt();
        length = in.readInt();
        time = in.readLong();
        name = in.readString();
        cost = in.readInt();
        imageUrl = in.readString();
        previewUrl = in.readString();
        videoUrl = in.readString();
        category = in.readString();
        currency = in.readString();
        date = in.readString();
        user = in.readString();
    }

    public Video() {
        name = "";
        date = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        likes = 0;
        dislikes = 0;
        views = 0;
        time = System.currentTimeMillis();
        comment = "";
        //empty constructor needed
    }

    public Video(String namey, String imageUrly, String videoUrly, String Usery, String commenty, int likesy, int dislikesy, int viewsy, String Currrency, String categorry, int lengthy, int costy, String previewy) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        name = namey;
        imageUrl = imageUrly;
        videoUrl = videoUrly;
        user = Usery;
        comment = commenty;
        likes = likesy;
        dislikes = dislikesy;
        date = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        views = viewsy;
        time = System.currentTimeMillis();
        length = lengthy;
        cost = costy;
        currency = Currrency;
        category = categorry;
        previewUrl = previewy;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(imageUrl);
        out.writeString(videoUrl);
        out.writeString(previewUrl);
        out.writeString(user);
        out.writeString(date);
        out.writeString(category);
        out.writeString(currency);
        out.writeString(comment);
        out.writeInt(likes);

        out.writeInt(length);
        out.writeInt(dislikes);
        out.writeInt(views);
        out.writeInt(cost);

        out.writeLong(time);
    }

    @PropertyName("time")
    public long getTime1() {
        return time;
    }

    public void setTime1(long timey) {
        time = timey;
    }

    @PropertyName("currency")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currencyy) {
        currency = currencyy;
    }

    @PropertyName("category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String diseasey) {
        category = diseasey;
    }

    @PropertyName("cost")
    public int getCost() {
        return cost;
    }

    public void setCost(int timey) {
        cost = timey;
    }

    @PropertyName("length")
    public int getLength() {
        return length;
    }

    public void setLength(int timey) {
        length = timey;
    }

    @PropertyName("name")
    public String getName() {
        return name;
    }

    public void setName(String namey) {
        name = namey;
    }

    @PropertyName("comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String commenty) {
        comment = commenty;
    }

    @PropertyName("videoUrl")
    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String imageUrly) {
        videoUrl = imageUrly;
    }

    @PropertyName("previewUrl")
    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String diseasey) {
        previewUrl = diseasey;
    }

    @PropertyName("likes")
    public int getLikes() {
        return likes;
    }

    public void setLikes(int likesy) {
        likes = likesy;
    }

    @PropertyName("dislikes")
    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikesy) {
        dislikes = dislikesy;
    }

    @PropertyName("views")
    public int getViews() {
        return views;
    }

    public void setViews(int viewsky) {
        views = viewsky;
    }

    @PropertyName("date")
    public String getDate() {
        return date;
    }

    @PropertyName("user")
    public String getUser() {
        return user;
    }

    public void setUser(String diseasey) {
        user = diseasey;
    }

    @PropertyName("imageUrl")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrly) {
        imageUrl = imageUrly;
    }

    public void setdate() {
        date = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
    }

    public void incrementViews() {
        views++;
    }

    public void incrementLikes() {
        likes++;
    }

    public void incrementDislikes() {
        dislikes++;
    }
}
