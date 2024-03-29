package com.provendor.users;

import android.content.res.Resources;
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
    private Resources res;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        mAuth = FirebaseAuth.getInstance();
        currentViewer = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        res = getResources();
        viewedProfile = ProfileList.currentUpload;

        TextView userID = findViewById(R.id.UserID1);
        userID.setText(viewedProfile.getUser());


        //Friend Request button and actions
        friendBut = findViewById(R.id.friendbutton);

        String viewerID = currentViewer.getUid();
        String viewedUserID = viewedProfile.getUser();
        final DocumentReference relationRef = db.collection("userdata").document(viewerID).collection("relations").document(viewedUserID);
        final Task<DocumentSnapshot> relationTask = relationRef.get();
        //Change the friend button depending on friend status
        {

            //if there is friend request, displays pending


            relationTask.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        PersonRelations relation = document.toObject(PersonRelations.class);
                        if (relation.getIsfriend() == PersonRelations.PENDING) {
                            friendBut.setText("Pending");
                            //Grey background
                            friendBut.setBackgroundColor(Color.parseColor("#cccccc"));
                            //Can't send more requests
                            friendBut.setClickable(false);
                        }
                        if (relation.getIsfriend() == PersonRelations.FRIENDED) {
                            //TODO: Add unfriend funtion
                            friendBut.setText("Unfriend");
                            //Grey background
                            friendBut.setBackgroundColor(res.getColor(R.color.button_unfriend));
                            friendBut.setClickable(true);
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
                friendBut.setBackgroundColor(res.getColor(R.color.button_friend));
                friendBut.setText("Cannot Friend");
            }
        }

        /*
         *
         */
        friendBut.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        final String sender = currentViewer.getUid();
                        final String recipient = viewedProfile.getUser();
                        final Invitation invite = new Invitation(sender, recipient);


                        //Extra code to check if a relation exists before setting pending status
                        relationTask.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot document = task.getResult();
                                PersonRelations relation = document.toObject(PersonRelations.class);
                                if (document.exists()) {
                                    int isFriend = relation.getIsfriend();
                                    if (isFriend == PersonRelations.NOTFRIENDS) {

                                        relationRef.update("isfriend", PersonRelations.PENDING);
                                        //Add notification to reciever node
                                        //TODO: Change notification object constructor param to relation
                                        Notification invitenotification = new Notification(invite);
                                        db.collection("userdata").document(recipient).collection("notifications").document("notifications").collection("notifications").add(invitenotification);


                                        DocumentReference RecipientDocument = db.collection("userdata").document(recipient).collection("notifications").document("notifications");
                                        RecipientDocument.update("unreadInbox", FieldValue.increment(1));


                                        //No longer friendable
                                        friendBut.setText("Pending");
                                        //Grey background
                                        friendBut.setBackgroundColor(res.getColor(R.color.button_pending));
                                    } else if (isFriend == PersonRelations.FRIENDED) {

                                        relationRef.update("isfriend", PersonRelations.NOTFRIENDS);
                                        //Edit sender's relation node for them
                                        //TODO:Determine if editing other users node is against database rules
                                        final DocumentReference friendRelationRef = db.collection("userdata").document(recipient).collection("relations").document(sender);
                                        final Task<DocumentSnapshot> friendRelation = relationRef.get();
                                        friendRelation.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                DocumentSnapshot document = task.getResult();
                                                if (document.exists()) {
                                                    friendRelationRef.update("isfriend", PersonRelations.NOTFRIENDS);
                                                }
                                            }
                                        });

                                        friendBut.setText("Friend");
                                        friendBut.setBackgroundColor(res.getColor(R.color.button_friend));
                                    }
                                } else {
                                    //Document doesn't exist (therefore will be friended)
                                    PersonRelations personRelations = new PersonRelations();
                                    personRelations.setUid(recipient);
                                    personRelations.setIsfriend(PersonRelations.PENDING);
                                    relationRef.set(personRelations);


                                }
                            }
                        });

                        //Add notification to reciever node
                        //TODO: Change notification object constructor param to relation
                        Notification invitenotification = new Notification(invite);
                        db.collection("userdata").document(recipient).collection("notifications").document("notifications").collection("notifications").add(invitenotification);


                        DocumentReference RecipientDocument = db.collection("userdata").document(recipient).collection("notifications").document("notifications");
                        RecipientDocument.update("unreadInbox", FieldValue.increment(1));


                        friendBut.setText("Pending");
                        friendBut.setBackgroundColor(res.getColor(R.color.colorAccent));
                        friendBut.setClickable(false);
                    }
                }
        );
    }

    public void setFriendButFriend() {
        friendBut.setText("Friend");
        friendBut.setBackgroundColor(res.getColor(R.color.button_friend));
    }

    public void setFriendButUnfriend() {
        friendBut.setText("Unfriend");
        friendBut.setBackgroundColor(res.getColor(R.color.button_unfriend));
    }

}