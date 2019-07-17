package com.provendor.utils;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.provendor.R;
import com.provendor.diagnosis.Diseaselist;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

import static com.provendor.tensorflow.loginactivity.newUser;

public class PicUtils extends AppCompatActivity
{
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final int PICK_IMAGE = 1;
    private static final String VIDEO_DIRECTORY = "/demonuts";
    private CropImageView cropImageView;
    private Uri contentURI;
    private ImageView imagevi;
    private UploadTask mUploadTask;
    private String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public PicUtils ()
    {

    }

    //util methods
    public void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void uploadFile() {
        EditText username = findViewById(R.id.username);
        String user = username.getText().toString().trim();
        if (user.length() <= 6) {
            Toast.makeText(PicUtils.this, "Username must be longer than 6 characters", Toast.LENGTH_SHORT).show();

        }
        if (user.length() > 6) {
            newUser.setUserName(user);
            final StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            final StorageReference photoRefy = storageRef.child("user/" + uid + "/images/" + System.currentTimeMillis() + ".jpg");

            if (contentURI != null) {
                Bitmap cropped = cropImageView.getCroppedImage();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                cropped.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask mUploadTask = photoRefy.putBytes(data);


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
                            newUser.setProfileImageUrl(downloadURL);
                            db.collection("userdata").document(uid).set(newUser);
                            startActivity(new Intent(PicUtils.this, Diseaselist.class));

                        } else {

                            // Handle failures
                            // ...
                        }
                    }
                });
            } else {
                Uri uri = getURLForResource(R.drawable.nouser);

                UploadTask mUploadTask = photoRefy.putFile(uri);
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
                            newUser.setProfileImageUrl(downloadURL);
                            db.collection("userdata").document(uid).set(newUser);
                            startActivity(new Intent(PicUtils.this, Diseaselist.class));
                            //TODO: check if username has been taken already

                        } else {
                            // Handle failures
                            // ...
                        }
                    }
                });

            }

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


    public Uri getURLForResource(int resourceId) {
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + resourceId);
    }

    public void uploadtoFirebase() {

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        final StorageReference photoRefy = storageRef.child("user/" + uid + "/images/" + System.currentTimeMillis() + ".jpg");


        // add File/URI
        if (contentURI != null) {
            photoRefy.putFile(contentURI);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            contentURI = data.getData();
            cropImageView = findViewById(R.id.cropImageView);
            cropImageView.setImageUriAsync(contentURI);

            cropImageView.setAspectRatio(10, 10);
            cropImageView.setFixedAspectRatio(true);
            cropImageView.setGuidelines(CropImageView.Guidelines.ON_TOUCH);
            cropImageView.setAutoZoomEnabled(true);
            cropImageView.setCropShape(CropImageView.CropShape.RECTANGLE);
            cropImageView.setScaleType(CropImageView.ScaleType.FIT_CENTER);
        }
    }
}
