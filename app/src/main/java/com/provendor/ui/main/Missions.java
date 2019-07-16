package com.provendor.ui.main;



import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.provendor.R;
import com.provendor.users.ProfileClass;

public class Missions extends AppCompatActivity {
    private FirebaseAuth mAuth;

    //default missions
//    mission1 = "Get 3 friends!";
//    mission3 = "Answer 5 questions!";
//    mission2 = "Get 10 followers!";
//    mission4 = "Post 1 video of your plant!";
//    mission5 = "Have 100 Gold!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missions);

        //mission instruction text views
        final TextView mission1Text = findViewById(R.id.Mission1);
        final TextView mission2Text = findViewById(R.id.Mission2);
        final TextView mission3Text = findViewById(R.id.Mission3);
        final TextView mission4Text = findViewById(R.id.Mission4);
        final TextView mission5Text = findViewById(R.id.Mission5);

        //mission progress text views
        final TextView mission1Progress = findViewById(R.id.friendProgress);
        final TextView mission2Progress = findViewById(R.id.questionProgress);
        final TextView mission3Progress = findViewById(R.id.followerProgress);
        final TextView mission4Progress = findViewById(R.id.vidProgress);
        final TextView mission5Progress = findViewById(R.id.goldProgress);

        //mission progress bars
        final ProgressBar progressBar1 = findViewById(R.id.ProgressBar1);
        final ProgressBar progressBar2 = findViewById(R.id.ProgressBar2);
        final ProgressBar progressBar3 = findViewById(R.id.ProgressBar3);
        final ProgressBar progressBar4 = findViewById(R.id.ProgressBar4);
        final ProgressBar progressBar5 = findViewById(R.id.ProgressBar5);

        //accessing FireBase for user data
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
        {
            DocumentReference docRef = db.collection("userdata").document(currentUser.getUid());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    ProfileClass profile = documentSnapshot.toObject(ProfileClass.class);

                    int lvl = profile.getLevel();

                    //mission requirements
                    int friendReq = lvl * 3;
                    int questionReq = lvl * 5;
                    int followerReq = lvl * 10;
                    //int vidReq = lvl;
                    int goldReq = lvl * 100;


                    //set mission instructions
                    mission1Text.setText("Get " + friendReq + " friends!");
                    mission2Text.setText("Answer" + questionReq + "questions!");
                    mission3Text.setText("Get" + followerReq + "followers!");
                    if (lvl == 1)
                        mission4Text.setText("Post 1 video of your plant!");
                    else
                        mission4Text.setText("Post" + lvl + "videos of your plant!");
                    mission5Text.setText("Have" + goldReq + "Gold!");

                    //set mission progress text
                    mission1Progress.setText(profile.getFriends() + "/" + friendReq);
                    mission2Progress.setText(profile.getQuestions() + "/" + questionReq);
                    mission3Progress.setText(profile.getFollowers() + "/" + followerReq);
                    mission4Progress.setText(profile.getVids() + "/" + lvl);
                    mission5Progress.setText(profile.getGold() + "/" + goldReq);

                    //set progress bars for each mission
                    progressBar1.setProgress(profile.getFriends());
                    progressBar1.setMax(friendReq);

                    progressBar2.setProgress(profile.getQuestions());
                    progressBar2.setMax(questionReq);

                    progressBar3.setProgress(profile.getFollowers());
                    progressBar3.setMax(followerReq);

                    progressBar4.setProgress(profile.getVids());
                    progressBar4.setMax(lvl);

                    progressBar5.setProgress(profile.getGold());
                    progressBar5.setMax(goldReq);
                }
            });
        }
        else
        {
            //send user to sign in screen
        }
    }
}
