package com.Provendor.Provendor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.Provendor.Provendor.tensorflow.LoginActivity2;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Changename extends AppCompatActivity {
    private EditText email;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText password;
    private String time;
    private FirebaseAuth mAuth;
    public static Video currentVideo;
    private FirebaseUser currentUser;

    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changename);
        email = (EditText)findViewById(R.id.login_email_input);
        Bitmap bitmap2 = ThumbnailUtils.createVideoThumbnail( UploadVideo.contentURI.getPath() , MediaStore.Images.Thumbnails.MINI_KIND );

        password = (EditText)findViewById(R.id.login_password_input);

        mAuth = FirebaseAuth.getInstance();
        button= (Button)findViewById(R.id.login);
        button.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                if (v == button){

                    UploadVideo();

                }

            }

        });
    }
    private void UploadVideo(){
        UploadVideo.UploadedVideo.setName(email.getText().toString().trim());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        time = "" + System.currentTimeMillis();
        UploadVideo.UploadedVideo.setUser(uid);
        UploadVideo.UploadedVideo.setCategory(password.getText().toString().trim());
        db.collection("videos").document(time).set(UploadVideo.UploadedVideo);
        currentVideo=UploadVideo.UploadedVideo;
        startActivity(new Intent(Changename.this, VideoView.class));
    }
}
