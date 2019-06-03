package com.Provendor.Provendor;

import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
<<<<<<< HEAD
import androidx.appcompat.app.ActionBar;
=======
>>>>>>> origin/master3
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.Provendor.Provendor.tensorflow.loginactivity;
import com.algolia.search.saas.Client;
import com.algolia.search.saas.Index;
import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;

import org.json.JSONException;
import org.json.JSONObject;

public class VideoView extends AppCompatActivity {
private PlayerView playerView;
private SimpleExoPlayer player;
    public static boolean fromSearchy;
    private TextView textView;
    private FirebaseFirestore mDatabaseRef;
    private Button button;
    private Query mChartsQuery;
    private RecyclerView mRecycler;
    public static JSONObject videoy;
    public static  Video currentUpload;
    private ImageView imageView;
    private ActionBar toolbar;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirestoreRecyclerAdapter<Video, VideoView.ProductViewHolder> adapter;
    private ViewFlipper VF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        playerView=findViewById(R.id.player_view);

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

<<<<<<< HEAD
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
=======
>>>>>>> origin/master3

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String useruid = currentUser.getUid();
        RecyclerView recyclerView = findViewById(R.id.viewsundervideo);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(llm);
        Client client = new Client("AK3H70NKN5", "2c8a8880eff5c3664adaa84af4b2bfad");
        Index index = client.getIndex("Provendor");
        JSONObject person = new JSONObject();
        try {
            person.put("Title", "meme");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            person.getString("Title");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        index.addObjectAsync(person,  null);



        /*when query*/
        Query query =         rootRef.collection("videos").orderBy("views", Query.Direction.ASCENDING).limit(10);

        FirestoreRecyclerOptions<Video> options = new FirestoreRecyclerOptions.Builder<Video>()
                .setQuery(query, Video.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Video, VideoView.ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull VideoView.ProductViewHolder holder, int position, @NonNull Video productModel) {
                holder.setProductName(productModel);

            }

            @NonNull
            @Override
            public VideoView.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = getLayoutInflater();
                LinearLayout mainLayout = (LinearLayout) findViewById(R.id.linearvideos);
                android.view.View myLayout = inflater.inflate(R.layout.videobelowsearch,mainLayout, false);
                return new VideoView.ProductViewHolder(myLayout);

            }
        };
        recyclerView.setAdapter(adapter);
      final  Button searchView = (Button) findViewById(R.id.searchbarlist);
        searchView.setOnClickListener(new android.view.View.OnClickListener() {

            @Override

            public void onClick(android.view.View v) {

                if (v == searchView){

                    startActivity(new Intent(VideoView.this, Searchpagevideos.class));
                }

            }

        });



    }


    @Override
    protected void onStart() {
        super.onStart();
        ExtractorMediaSource mediaSource = null;
        adapter.startListening();
        player= ExoPlayerFactory.newSimpleInstance(this,new DefaultTrackSelector());
        playerView.setPlayer(player);
        DefaultDataSourceFactory dataSourceFactory= new DefaultDataSourceFactory(this, Util.getUserAgent(this,"ExoPlayer"));

         
        if(fromSearchy) {
            try {
                mediaSource=new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(videoy.getString("videoUrl")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
            mediaSource=new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(Changename.currentVideo.getVideoUrl()));
        if (mediaSource!=null)
        player.prepare(mediaSource);
        player.setPlayWhenReady(true);
        fromSearchy=false;
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (adapter != null) {
            adapter.stopListening();
        }
        playerView.setPlayer(null);
        player.release();
        player=null;
    }

    private class ProductViewHolder extends RecyclerView.ViewHolder {
        private android.view.View view;

        ProductViewHolder(android.view.View itemView) {
            super(itemView);
            view = itemView;
        }

        void setProductName(final Video productName) {
            CardView cview =view.findViewById(R.id.cardview);
            textView = view.findViewById(R.id.person_name);
            textView.setText(productName.getName());
            imageView= (view.findViewById(R.id.person_photo));
            Glide.with(imageView.getContext()).load(storage.getReferenceFromUrl(productName.getImageUrl())).into(imageView);

            TextView textViewy= view.findViewById(R.id.person_age);
            textViewy.setText(productName.getDate());



            cview.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View view) {
                    Changename.currentVideo=productName;
                    startActivity(new Intent(VideoView.this, VideoView.class));
                }
            });
        }

    }





}
