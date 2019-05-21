package com.Provendor.Provendor;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;

public class VideoView extends AppCompatActivity {
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
    private FirestoreRecyclerAdapter<Video, VideoView.ProductViewHolder> adapter;
    private ViewFlipper VF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        playerView=findViewById(R.id.player_view);

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String useruid = currentUser.getUid();
        RecyclerView recyclerView = findViewById(R.id.viewsundervideo);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);


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


    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        player= ExoPlayerFactory.newSimpleInstance(this,new DefaultTrackSelector());
        playerView.setPlayer(player);
        DefaultDataSourceFactory dataSourceFactory= new DefaultDataSourceFactory(this, Util.getUserAgent(this,"ExoPlayer"));
        ExtractorMediaSource mediaSource=new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(Changename.currentVideo.getVideoUrl()));
        player.prepare(mediaSource);
        player.setPlayWhenReady(true);
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

        void setProductName(final Video productName) {
            CardView cview =view.findViewById(R.id.cardview);
            textView = view.findViewById(R.id.person_name);
            textView.setText(productName.getName());
            imageView= (view.findViewById(R.id.person_photo));
         //   TextView textViewy= view.findViewById(R.id.person_age);
           // textViewy.setText(productName.getDate());

         //   meme(productName);

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
