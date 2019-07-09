package com.provendor.video;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.provendor.R;

public class Changename extends AppCompatActivity {
    public static Video currentVideo;
    private EditText email;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText password;
    private String time;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changename);
        email = findViewById(R.id.login_email_input);
        Bitmap bitmap2 = ThumbnailUtils.createVideoThumbnail(UploadVideo.contentURI.getPath(), MediaStore.Images.Thumbnails.MINI_KIND);

        password = findViewById(R.id.login_password_input);

        mAuth = FirebaseAuth.getInstance();
        button = findViewById(R.id.login);
        button.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                if (v == button) {

                    UploadVideo();

                }

            }

        });
    }

    private void UploadVideo() {
        UploadVideo.UploadedVideo.setName(email.getText().toString().trim());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        long time2 = System.currentTimeMillis();
        time = "" + time2;
        UploadVideo.UploadedVideo.setUser(uid);
        UploadVideo.UploadedVideo.setTime1(time2);
        UploadVideo.UploadedVideo.setCategory(password.getText().toString().trim());
        db.collection("videos").document(uid + time).set(UploadVideo.UploadedVideo);
        currentVideo = UploadVideo.UploadedVideo;
        startActivity(new Intent(Changename.this, VideoView.class));

    }
}
