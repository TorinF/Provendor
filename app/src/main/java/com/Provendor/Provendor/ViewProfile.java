package com.Provendor.Provendor;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewProfile extends AppCompatActivity {

    private Button friendBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        final ProfileClass thisprofile = ProfileList.currentUpload;
        TextView userID = findViewById(R.id.UserID1);
        userID.setText(thisprofile.getUser());

        //Friend Request button and action
        friendBut = findViewById(R.id.friendBut);
        friendBut.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
//                        sender =;
//                        recipient =;
//                        Invitation invite = new Invitation(sender, recipient, time);
                    }
                }
        );


    }
}