package com.Provendor.Provendor;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ViewProfile extends AppCompatActivity {

    private Button friendBut;

    private FirebaseAuth mAuth;
    private FirebaseUser currentViewer;
    private ProfileClass viewedProfile;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        mAuth = FirebaseAuth.getInstance();
        currentViewer = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        viewedProfile = ProfileList.currentUpload;

        TextView userID = findViewById(R.id.UserID1);
        userID.setText(viewedProfile.getUser());


        //Friend Request button and actions
        friendBut = findViewById(R.id.friendbutton);

        //Change the button if Friend request is pending
        {
            String viewer = currentViewer.getUid();
            String viewedUser = viewedProfile.getUser();

            DocumentReference invite = db.collection("userdata").document(viewer).collection("requests").document(viewedUser);

            //if there is friend request, displays pending
            invite.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        friendBut.setText("Pending");
                        //Grey background
                        friendBut.setBackgroundColor(Color.parseColor("#cccccc"));
                        //Can't send more requests
                        friendBut.setClickable(false);
                    }
                }
            });
        }

        friendBut.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        String sender = currentViewer.getUid();
                        String recipient = viewedProfile.getUser();
                        Invitation invite = new Invitation(sender, recipient);

                        //Add invite to sender's and reciever's nodes
                        db.collection("userdata").document(sender).collection("requests").document(recipient).set(invite);
                        db.collection("userdata").document(recipient).collection("pending").document(sender).set(invite);
                        //Add notification to reciever node
                        Notification invitenotification=new Notification(invite);
                        db.collection("userdata").document(recipient).collection("notifications").document("notifications").collection("notifications").add(invitenotification);

                        DocumentReference storyRef = db.collection("stories").document("hello-world");


                        DocumentReference RecipientDocument =     db.collection("userdata").document(recipient).collection("notifications").document("notifications");
                        RecipientDocument.update("unreadInbox", FieldValue.increment(1));


                        friendBut.setText("Pending");

                        //Grey background
                        friendBut.setBackgroundColor(Color.parseColor("#cccccc"));
                        //Can't send more requests
                        friendBut.setClickable(false);
                    }
                }
        );
    }

}