package com.provendor.ui.main;



import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.provendor.R;
import com.provendor.users.ProfileClass;

public class Missions extends AppCompatActivity {
    //default missions
//    mission1 = "Get 3 friends!";
//    mission3 = "Answer 5 questions!";
//    mission2 = "Get 10 followers!";
//    mission4 = "Post 1 video of your plant!";
//    mission5 = "Have 100 Gold!";

    private static int currentLevel;
    private static boolean isMission1Done;
    private static boolean isMission2Done;
    private static boolean isMission3Done;
    private static boolean isMission4Done;
    private static boolean isMission5Done;

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
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            DocumentReference docRef = db.collection("userdata").document(currentUser.getUid());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    ProfileClass profile = documentSnapshot.toObject(ProfileClass.class);

                    int lvl = 0;
                    int friends = 0;
                    int questions = 0;
                    int followers = 0;
                    int vids = 0;
                    int gold = 0;

                    try {
                        lvl = profile.getLevel();
                        friends = profile.getFriends();
                        questions = profile.getQuestions();
                        followers = profile.getFollowers();
                        vids = profile.getVids();
                        gold = profile.getGold();
                    }
                    catch (NullPointerException nullptr) {
                        Toast.makeText(Missions.this, "Error: Null value detected.",
                                Toast.LENGTH_LONG).show();
                    }

                    //check if lvl changed
                    if (lvl != currentLevel)
                    {
                        currentLevel = lvl;
                        isMission1Done = false;
                        isMission2Done = false;
                        isMission3Done = false;
                        isMission4Done = false;
                        isMission5Done = false;
                    }

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

                    //set progress bar maxes
                    progressBar1.setMax(friendReq);
                    progressBar2.setMax(questionReq);
                    progressBar3.setMax(followerReq);
                    progressBar4.setMax(lvl);
                    progressBar5.setMax(goldReq);

                    //default mission status is complete
                    mission1Progress.setTextColor(Color.GREEN);
                    mission2Progress.setTextColor(Color.GREEN);
                    mission3Progress.setTextColor(Color.GREEN);
                    mission4Progress.setTextColor(Color.GREEN);
                    mission5Progress.setTextColor(Color.GREEN);

                    mission1Progress.setText(friendReq + "/" + friendReq);
                    mission2Progress.setText(questionReq + "/" + questionReq);
                    mission3Progress.setText(followerReq + "/" + followerReq);
                    mission4Progress.setText(lvl + "/" + lvl);
                    mission5Progress.setText(goldReq + "/" + goldReq);

                    progressBar1.setProgress(friendReq);
                    progressBar2.setProgress(questionReq);
                    progressBar3.setProgress(followerReq);
                    progressBar4.setProgress(lvl);
                    progressBar5.setProgress(goldReq);


                    if (!isMission1Done) {
                        mission1Progress.setTextColor(Color.BLACK);
                        mission1Progress.setText(friends + "/" + friendReq);
                        progressBar1.setProgress(friends);

                        if (friendReq == friends) {
                            mission1Progress.setTextColor(Color.GREEN);
                            isMission1Done = true;
                            profile.setXP(profile.getXp() + 100);
                            profile.setGold(profile.getGold() + 50);
                            Toast.makeText(Missions.this, "Congrats, Mission Complete! You have received 100 XP and 50 gold.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    if (!isMission2Done)
                    {
                        mission2Progress.setTextColor(Color.BLACK);
                        mission2Progress.setText(questions + "/" + questionReq);
                        progressBar2.setProgress(questions);

                        if (questionReq == questions) {
                            mission2Progress.setTextColor(Color.GREEN);
                            isMission2Done = true;
                            profile.setXP(profile.getXp() + 100);
                            profile.setGold(profile.getGold() + 50);
                            Toast.makeText(Missions.this, "Congrats, Mission Complete! You have received 100 XP and 50 gold.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    if (!isMission3Done)
                    {
                        mission3Progress.setTextColor(Color.BLACK);
                        mission3Progress.setText(followers + "/" + followerReq);
                        progressBar3.setProgress(followers);

                        if (followerReq == followers) {
                            mission3Progress.setTextColor(Color.GREEN);
                            isMission3Done = true;
                            profile.setXP(profile.getXp() + 100);
                            profile.setGold(profile.getGold() + 50);
                            Toast.makeText(Missions.this, "Congrats, Mission Complete! You have received 100 XP and 50 gold.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    if (!isMission4Done)
                    {
                        mission4Progress.setTextColor(Color.BLACK);
                        mission4Progress.setText(vids + "/" + lvl);
                        progressBar4.setProgress(vids);

                        if (lvl == vids) {
                            mission4Progress.setTextColor(Color.GREEN);
                            isMission4Done = true;
                            profile.setXP(profile.getXp() + 100);
                            profile.setGold(profile.getGold() + 50);
                            Toast.makeText(Missions.this, "Congrats, Mission Complete! You have received 100 XP and 50 gold.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    if (!isMission5Done)
                    {
                        mission5Progress.setTextColor(Color.BLACK);
                        mission5Progress.setText(gold + "/" + goldReq);
                        progressBar5.setProgress(gold);

                        if (goldReq == gold) {
                            mission5Progress.setTextColor(Color.GREEN);
                            isMission5Done = true;
                            profile.setXP(profile.getXp() + 100);
                            profile.setGold(profile.getGold() + 50);
                            Toast.makeText(Missions.this, "Congrats, Mission Complete! You have received 100 XP and 50 gold.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            });
        } else {
            //send user to sign in screen
        }
    }
}
