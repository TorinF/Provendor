package com.Provendor.Provendor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class Profile extends AppCompatActivity {
    private String uid;
    private ProfileClass owner;
    private TextView Username;
    private TextView UserID;
    private TextView Vids;
    private TextView Ques;
    private TextView Friends;
    private TextView Followers;
    private FirebaseFirestore db;
    private Button changeProfilePic;
    private int GALLERY = 1, CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Username = (TextView) findViewById(R.id.UsernameText);
        uid = user.getUid();
        UserID = (TextView) findViewById(R.id.UserID1);
        Vids = (TextView) findViewById(R.id.vids);
        Ques = (TextView) findViewById(R.id.qs);
        Friends = (TextView) findViewById(R.id.friends);
        Followers = (TextView) findViewById(R.id.followers);

        DocumentReference docRef = db.collection("userData").document(uid);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                owner= documentSnapshot.toObject(ProfileClass.class);
            }
        });
        changeProfilePic = (Button) findViewById(R.id.button4);
        changeProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
        Username.setText(owner.getuserName());
        UserID.setText(owner.getuser());
        Vids.setText("Videos: " + owner.getvids());
        Ques.setText("Questions: " + owner.getquestions());
        Friends.setText("Friends: " + owner.getfriends());
        Followers.setText("Followers: " + owner.getfollowers());

    }
    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select video from gallery",
                "Record video from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                chooseVideoFromGallary();
                                break;
                            case 1:
                                takeVideoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void chooseVideoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takeVideoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }
}
