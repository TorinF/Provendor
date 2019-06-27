package com.Provendor.Provendor;

import com.google.firebase.firestore.PropertyName;

public class Invitation {
    private String sender;
    private String recipient;
    private String time;

    public Invitation() {
        sender = "";
        recipient = "";
        time = "";
    }

    public Invitation(String sender, String recipient, String time) {
        this.sender = sender;
        this.recipient = recipient;
        this.time = time;
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
