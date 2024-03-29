package com.provendor.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.provendor.R;
import com.provendor.users.Notification;
import com.provendor.users.PersonRelations;
import com.provendor.users.ProfileClass;
import com.provendor.users.ProfileList;
import com.provendor.users.ViewProfile;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    public static Notification currentUpload;
    private TextView textView;
    private FirebaseFirestore mDatabaseRef;
    private Button button;
    private Query mChartsQuery;
    private RecyclerView mRecycler;
    private ImageView imageView;
    private FirebaseAuth mAuth;
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirestoreRecyclerAdapter<Notification, PlaceholderFragment.ProductViewHolder> adapter;
    private ViewFlipper VF;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inboxandmessages, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();
        String useruid = currentUser.getUid();
        RecyclerView recyclerView = getView().findViewById(R.id.notificationlist);
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
                LinearLayout mainLayout = getView().findViewById(R.id.linear);
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

        void setProductName(final Notification productName) { //setting each item of the recyclerview
            CardView cview = view.findViewById(R.id.cardview);
            textView = view.findViewById(R.id.person_name);
            textView.setText(productName.gettype());
            imageView = (view.findViewById(R.id.person_photo));
            TextView textViewy = view.findViewById(R.id.person_age);
            textViewy.setText(String.valueOf(productName.getuseruid()));
            Button buttonaccept = view.findViewById(R.id.button6);
            Button buttondeny = view.findViewById(R.id.button7);

            if (productName.gettype().equals("friendReq")) {
                buttonaccept.setVisibility(View.VISIBLE);
                buttondeny.setVisibility(View.VISIBLE);
            }
            buttonaccept.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO:logic here to accept/deny FriendRequest.  Send notification to other user with recipients decision (See Notification Class). Delete this notification from user's node when the user clicks on the button.

                    final String friendedID = productName.getuseruid();
                    final String userUid = currentUser.getUid();

                    //update relations

                    //Set accepted user relation to friends
                    final DocumentReference relationRef =  db.collection("userdata").document(userUid).collection("relations").document(friendedID);
                    final Task<DocumentSnapshot> relation = relationRef.get();
                    relation.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                relationRef.update("isfriend", PersonRelations.FRIENDED);
                            } else {
                                PersonRelations personRelations = new PersonRelations();
                                personRelations.setUid(friendedID);
                                personRelations.setIsfriend(PersonRelations.FRIENDED);
                                relationRef.set(personRelations);
                            }
                        }
                    });

                    //Edit sender's relation node for them & send notification
                    //TODO:Determine if editing other users node is against database rules
                    final DocumentReference friendRelationRef = db.collection("userdata").document(friendedID).collection("relations").document(userUid);
                    final Task<DocumentSnapshot> friendRelation = friendRelationRef.get();
                    friendRelation.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot document = task.getResult();
                            PersonRelations relation;
                            if (document.exists()) {
                                relation = document.toObject(PersonRelations.class);
                                friendRelationRef.update("isfriend", PersonRelations.FRIENDED);
                            } else {
                                relation = new PersonRelations();
                                relation.setUid(friendedID);
                                relation.setIsfriend(PersonRelations.FRIENDED);
                                friendRelationRef.set(relation);
                            }
                            //Create notification
                            Notification notification = new Notification("friend", relation);

                            //Send notification
                            db.collection("userdata").document(friendedID).collection("notifications").document("notifications").collection("notifications").add(notification);
                            DocumentReference RecipientDocument = db.collection("userdata").document(friendedID).collection("notifications").document("notifications");
                            RecipientDocument.update("unreadInbox", FieldValue.increment(1));

                        }
                    });

                    //Toast code
                    Context context = getContext();
                    CharSequence text = "You Clicked on the button!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }
            });

            buttondeny.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View view) {

                    final String rejecteduid = productName.getuseruid();
                    String sender = currentUser.getUid();

                    final DocumentReference friendRelationRef = db.collection("userdata").document(rejecteduid).collection("relations").document(sender);
                    final Task<DocumentSnapshot> friendRelation = friendRelationRef.get();
                    friendRelation.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot document = task.getResult();
                            PersonRelations relation;
                            if (document.exists()) {
                                relation = document.toObject(PersonRelations.class);
                                friendRelationRef.update("isfriend", PersonRelations.FRIENDED);
                            } else {
                                relation = new PersonRelations();
                                relation.setUid(rejecteduid);
                                relation.setIsfriend(PersonRelations.FRIENDED);
                                friendRelationRef.set(relation);
                            }
                        }
                    });
                }
            });
            meme(productName);

            cview.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View view) {
                    ///logic here to get username and open up profile
                    //TODO: If user clicks the item, and the type is a friendrequest, it should send the user to the friend requester's profile
                    String uid = productName.getuseruid();
                    DocumentReference docRef = db.collection("userdata").document(uid);

                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            ProfileList.currentUpload = documentSnapshot.toObject(ProfileClass.class);
                            startActivity(new Intent(getActivity(), ViewProfile.class));
                        }
                    });

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
