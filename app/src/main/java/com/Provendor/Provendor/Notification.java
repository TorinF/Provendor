package com.Provendor.Provendor;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.firestore.PropertyName;

import java.util.Calendar;

public class Notification {
    private String message;
    private String useruid;
    private String type;
    private String date;
    public Notification(){
       message="";
       useruid="";
       type="";
       date="";
    }
    public Notification(String mMessage, String mUseruid, String mtype){
        message=mMessage;
        useruid=mUseruid;
        type=mtype;
       date = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
    }
    public Notification(Invitation friendrequest){
        message="Has requested to be friends";
        useruid=friendrequest.getRecipient();
        type="friendReq";
        date =friendrequest.getTime();
    }
    @PropertyName("message")
    public String getmessage() {return message;}
    @PropertyName("useruid")
    public String getuseruid() {return useruid;}
    @PropertyName("type")
    public String gettype() {return type;}
    @PropertyName("date")
    public String getdate() {return date;}


    // Setters
    public void setdate(String date) {this.date = date;}
    public void settype(String type) {this.type = type;}
    public void setUseruid(String useruid) {this.useruid = useruid;}
    public void setmessage(String message) {this.message = message;}

}
