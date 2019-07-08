package com.Provendor.Provendor;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.PropertyName;

//path=videos/videos/useridtimeupload/relations/userid
public class VideoRelations {
    private String uid;
    private boolean issubscribed;
    private boolean isliked;
    private boolean isdisliked;

    public VideoRelations() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        issubscribed = false;
        isliked = false;
        isdisliked = false;
    }

    @PropertyName("issubscribed")
    public boolean getIssubscribed() {
        return issubscribed;
    }

    public void setIssubscribed(boolean issubscribed) {
        this.issubscribed = issubscribed;
    }

    @PropertyName("isliked")
    public boolean getIsliked() {
        return isliked;
    }

    public void setIsliked(boolean isliked) {
        this.isliked = isliked;
    }

    @PropertyName("isdisliked")
    public boolean getIsdisliked() {
        return isdisliked;
    }

    public void setIsdisliked(boolean isdisliked) {
        this.isdisliked = isdisliked;
    }

    @PropertyName("uid")
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
