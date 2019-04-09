package com.Provendor.Provendor;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.Provendor.Provendor.tensorflow.LoginActivity2;
import com.Provendor.Provendor.tensorflow.loginactivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.firestore.core.View;

public class Diseaselist extends AppCompatActivity {
    private TextView textView;
    private FirebaseFirestore mDatabaseRef;
    private Button button;
    private Query mChartsQuery;
    private RecyclerView mRecycler;
    public static  Upload currentUpload;
    private FirebaseAuth mAuth;
    private FirestoreRecyclerAdapter<Upload, ProductViewHolder> adapter;
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
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);



        Query query = rootRef.collection("users").document(useruid).collection("diagnoses")
                .orderBy("disease", Query.Direction.ASCENDING);

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
               android.view.View views = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_diseaselist, parent, false);
                return new ProductViewHolder(views);

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

