package com.Provendor.Provendor;


import com.google.firebase.firestore.PropertyName;

import java.util.Calendar;


public class Invitation {
    private String sender;
    private String recipient;
    private String time;

    public Invitation() {
        sender = "";
        recipient = "";
        time = "";
    }

    public Invitation(String sender, String recipient) {
        this.sender = sender;
        this.recipient = recipient;
        this.time = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
    }

    //Getters
    @PropertyName("sender")
    public String getSender() {return sender;}
    @PropertyName("recipient")
    public String getRecipient() {return recipient;}
    @PropertyName("time")
    public String getTime() {return time;}

    // Setters
    public void setSender(String sender) {this.sender = sender;}
    public void setRecipient(String recipient) {this.recipient = recipient;}
    public void setTime(String time) {this.time = time;}
}
