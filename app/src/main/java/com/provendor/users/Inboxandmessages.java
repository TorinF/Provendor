package com.provendor.users;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.provendor.R;
import com.provendor.TabAdapter;
import com.provendor.ui.main.PlaceholderFragment;
import com.provendor.ui.main.SectionsPagerAdapter;

public class Inboxandmessages extends AppCompatActivity {
    Inbox currentInbox;
    String username;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inboxandmessages);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        username = user.getUid();
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("userdata").document(username).collection("notifications").document("notifications");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                currentInbox = documentSnapshot.toObject(Inbox.class);
                currentInbox.checkedinbox();
                db.collection("userdata").document(username).collection("notifications").document("notifications").set(currentInbox);

            }
        });


        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new PlaceholderFragment(), "Inbox");
        adapter.addFragment(new PlaceholderFragment(), "Messages");

        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);


    }
}