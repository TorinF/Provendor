package com.provendor.questionForum;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.provendor.R;

public class SetQuestion extends AppCompatActivity {

    private QuestionsClass ques;
    private Button submit;
    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_question);
        submit = findViewById(R.id.pushQ);
        text = findViewById(R.id.qText);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ques.setQuestion(text.toString());
                ques.setAnswered(false);
                ques.setKarma(0);
                ques.setUser(user.getUid());
            }
        });
    }
}
