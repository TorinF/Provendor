package com.Provendor.Provendor;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.PropertyName;

import java.io.Serializable;
import java.util.Calendar;

public class Upload implements Parcelable {
    private String name;
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

    }

    public Upload() {
        name= ""; //empty constructor needed
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
    }

    public String getName() {
        return name;
    }
    @PropertyName("disease")
    public String getDisease() {
        return disease;
    }
    public float getConfidence() {
        return confidence;
    }
    public String getDate() {
        return date;
    }
    public void setName(String namey) {
        name = namey;
    }
    public void setdate() {
        date=java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
    }
    public void setDisease(String diseasey) {
        disease = diseasey;
    }
    public void setConfidence(float confidencey) {
        confidence = confidencey;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrly) {
        imageUrl = imageUrly;
    }
}
