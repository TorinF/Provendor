package com.Provendor.Provendor;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
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

public class Videolists extends AppCompatActivity {
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private TextView textView;
    private FirebaseFirestore mDatabaseRef;
    private Button button;
    private Query mChartsQuery;
    private RecyclerView mRecycler;
    public static  Video currentUpload;
    private ImageView imageView;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirestoreRecyclerAdapter<Video, Videolists.ProductViewHolder> adapter;
    private ViewFlipper VF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videolists);

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String useruid = currentUser.getUid();
        RecyclerView recyclerView = findViewById(R.id.Popular);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(llm);


        /*when query*/
        Query query =   rootRef.collection("videos").whereEqualTo("category","free").orderBy("views", Query.Direction.ASCENDING).limit(10);

       FirestoreRecyclerOptions<Video> options = new FirestoreRecyclerOptions.Builder<Video>()
                .setQuery(query, Video.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Video, Videolists.ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Videolists.ProductViewHolder holder, int position, @NonNull Video productModel) {
               holder.setProductName( productModel);

            }

            @NonNull
            @Override
            public Videolists.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = getLayoutInflater();
                LinearLayout mainLayout = (LinearLayout) findViewById(R.id.linear);
                android.view.View myLayout = inflater.inflate(R.layout.videoviewy,mainLayout, false);
                return new Videolists.ProductViewHolder(myLayout);

            }
        };
        recyclerView.setAdapter(adapter);

        final Button button = (Button) findViewById(R.id.button3);
        final Button searchView = (Button) findViewById(
                R.id.searchbar1);

        button.setOnClickListener(new android.view.View.OnClickListener() {

            @Override

            public void onClick(android.view.View v) {

                if (v == button){

                    startActivity(new Intent(Videolists.this, UploadVideo.class));
                }

            }

        });

        searchView.setOnClickListener(new android.view.View.OnClickListener() {

            @Override

            public void onClick(android.view.View v) {

                if (v == searchView){

                    startActivity(new Intent(Videolists.this, Searchpagevideos.class));
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
                    case R.id.Diagnoses:
                        startActivity(new Intent(Videolists.this, Diseaselist.class));
                        break;


                }
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.Social);
        bottomNavigationView.getMenu().findItem(R.id.Social).setChecked(true);




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
    protected void meme(final Video productName){
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

        void setProductName( final Video productName) {


            CardView cview =view.findViewById(R.id.cardview);
            textView = view.findViewById(R.id.person_name);
            textView.setText(productName.getName());
            imageView= (view.findViewById(R.id.videophoto));
             TextView textViewy= view.findViewById(R.id.person_age);
            textViewy.setText(productName.getDate());

            meme(productName);
            cview.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View view) {
                    Changename.currentVideo=productName;
                    startActivity(new Intent(Videolists.this, VideoView.class));
                }
            });
        }

    }

}
