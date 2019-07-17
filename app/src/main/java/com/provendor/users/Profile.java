package com.provendor.users;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.UploadTask;
import com.provendor.R;
import com.provendor.utils.PicUtils;

public class Profile extends PicUtils {
    UploadTask mUploadTask;
    Button uploadbutton;
    private ProfileClass owner;
    private TextView username;
    private TextView userID;
    private Bitmap croppedImage;
    private TextView vids;
    private TextView ques;
    private TextView friends;
    private TextView followers;

    private int GALLERY = 1, CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser User = mAuth.getCurrentUser();
        uploadbutton = findViewById(R.id.button5);
        Button changeProfilePic = findViewById(R.id.button4);

        // if (owner!=null) {

        // }
        changeProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });


        uploadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(Profile.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });
    }






}
