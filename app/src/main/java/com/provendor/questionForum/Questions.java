package com.provendor.questionForum;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.provendor.R;

public class Questions extends AppCompatActivity {

    private TextView date;
    private TextView q;
    private TextView name;
    private TextView karma;
    private TextView answered;
    private Button karmaGive;
    private QuestionsClass test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        date = findViewById(R.id.Date);
        q = findViewById(R.id.Question);
        name = findViewById(R.id.UserName);
        karma = findViewById(R.id.Karma);
        answered = findViewById(R.id.Answered);
        karmaGive = findViewById(R.id.Karmagiver);
        test = new QuestionsClass();
        karmaGive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test.setKarma(test.getKarma() + 1);
            }
        });
        date.setText(test.getDate());
        q.setText(test.getDate());
        name.setText(test.getUser());
        karma.setText("Karma: " + test.getKarma());
        if (test.isAnswered()) {
            answered.setText("This question has been answered");
        } else {
            answered.setText("This question has not been answered");
        }

    }
}
