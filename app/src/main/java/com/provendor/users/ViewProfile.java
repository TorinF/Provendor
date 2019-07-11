package com.provendor.users;

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
import com.provendor.R;

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

        String viewerID = currentViewer.getUid();
        String viewedUserID = viewedProfile.getUser();
        final DocumentReference relation = db.collection("userdata").document(viewerID).collection("relations").document(viewedUserID);
        //Change the friend button depending on friend status
        {

            //if there is friend request, displays pending


            relation.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        int fri = document.get("isfriend", Integer.class).intValue();
                        if (fri == PersonRelations.PENDING) {
                            friendBut.setText("Pending");
                            //Grey background
                            friendBut.setBackgroundColor(Color.parseColor("#cccccc"));
                            //Can't send more requests
                            friendBut.setClickable(false);
                        }
                        if (fri == PersonRelations.FRIENDED) {
                            friendBut.setText("Friends");
                            //Grey background
                            friendBut.setBackgroundColor(Color.parseColor("#cccccc"));
                            //Can't send more requests
                            friendBut.setClickable(false);
                        }

                    }
                }
            });


        }


        //Check if Profile is the user himself
        {

            String viewer = currentViewer.getUid();
            String viewedUser = viewedProfile.getUser();

            if (viewer.equals(viewedUser)) {
                friendBut.setClickable(false);
                //Grey color
                friendBut.setBackgroundColor(Color.parseColor("#cccccc"));
                friendBut.setText("Cannot Friend");
            }
        }

        friendBut.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        final String sender = currentViewer.getUid();
                        final String recipient = viewedProfile.getUser();
                        Invitation invite = new Invitation(sender, recipient);


                        //Extra code to check if a relation exists before setting pending status
                        relation.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    relation.update("isfriend", PersonRelations.PENDING);
                                } else {
                                    PersonRelations personRelations = new PersonRelations();
                                    personRelations.setUid(recipient);
                                    personRelations.setIsfriend(PersonRelations.PENDING);
                                    relation.set(personRelations);
                                }
                            }
                        });

                        //Add notification to reciever node
                        //TODO: Change notification object constructor param to relation
                        Notification invitenotification = new Notification(invite);
                        db.collection("userdata").document(recipient).collection("notifications").document("notifications").collection("notifications").add(invitenotification);


                        DocumentReference RecipientDocument = db.collection("userdata").document(recipient).collection("notifications").document("notifications");
                        RecipientDocument.update("unreadInbox", FieldValue.increment(1));


                        //No longer friendable
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