package com.Provendor.Provendor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;


public class Profile extends AppCompatActivity {
    private String uid;
    private ProfileClass owner;
    private TextView username;
    private TextView userID;
    Uri contentURI;
    private TextView vids;
    private static final String VIDEO_DIRECTORY = "/demonuts";

    private TextView ques;
    private TextView friends;
    private TextView followers;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button changeProfilePic;
    private int GALLERY = 1, CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        username = (TextView) findViewById(R.id.UsernameText);
        uid = user.getUid();
        userID = (TextView) findViewById(R.id.UserID1);
        vids = (TextView) findViewById(R.id.vids);
        ques = (TextView) findViewById(R.id.qs);
        friends = (TextView) findViewById(R.id.friends);
        followers = (TextView) findViewById(R.id.followers);


        DocumentReference docRef = db.collection("userdata").document(uid);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                owner = documentSnapshot.toObject(ProfileClass.class);
                username.setText(owner.getUserName());
                userID.setText(owner.getUser());
                vids.setText("Videos: " + owner.getVids());
                ques.setText("Questions: " + owner.getQuestions());
                friends.setText("Friends: " + owner.getFriends());
                followers.setText("Followers: " + owner.getFollowers());
            }
        });
        changeProfilePic = (Button) findViewById(R.id.button4);
        changeProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
        // if (owner!=null) {

        // }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("result", "" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            Log.d("what", "cancle");
            return;
        }

        if (requestCode == GALLERY) {
            Log.d("what", "gale");
            if (data != null) {
                contentURI = data.getData();

                String selectedVideoPath = getPath(contentURI);
                Log.d("path", selectedVideoPath);
                saveVideoToInternalStorage(selectedVideoPath);


            }

        } else if (requestCode == CAMERA) {
            contentURI = data.getData();
            String recordedVideoPath = getPath(contentURI);
            Log.d("frrr", recordedVideoPath);
            saveVideoToInternalStorage(recordedVideoPath);

        }
    }

    private void saveVideoToInternalStorage(String filePath) {

        File newfile;

        try {

            File currentFile = new File(filePath);
            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + VIDEO_DIRECTORY);
            newfile = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");

            if (!wallpaperDirectory.exists()) {
                wallpaperDirectory.mkdirs();
            }

            if (currentFile.exists()) {

                InputStream in = new FileInputStream(currentFile);
                OutputStream out = new FileOutputStream(newfile);

                // Copy the bits from instream to outstream
                byte[] buf = new byte[1024];
                int len;

                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                Log.v("vii", "Video file saved successfully.");
            } else {
                Log.v("vii", "Video saving failed. Source file missing.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select video from gallery",
                "Record video from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                chooseVideoFromGallary();
                                break;
                            case 1:
                                takeVideoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void chooseVideoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takeVideoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    private void uploadtoFirebase() {

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        final StorageReference photoRefy = storageRef.child("user/" + uid + "/images/" + System.currentTimeMillis() + ".jpg");


// add File/URI
        if (contentURI != null) {
            photoRefy.putFile(contentURI);
        }
    }
}
