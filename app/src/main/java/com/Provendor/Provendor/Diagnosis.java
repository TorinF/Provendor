package com.Provendor.Provendor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.provendor.MainActivity.storage;

public class Diagnosis extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);
        Intent intent = getIntent();
        Intent i = getIntent();
        Upload cust = (Upload) i.getParcelableExtra("key");

        StorageReference gsReference =
                storage.getReferenceFromUrl(""+cust.getImageUrl().toString());


// ImageView in your Activity
        ImageView imageView = findViewById(R.id.imageCaptured);

// Download directly from StorageReference using Glide
// (See MyAppGlideModule for Loader registration)
        Glide.with(this /* context */)
                .load(gsReference)
                .into(imageView);
    }
}
