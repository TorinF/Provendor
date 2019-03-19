package com.Provendor.Provendor;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Calendar;

public class Upload implements Parcelable {
    private String mName;
    private String mImageUrl;
    private String mdisease;
    private String mdate;
    private float mconfidence;
    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeFloat(mconfidence);
        out.writeString(mName);
        out.writeString(mImageUrl);
        out.writeString(mdisease);
        out.writeString(mdate);
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
        mconfidence = in.readInt();
        mName  = in.readString();
        mImageUrl  = in.readString();
        mdisease = in.readString();
        mdate = in.readString();

    }

    public Upload() {
        mName= ""; //empty constructor needed
    }

    public Upload(String name, String imageUrl, String disease, float confidence) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        mdisease=disease;
        mdate=java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        mName = name;
        mImageUrl = imageUrl;
        mconfidence=confidence;
    }

    public String getName() {
        return mName;
    }
    public String getDisease() {
        return mdisease;
    }
    public float getConfidence() {
        return mconfidence;
    }
    public String getDate() {
        return mdate;
    }
    public void setName(String name) {
        mName = name;
    }
    public void setdate() {
        mdate=java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
    }
    public void setDisease(String disease) {
        mdisease = disease;
    }
    public void setConfidence(float confidence) {
        mconfidence = confidence;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}
