package com.provendor.users;

import com.google.firebase.firestore.PropertyName;

import java.util.Calendar;

///Your standard notification class. Notifications are located in userdata/user/notifications/notifications/notifications/examplenotification
public class Notification {
    private String message; //actual message of the notification
    private String useruid; //user who started/caused the notification
    private String type; //type of notification to help formatting- e.g. was it a comment? Was it a friend request? Was it a subscriber?
    private String date; //time of notification occuring

    public Notification() {
        message = "";
        useruid = "";
        type = "";
        date = "";
    }

    public Notification(String mMessage, String mUseruid, String mtype) {
        message = mMessage;
        useruid = mUseruid;
        type = mtype;
        date = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
    }

    public Notification(Invitation friendrequest) {
        message = "Has requested to be friends";
        useruid = friendrequest.getSender();
        type = "friendReq";
        date = friendrequest.getTime();
    }

    public Notification(String type, PersonRelations Personsrelated) {
        useruid = Personsrelated.getUid();
        date = Personsrelated.getTime();
        if (type.equals("request")) {
            message = Personsrelated.getUsername() + " has requested to be friends";
            type = "friendReq";
        }
        if (type.equals("subscribed")) {
            message = Personsrelated.getUsername() + " has subscribed to you";
            type = "subscribed";
        }
        if (type.equals("unfriended")) {
            message = Personsrelated.getUsername() + " has unfriended you";
            type = "unfriended";
        }
        if (type.equals("friends")) {
            message = Personsrelated.getUsername() + " has accepted your friend request";
            type = "friends";
        }


    }

    @PropertyName("message")
    public String getmessage() {
        return message;
    }

    @PropertyName("useruid")
    public String getuseruid() {
        return useruid;
    }

    @PropertyName("type")
    public String gettype() {
        return type;
    }

    @PropertyName("date")
    public String getdate() {
        return date;
    }


    // Setters
    public void setdate(String date) {
        this.date = date;
    }

    public void settype(String type) {
        this.type = type;
    }

    public void setUseruid(String useruid) {
        this.useruid = useruid;
    }

    public void setmessage(String message) {
        this.message = message;
    }

}
