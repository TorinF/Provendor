package com.Provendor.Provendor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Questions extends AppCompatActivity {

    private TextView date;
    private TextView q;
    private TextView name;
    private TextView Karma;
    private TextView Answered;
    private QuestionsClass test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        
    }
}
