package com.provendor.users;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.PropertyName;

//path = userdata/useridofperson1/relations/useridofpersonrelatedtoperson1
public class PersonRelations {
    private int isfriend;//0 if false, 1 if pending, 2 if yes
    private boolean issubscribed;
    private boolean isblocked;
    private String uid;
    private String username;
    private String userimageurl;

    public PersonRelations() {
        isfriend = 0;
        issubscribed = false;
        isblocked = false;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

    }


    @PropertyName("isfriend")
    public int getIsfriend() {
        return isfriend;
    }

    public void setIsfriend(int isfriend) {
        this.isfriend = isfriend;
    }

    @PropertyName("issubscribed")
    public boolean getIssubscribed() {
        return issubscribed;
    }

    public void setIssubscribed(boolean issubscribed) {
        this.issubscribed = issubscribed;
    }

    @PropertyName("isblocked")
    public boolean getIsblocked() {
        return isblocked;
    }

    public void setIsblocked(boolean isblocked) {
        this.isblocked = isblocked;
    }

    @PropertyName("uid")
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @PropertyName("username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @PropertyName("userimageurl")
    public String getUserimageurl() {
        return userimageurl;
    }

    public void setUserimageurl(String userimageurl) {
        this.userimageurl = userimageurl;
    }
}
