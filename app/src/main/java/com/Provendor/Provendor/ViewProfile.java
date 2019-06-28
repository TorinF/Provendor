package com.Provendor.Provendor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ViewProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        ProfileClass thisprofile=ProfileList.currentUpload;
        TextView userID = (TextView) findViewById(R.id.UserID1);
        userID.setText(thisprofile.getUser());

    }


}
