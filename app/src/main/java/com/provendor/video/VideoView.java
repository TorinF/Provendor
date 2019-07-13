package com.provendor.video;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.algolia.search.saas.Client;
import com.algolia.search.saas.Index;
import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.provendor.R;

import org.json.JSONException;
import org.json.JSONObject;

public class VideoView extends AppCompatActivity {
    public static boolean fromSearchy;
    public static JSONObject videoy;
    public Video currentUpload;
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private TextView textView;
    private FirebaseFirestore mDatabaseRef;
    private Button pushCom;
    private Query mChartsQuery;
    private RecyclerView mRecycler;
    private ImageView imageView;
    private ActionBar toolbar;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirestoreRecyclerAdapter<Video, VideoView.ProductViewHolder> adapter;
    private ViewFlipper VF;
    private ToggleButton like;
    private ToggleButton dislike;
    private EditText comment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        playerView = findViewById(R.id.player_view);

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String useruid = currentUser.getUid();
        RecyclerView recyclerView = findViewById(R.id.viewsundervideo);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(llm);
        Client client = new Client("AK3H70NKN5", "2c8a8880eff5c3664adaa84af4b2bfad");
        Index index = client.getIndex("com.provendor");
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

        index.addObjectAsync(person, null);
        like = findViewById(R.id.likeTogs);
        dislike = findViewById(R.id.disTog);

        like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (currentUpload.getLikes() >= currentUpload.getDislikes()) {
                        currentUpload.setLikes(currentUpload.getLikes() + 1);
                    } else {
                        currentUpload.setDislikes(currentUpload.getDislikes() - 1);
                    }
                    dislike.setChecked(false);
                }
            }
        });

        dislike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (currentUpload.getDislikes() >= currentUpload.getLikes()) {
                        currentUpload.setDislikes(currentUpload.getDislikes() + 1);
                    } else {
                        currentUpload.setLikes(currentUpload.getLikes() - 1);
                    }
                    like.setChecked(false);
                }
            }
        });

        comment = findViewById(R.id.commentTxt);

        pushCom = findViewById(R.id.pushComment);

        pushCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentUpload.setComment(comment.toString());
            }
        });






        /*when query*/
        Query query = rootRef.collection("videos").orderBy("views", Query.Direction.ASCENDING).limit(10);

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
                FrameLayout mainLayout = findViewById(R.id.linearvideos);
                android.view.View myLayout = inflater.inflate(R.layout.videobelowsearch, mainLayout, false);
                return new VideoView.ProductViewHolder(myLayout);

            }
        };
        recyclerView.setAdapter(adapter);
        final Button searchView = findViewById(R.id.searchbarlist);
        searchView.setOnClickListener(new android.view.View.OnClickListener() {

            @Override

            public void onClick(android.view.View v) {

                if (v == searchView) {

                    startActivity(new Intent(VideoView.this, SearchPageVideos.class));
                }

            }

        });


    }


    @Override
    protected void onStart() {
        super.onStart();
        ExtractorMediaSource mediaSource = null;
        adapter.startListening();
        if (player == null) {
            // 1. Create a default TrackSelector
            LoadControl loadControl = new DefaultLoadControl(
                    new DefaultAllocator(true, 16),
                    VideoPlayerConfig.MIN_BUFFER_DURATION,
                    VideoPlayerConfig.MAX_BUFFER_DURATION,
                    VideoPlayerConfig.MIN_PLAYBACK_START_BUFFER,
                    VideoPlayerConfig.MIN_PLAYBACK_RESUME_BUFFER, -1, true);

            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);
            // 2. Create the player
            player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this), trackSelector, loadControl);
            playerView.setPlayer(player);
            playerView.setPlayer(player);
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this,
                    Util.getUserAgent(this, getString(R.string.app_name)), bandwidthMeter);
            if (fromSearchy) {
                try {
                    mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(videoy.getString("videoUrl")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else
                mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(Changename.currentVideo.getVideoUrl()));
            if (mediaSource != null)
                player.prepare(mediaSource);
            player.setPlayWhenReady(true);
            fromSearchy = false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (adapter != null) {
            adapter.stopListening();
        }
        playerView.setPlayer(null);
        player.release();
        player = null;
    }

    private class ProductViewHolder extends RecyclerView.ViewHolder {
        private android.view.View view;

        ProductViewHolder(android.view.View itemView) {
            super(itemView);
            view = itemView;
        }

        void setProductName(final Video productName) {
            CardView cview = view.findViewById(R.id.cardview);
            textView = view.findViewById(R.id.person_name);
            textView.setText(productName.getName());
            imageView = (view.findViewById(R.id.person_photo));
            Glide.with(imageView.getContext()).load(storage.getReferenceFromUrl(productName.getImageUrl())).into(imageView);

            TextView textViewy = view.findViewById(R.id.person_age);
            textViewy.setText(productName.getDate());


            cview.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View view) {
                    Changename.currentVideo = productName;
                    startActivity(new Intent(VideoView.this, VideoView.class));
                }
            });
        }

    }


}
