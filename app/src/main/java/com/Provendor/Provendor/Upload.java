package com.Provendor.Provendor;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.PropertyName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Calendar;

public class Upload implements Parcelable {
    private String name;
    private long time1;
    private String imageUrl;
    private String disease;
    private String date;
    private float confidence;
    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeFloat(confidence);
        out.writeString(name);
        out.writeString(imageUrl);
        out.writeString(disease);
        out.writeString(date);
        out.writeLong(time1);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Upload> CREATOR = new Parcelable.Creator<Upload>() {
        public Upload createFromParcel(Parcel in) {
            return new Upload(in);
        }

        public Upload[] newArray(int size) {
            return new Upload[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Upload(Parcel in) {
        confidence = in.readInt();
        name  = in.readString();
        imageUrl  = in.readString();
        disease = in.readString();
        date = in.readString();
        time1= in.readLong();
    }

    public Upload() {
        name= "";
        time1=System.currentTimeMillis();
        //empty constructor needed
    }

    public Upload(String namey, String imageUrly, String diseasey, float confidencey) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        disease=diseasey;
        date=java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        name = namey;
        imageUrl = imageUrly;
        confidence=confidencey;
        time1=System.currentTimeMillis();


    }
    @PropertyName("time1")
    public long getTime1(){return time1;}
    @PropertyName("name")
    public String getName() {
        return name;
    }
    @PropertyName("disease")
    public String getDisease() {
        return disease;
    }
    @PropertyName("confidence")
    public float getConfidence() {
        return confidence;
    }
    @PropertyName("date")
    public String getDate() {
        return date;
    }
    public void setName(String namey) {
        name = namey;
    }
    public void setdate() {
        date=java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
    }
    public void setTime1(long timey) {
        time1 = timey;
    }
    public void setDisease(String diseasey) {
        disease = diseasey;
    }
    public void setConfidence(float confidencey) {
        confidence = confidencey;
    }
    @PropertyName("imageUrl")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrly) {
        imageUrl = imageUrly;
    }
}
