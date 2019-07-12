package com.provendor.diagnosis;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.provendor.GlideApp;
import com.provendor.MainActivity;
import com.provendor.R;


public class Diagnosis extends AppCompatActivity {
    FirebaseStorage storage = FirebaseStorage.getInstance();
    Upload upload;
    ImageButton button;
    TextView textElement;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);
        upload = MainActivity.Companion.a();

        imageView = (findViewById(R.id.imageCaptured));

        GlideApp.with(this /* context */)

                .load(storage.getReferenceFromUrl(upload.getImageUrl()))

                .into(imageView);

        textElement = findViewById(R.id.textResult);
        textElement.setText(upload.getDisease());
        button = findViewById(R.id.imageclose);
        button.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                if (v == button) {

                    startActivity(new Intent(Diagnosis.this, Diseaselist.class));
                }

            }

        });


// ImageView in your Activity


// Download directly from StorageReference using Glide
// (See MyAppGlideModule for Loader registration)

    }

    public void loadWithGlide() {

        // [START storage_load_with_glide]

        // Reference to an image file in Cloud Storage


        // ImageView in your Activity


        // Download directly from StorageReference using Glide

        // (See MyAppGlideModule for Loader registration)


        // [END storage_load_with_glide]

    }


}
