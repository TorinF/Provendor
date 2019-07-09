package com.provendor.questionForum;

import com.google.firebase.firestore.PropertyName;

import java.util.Calendar;

public class QuestionsClass {
    private String question;
    private int karma;
    private String date;
    private boolean answered;
    private String user;

    public QuestionsClass() {
        question = "";
        karma = 0;
        date = date = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        answered = false;
        user = "";
    }

    public QuestionsClass(String q, int karmas, String datey, boolean ans, String userName) {
        question = q;
        karma = karmas;
        date = datey;
        answered = ans;
        user = userName;
    }

    @PropertyName("date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @PropertyName("answered")
    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    @PropertyName("karma")
    public int getKarma() {
        return karma;
    }

    public void setKarma(int karma) {
        this.karma = karma;
    }

    @PropertyName("question")
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @PropertyName("user")
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
