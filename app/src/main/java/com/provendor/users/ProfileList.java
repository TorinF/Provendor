package com.provendor.users;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.provendor.MainActivity;
import com.provendor.R;
import com.provendor.video.Videolists;

public class ProfileList extends AppCompatActivity {

    public static ProfileClass currentUpload;
    private TextView notificationnumber;
    private TextView textView;
    private int notifications;
    private FirebaseFirestore mDatabaseRef;
    private Button button;
    private Query mChartsQuery;
    private DocumentReference docRef;
    private RecyclerView mRecycler;
    private ImageView imageView;
    private FirebaseAuth mAuth;
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirestoreRecyclerAdapter<ProfileClass, ProductViewHolder> adapter;
    private ViewFlipper VF;
    private Inbox currentInbox;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String useruid = currentUser.getUid();
        RecyclerView recyclerView = findViewById(R.id.profilelist);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(llm);
        playerView = findViewById(R.id.video_view);

        /*when query*/
        Query query = rootRef.collection("userdata")
                .orderBy("friends", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ProfileClass> options = new FirestoreRecyclerOptions.Builder<ProfileClass>()
                .setQuery(query, ProfileClass.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<ProfileClass, ProfileList.ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProfileList.ProductViewHolder holder, int position, @NonNull ProfileClass productModel) {
                holder.setProductName(productModel);

            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = getLayoutInflater();
                LinearLayout mainLayout = findViewById(R.id.linear);
                android.view.View myLayout = inflater.inflate(R.layout.recyclerview, mainLayout, false);
                android.view.View views = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_profile_list, parent, false);
                return new ProductViewHolder(myLayout);

            }
        };
        recyclerView.setAdapter(adapter);

        button = findViewById(R.id.button2);


        button.setOnClickListener(new android.view.View.OnClickListener() {

            @Override

            public void onClick(android.view.View v) {

                if (v == button) {

                    startActivity(new Intent(ProfileList.this, MainActivity.class));
                }

            }

        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        //Add your action onClick
                        break;
                    case R.id.Social:
                        startActivity(new Intent(ProfileList.this, Videolists.class));
                        break;
                    case R.id.Inbox:
                        startActivity(new Intent(ProfileList.this, Inboxandmessages.class));
                        break;


                }
                return false;
            }
        });

        BottomNavigationMenuView mbottomNavigationMenuView =
                (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);

        View view = mbottomNavigationMenuView.getChildAt(3);

        BottomNavigationItemView itemView = (BottomNavigationItemView) view;

        View cart_badge = LayoutInflater.from(this)
                .inflate(R.layout.activity_inboximage,
                        mbottomNavigationMenuView, false);
        notificationnumber = cart_badge.findViewById(R.id.cart_badge);
        notificationnumber.setText("0");
        docRef = rootRef.collection("userdata").document(useruid).collection("notifications").document("notifications");
        notifications = 0;

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                currentInbox = documentSnapshot.toObject(Inbox.class);
                notifications = currentInbox.getUnreadInbox() + currentInbox.getUnreadMessages();
                notificationnumber.setText(String.valueOf(notifications));
                if (notifications != 0) {
                    notificationnumber.setVisibility(View.VISIBLE);

                }

            }
        });
        /* changing the number of notifications*/


        itemView.addView(cart_badge);
        bottomNavigationView.getMenu().findItem(R.id.Diagnoses).setChecked(true);
        bottomNavigationView.setSelectedItemId(R.id.Diagnoses);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                currentInbox = documentSnapshot.toObject(Inbox.class);
                notifications = currentInbox.getUnreadInbox() + currentInbox.getUnreadMessages();
                notificationnumber.setText(String.valueOf(notifications));
                if (notifications != 0) {
                    notificationnumber.setVisibility(View.VISIBLE);

                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (adapter != null) {
            adapter.stopListening();
        }
    }

    protected void meme(final ProfileClass productName) {
        ///     GlideApp.with(this /* context */)

        ///              .load(storage.getReferenceFromUrl(productName.getProfileImageUrl()))

        ///           .into(imageView);
    }

    private class ProductViewHolder extends RecyclerView.ViewHolder {
        private android.view.View view;

        ProductViewHolder(android.view.View itemView) {
            super(itemView);
            view = itemView;
        }

        void setProductName(final ProfileClass productName) {
            CardView cview = view.findViewById(R.id.cardview);
            textView = view.findViewById(R.id.person_name);
            textView.setText(productName.getUserName());
            imageView = (view.findViewById(R.id.person_photo));
            TextView textViewy = view.findViewById(R.id.person_age);
            textViewy.setText(String.valueOf(productName.getFriends()));

            meme(productName);

            cview.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View view) {
                    currentUpload = productName;
                    startActivity(new Intent(ProfileList.this, ViewProfile.class));
                }
            });
        }
    }
}






