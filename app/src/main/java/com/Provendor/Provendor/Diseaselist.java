package com.Provendor.Provendor;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.annotation.NonNull;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.Provendor.Provendor.tensorflow.LoginActivity2;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;

public class Diseaselist extends AppCompatActivity {
    private TextView textView;
    private FirebaseFirestore mDatabaseRef;
    private Button button;
    private Query mChartsQuery;
    private RecyclerView mRecycler;
    public static  Upload currentUpload;
    private ImageView imageView;
    private FirebaseAuth mAuth;
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirestoreRecyclerAdapter<Upload, ProductViewHolder> adapter;
    private ViewFlipper VF;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diseaselist);

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String useruid = currentUser.getUid();
        RecyclerView recyclerView = findViewById(R.id.goodmeme);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        VF = (ViewFlipper) findViewById(R.id.ViewFlipper01);
        recyclerView.setLayoutManager(llm);
        playerView=findViewById(R.id.video_view);

/*when query*/
        Query query = rootRef.collection("users").document(useruid).collection("diagnoses")
                .orderBy("time1", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Upload> options = new FirestoreRecyclerOptions.Builder<Upload>()
                    .setQuery(query, Upload.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Upload, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull  ProductViewHolder holder, int position, @NonNull Upload productModel) {
                holder.setProductName(productModel);

            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = getLayoutInflater();
                LinearLayout mainLayout = (LinearLayout) findViewById(R.id.linear);
                android.view.View myLayout = inflater.inflate(R.layout.recyclerview,mainLayout, false);
               android.view.View views = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_diseaselist, parent, false);
                return new ProductViewHolder(myLayout);

            }
        };
        recyclerView.setAdapter(adapter);

        button = (Button)findViewById(R.id.button2);





        button.setOnClickListener(new android.view.View.OnClickListener() {

            @Override

            public void onClick(android.view.View v) {

                if (v == button){

                    startActivity(new Intent(Diseaselist.this, MainActivity.class));
                }

            }

        });
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        //Add your action onClick
                        break;
                    case R.id.Social:
                        startActivity(new Intent(Diseaselist.this, Videolists.class));
                        break;


                }
                return false;
            }
        });
        bottomNavigationView.getMenu().findItem(R.id.Diagnoses).setChecked(true);
        bottomNavigationView.setSelectedItemId(R.id.Diagnoses);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (adapter != null) {
            adapter.stopListening();
        }
    }
    protected void meme(final Upload productName){
        GlideApp.with(this /* context */)

                .load(storage.getReferenceFromUrl(productName.getImageUrl()))

                .into(imageView);
    }
    private class ProductViewHolder extends RecyclerView.ViewHolder {
        private android.view.View view;

        ProductViewHolder(android.view.View itemView) {
            super(itemView);
            view = itemView;
        }

        void setProductName(final Upload productName) {
            CardView cview =view.findViewById(R.id.cardview);
            textView = view.findViewById(R.id.person_name);
            textView.setText(productName.getDisease());
            imageView= (view.findViewById(R.id.person_photo));
            TextView textViewy= view.findViewById(R.id.person_age);
            textViewy.setText(productName.getDate());

            meme(productName);

            cview.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View view) {
                   currentUpload=productName;
                    startActivity(new Intent(Diseaselist.this, DiagnosisClick.class));
                }
            });
        }

    }


}

