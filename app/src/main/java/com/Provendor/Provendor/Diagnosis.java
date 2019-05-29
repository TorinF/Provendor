package com.Provendor.Provendor;


import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.Provendor.Provendor.GlideApp;
import com.google.firebase.storage.FirebaseStorage;

import androidx.appcompat.app.AppCompatActivity;


public class Diagnosis extends AppCompatActivity {
    FirebaseStorage storage = FirebaseStorage.getInstance();
    Upload meme;
     ImageButton button;
    TextView textElement;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);
meme= MainActivity.Companion.a();

 imageView= (findViewById(R.id.imageCaptured));

                GlideApp.with(this /* context */)

                        .load(storage.getReferenceFromUrl(meme.getImageUrl()))

                        .into(imageView);

       textElement = (TextView)findViewById(R.id.textResult);
        textElement.setText(meme.getDisease());
         button = (ImageButton)findViewById(R.id.imageclose);
        button.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                if (v == button){

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
