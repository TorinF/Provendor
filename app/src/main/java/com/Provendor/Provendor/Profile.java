package com.Provendor.Provendor;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.adapters.TimePickerBindingAdapter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
    ImageView imagevi;
    UploadTask mUploadTask;
    private TextView vids;
    private static final String VIDEO_DIRECTORY = "/demonuts";
    Button uploadbutton;
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
        imagevi = (ImageView) findViewById(R.id.displayImage);
        ques = (TextView) findViewById(R.id.qs);
        uploadbutton = (Button) findViewById(R.id.button5);
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

        // if (owner!=null) {

        // }
        changeProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });


        uploadbutton.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){
        if (mUploadTask != null && mUploadTask.isInProgress()) {
            Toast.makeText(Profile.this, "Upload in progress", Toast.LENGTH_SHORT).show();
        } else {
            uploadFile();
        }
    }
    });
}




    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }
    public static final int PICK_IMAGE = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            contentURI = data.getData();

            Glide.with(this).load(contentURI).into(imagevi);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        final StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        final StorageReference photoRefy = storageRef.child("user/" + uid + "/images/" + System.currentTimeMillis() + ".jpg");
        if (contentURI != null) {


         mUploadTask= photoRefy.putFile(contentURI);
            Task<Uri> urlTask = mUploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return photoRefy.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String downloadURL = downloadUri.toString();
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
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
