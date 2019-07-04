package com.Provendor.Provendor.ui.main;

import android.content.Context;
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
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Provendor.Provendor.MainActivity;
import com.Provendor.Provendor.Notification;
import com.Provendor.Provendor.ProfileClass;
import com.Provendor.Provendor.ProfileList;
import com.Provendor.Provendor.R;
import com.Provendor.Provendor.Videolists;
import com.Provendor.Provendor.ViewProfile;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    private TextView textView;
    private FirebaseFirestore mDatabaseRef;
    private Button button;
    private Query mChartsQuery;
    private RecyclerView mRecycler;
    public static Notification currentUpload;
    private ImageView imageView;
    private FirebaseAuth mAuth;
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirestoreRecyclerAdapter<Notification, PlaceholderFragment.ProductViewHolder> adapter;
    private ViewFlipper VF;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_inboxandmessages, container, false);
        }
            @Override
            public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String useruid = currentUser.getUid();
        RecyclerView recyclerView = (RecyclerView)getView().findViewById(R.id.notificationlist);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(llm);

        /*when query*/
        Query query = rootRef.collection("userdata").document(useruid).collection("notifications").document("notifications").collection("notifications")
                .orderBy("type", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Notification> options = new FirestoreRecyclerOptions.Builder<Notification>()
                .setQuery(query, Notification.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Notification, PlaceholderFragment.ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PlaceholderFragment.ProductViewHolder holder, int position, @NonNull Notification productModel) {
                holder.setProductName(productModel);

            }

            @NonNull
            @Override
            public PlaceholderFragment.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = getLayoutInflater();
                LinearLayout mainLayout = (LinearLayout) getView().findViewById(R.id.linear);
                android.view.View myLayout = inflater.inflate(R.layout.recyclerview, mainLayout, false);
                android.view.View views = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_inboxandmessages, parent, false);
                return new PlaceholderFragment.ProductViewHolder(myLayout);

            }
        };
        recyclerView.setAdapter(adapter);






    }

    @Override
     public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();

        if (adapter != null) {
            adapter.stopListening();
        }
    }

    protected void meme(final Notification productName) {
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

        void setProductName(final Notification productName) {
            CardView cview = view.findViewById(R.id.cardview);
            textView = view.findViewById(R.id.person_name);
            textView.setText(productName.gettype());
            imageView = (view.findViewById(R.id.person_photo));
            TextView textViewy = view.findViewById(R.id.person_age);
            textViewy.setText(String.valueOf(productName.getuseruid()));
            Button buttonaccept= view.findViewById(R.id.button6);
            Button buttondeny= view.findViewById(R.id.button7);

            buttonaccept.setVisibility(View.VISIBLE);
            buttondeny.setVisibility(View.VISIBLE);
            buttonaccept.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View view) {
                        ///logic here to get username and open up profile
                    Context context = getContext();
                    CharSequence text = "You Clicked on the button!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }
            });
            meme(productName);

            cview.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View view) {
                  if(productName.gettype()=="friendreq")
                      ///logic here to get username and open up profile
                    startActivity(new Intent(getActivity(), ViewProfile.class));
                    Context context = getContext();
                    CharSequence text = "You Clicked on the item !";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            });
        }
    }
    }
