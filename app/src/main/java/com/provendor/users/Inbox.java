package com.provendor.users;

import com.google.firebase.firestore.PropertyName;


public class Inbox {
    private int unreadInbox;
    private int unreadMessages;

    public Inbox() {
        unreadInbox = 0;
        unreadMessages = 0;
    }

    public Inbox(int munreadInbox, int munreadMessages) {
        unreadInbox = munreadInbox;
        unreadInbox = munreadMessages;
    }

    public void checkedinbox() {
        unreadInbox = 0;
    }

    @PropertyName("unreadMessages")
    public int getUnreadMessages() {
        return unreadMessages;
    }

    public void setUnreadMessages(int unreadMessages) {
        this.unreadMessages = unreadMessages;
    }

    @PropertyName("unreadInbox")
    public int getUnreadInbox() {
        return unreadInbox;
    }

    // Setters
    public void setUnreadInbox(int unreadInbox) {
        this.unreadInbox = unreadInbox;
    }


}
