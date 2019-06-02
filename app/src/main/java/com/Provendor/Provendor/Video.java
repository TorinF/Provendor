package com.Provendor.Provendor;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.PropertyName;

import java.util.Calendar;

public class Video implements Parcelable {
    private String name;
    private String imageUrl;
    private String videoUrl;
    private String previewUrl;
    private String user;
    private int likes;
    private int dislikes;
    private String date;
    private long time;
    private int views;
    private String category;
    private String currency;
    private int cost;
    private int length;
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
        out.writeInt(likes);

        out.writeInt(length);
        out.writeInt(dislikes);
        out.writeInt(views);
        out.writeInt(cost);

        out.writeLong(time);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Creator<Video> CREATOR = new Creator<Video>() {
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Video(Parcel in) {
        views = in.readInt();
        dislikes = in.readInt();
        likes = in.readInt();
        length = in.readInt();
        time  = in.readLong();
        name  = in.readString();
        cost= in.readInt();
        imageUrl  = in.readString();
        previewUrl  = in.readString();
        videoUrl = in.readString();
        category  = in.readString();
        currency = in.readString();
        date = in.readString();
        user= in.readString();
    }

    public Video() {
        name= "";
        date=java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        likes=0;
        dislikes=0;
        views=0;
        time=System.currentTimeMillis();
        //empty constructor needed
    }

    public Video(String namey, String imageUrly, String videoUrly, String Usery, int likesy, int dislikesy, int viewsy, String Currrency, String categorry, int lengthy, int costy, String previewy) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        name=namey;
        imageUrl=imageUrly;
        videoUrl=videoUrly;
        user=Usery;
        likes=likesy;
        dislikes=dislikesy;
        date=java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        views=viewsy;
        time=System.currentTimeMillis();
        length=lengthy;
        cost=costy;
        currency=Currrency;
        category=categorry;
    previewUrl=previewy;
    }
    @PropertyName("time")
    public long getTime1(){return time;}
    @PropertyName("currency")
    public String getCurrency() {
        return currency;
    }
    @PropertyName("category")
    public String getCategory() {
        return category;
    }
    @PropertyName("cost")
    public int getCost() {
        return cost;
    }

    @PropertyName("length")
    public int getLength() {
        return length;
    }

    @PropertyName("name")
    public String getName() {
        return name;
    }
    @PropertyName("videoUrl")
    public String getVideoUrl() {
        return videoUrl;
    }
    @PropertyName("previewUrl")
    public String getPreviewUrl() {
        return previewUrl;
    }
    @PropertyName("likes")
    public int getLikes() {
        return likes;
    }
    @PropertyName("dislikes")
    public int getDislikes() {
        return dislikes;
    }
    @PropertyName("views")
    public int getViews() {
        return views;
    }
    @PropertyName("date")
    public String getDate() {
        return date;
    }
    @PropertyName("user")
    public String getUser() {
        return user;
    }

        @PropertyName("imageUrl")
    public String getImageUrl() {
        return imageUrl;
    }
    public void setName(String namey) {
        name = namey;
    }
    public void setdate() {
        date=java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
    }
    public void setCost(int timey) {
        cost = timey;
    }
    public void setCurrency(String currencyy) {
        currency = currencyy;
    }
    public void setLength(int timey) {
        length = timey;
    }
    public void setCategory(String diseasey) {
        category = diseasey;
    }
    public void setPreviewUrl(String diseasey) {
        previewUrl = diseasey;
    }
    public void setTime1(long timey) {
        time = timey;
    }
    public void setUser(String diseasey) {
        user = diseasey;
    }


    public void setImageUrl(String imageUrly) {
        imageUrl = imageUrly;
    }
    public void setVideoUrl(String imageUrly) {
        videoUrl = imageUrly;
    }
    public void setLikes(int likesy) {
        likes = likesy;
    }
    public void setDislikes(int dislikesy) {
        dislikes = dislikesy;
    }
    public void setViews(int viewsky) {
        views = viewsky;
    }
    public void incrementViews(){views++;}
    public void incrementLikes(){likes++;}
    public void incrementDislikes(){dislikes++;}
}
