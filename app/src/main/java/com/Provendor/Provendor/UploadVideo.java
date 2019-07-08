package com.Provendor.Provendor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Calendar;
import android.app.ProgressDialog;

import wseemann.media.FFmpegMediaMetadataRetriever;

public class UploadVideo extends AppCompatActivity {
    private Button btn;
    private Button btn2;
    public static Uri contentURI;
    public static Video UploadedVideo;
    private static ProgressDialog progressDialog;
    private VideoView videoView;
    private FirebaseAuth mAuth;
    private String uid;
    private FirebaseUser currentUser;
    private static final String VIDEO_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_video);
        UploadedVideo=new Video();
        btn2 = (Button) findViewById(R.id.btn2);
        btn = (Button) findViewById(R.id.btn);
        videoView = (VideoView) findViewById(R.id.yeet);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
         uid = user.getUid();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadtoFirebase();
            }
        });
    }

    private void uploadtoFirebase() {
        ProgressBar progressBar= (ProgressBar)findViewById(R.id.progressBar2);

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        final StorageReference photoRefy = storageRef.child("user/"+uid + "/images/"+System.currentTimeMillis()+".jpg");


        final StorageReference photoRef = storageRef.child("user/"+uid + "/videos/"+System.currentTimeMillis()+".mp4");
// add File/URI
        db = FirebaseFirestore.getInstance();
        db.collection("userdata").document(uid).collection("videos").document("myvideos").set(UploadedVideo);

        if (contentURI != null)
        {


            photoRef.putFile(contentURI)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Upload succeeded
                            Toast.makeText(getApplicationContext(), "Upload Success...", Toast.LENGTH_SHORT).show();
                            photoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    final Uri downloadUrl = uri;

                                    UploadedVideo.setVideoUrl(downloadUrl.toString());
                                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//use one of overloaded setDataSource() functions to set your data source
                                    retriever.setDataSource(getBaseContext(), contentURI);
                                    Bitmap bitmap=   retriever.getFrameAtTime(200);
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                    byte[] data = baos.toByteArray();

                                   photoRefy.putBytes(data) .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            // Upload succeeded
                                            Toast.makeText(getApplicationContext(), "Upload Success...", Toast.LENGTH_SHORT).show();
                                            photoRefy.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    final Uri downloadUrl = uri;
                                                    UploadedVideo.setImageUrl(downloadUrl.toString());
//use one of overloaded setDataSource() functions to set your data source


                                                }
                                            });
                                        }
                                    })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception exception) {
                                                    // Upload failed
                                                    Toast.makeText(getApplicationContext(), "Upload failed...", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                                            //displaying percentage in progress dialog
                                        }
                                    });
                                    String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                                    long timeInMillisec =Long.parseLong(time )/1000;
                                    UploadedVideo.setLength(((int)timeInMillisec));
                                    retriever.release();

                                    startActivity(new Intent(UploadVideo.this, Changename.class));
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Upload failed
                            Toast.makeText(getApplicationContext(), "Upload failed...", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            ProgressBar progressBar= (ProgressBar)findViewById(R.id.progressBar2);
                            progressBar.setVisibility(View.VISIBLE);
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();                            System.out.println("Upload is " + progress + "% done");
                            int currentprogress = (int) progress;

                            progressBar.setProgress(currentprogress);
                            //displaying percentage in progress dialog
                        }
                    });
    }
    }
    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select video from gallery",
                "Record video from camera" };
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
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takeVideoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("result",""+resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            Log.d("what","cancle");
            return;
        }
        if (requestCode == GALLERY) {
            Log.d("what","gale");
            if (data != null) {
                 contentURI = data.getData();

                String selectedVideoPath = getPath(contentURI);
                Log.d("path",selectedVideoPath);
                saveVideoToInternalStorage(selectedVideoPath);
                videoView.setVideoURI(contentURI);
                videoView.requestFocus();
                videoView.start();

            }

        } else if (requestCode == CAMERA) {
             contentURI = data.getData();
            String recordedVideoPath = getPath(contentURI);
            Log.d("frrr",recordedVideoPath);
            saveVideoToInternalStorage(recordedVideoPath);
            videoView.setVideoURI(contentURI);
            videoView.requestFocus();
            videoView.start();
        }
    }

    private void saveVideoToInternalStorage (String filePath) {

        File newfile;

        try {

            File currentFile = new File(filePath);
            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + VIDEO_DIRECTORY);
            newfile = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".mp4");

            if (!wallpaperDirectory.exists()) {
                wallpaperDirectory.mkdirs();
            }

            if(currentFile.exists()){

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
            }else{
                Log.v("vii", "Video saving failed. Source file missing.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

}

