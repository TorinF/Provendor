package com.Provendor.Provendor

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle

import com.google.firebase.firestore.CollectionReference

import com.google.firebase.firestore.DocumentChange

import com.google.firebase.firestore.DocumentSnapshot

import com.google.firebase.firestore.EventListener

import com.google.firebase.firestore.FieldPath

import com.google.firebase.firestore.FieldValue

import com.google.firebase.firestore.FirebaseFirestore

import com.google.firebase.firestore.FirebaseFirestoreException

import com.google.firebase.firestore.FirebaseFirestoreSettings

import com.google.firebase.firestore.MetadataChanges

import com.google.firebase.firestore.Query

import com.google.firebase.firestore.QuerySnapshot

import com.google.firebase.firestore.ServerTimestamp

import com.google.firebase.firestore.SetOptions

import com.google.firebase.firestore.Source
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.util.Log
import android.view.View

import com.google.android.gms.tasks.Continuation

import com.google.android.gms.tasks.OnFailureListener

import com.google.android.gms.tasks.Task

import com.google.firebase.FirebaseApp

import com.google.firebase.storage.FirebaseStorage

import com.google.firebase.storage.StorageException

import com.google.firebase.storage.StorageMetadata

import com.google.firebase.storage.StorageReference

import com.google.firebase.storage.UploadTask
import android.view.Window
import android.view.WindowManager
import com.Provendor.Provendor.R
import android.widget.Toast;
import com.Provendor.Provendor.Diagnosis
import com.Provendor.Provendor.R.id.cameraView
import com.Provendor.Provendor.Upload
import com.Provendor.Provendor.tensorflow.Classifier
import com.Provendor.Provendor.tensorflow.TensorFlowImageClassifier
import com.wonderkiln.camerakit.CameraKitImage
import kotlinx.android.synthetic.main.activity_main.*

import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserInfo
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var storage: FirebaseStorage
        private val mImageUri: Uri? = null
        private var  downloadUri:Uri? = null
        private var mStorageRef: StorageReference? = null
        private var mDatabaseRef: DatabaseReference? = null
        val uploady = Upload()
         var imageurl =""
        val db = FirebaseFirestore.getInstance()
        private var time = ""

        private val mUploadTask: StorageTask<*>? = null
        private var Useruid= ""
        private const val TAG = "MainActivity"
        private const val INPUT_WIDTH = 300
        private const val INPUT_HEIGHT = 300
        private const val IMAGE_MEAN = 128
        private const val IMAGE_STD = 128f
        private const val INPUT_NAME = "Mul"
        private const val OUTPUT_NAME = "final_result"
        private const val MODEL_FILE = "file:///android_asset/hero_stripped_graph.pb"
        private const val LABEL_FILE = "file:///android_asset/hero_labels.txt"
    }

    private var classifier: Classifier? = null
    private var initializeJob: Job? = null
    private lateinit var job: Job


    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }
    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }
    private fun includesForUploadFiles(bitmap: Bitmap?) {

        val storage = FirebaseStorage.getInstance()



        // [START upload_create_reference]

        // Create a storage reference from our app

        val storageRef = storage.reference


        val user = FirebaseAuth.getInstance().currentUser!!.uid// Create a Storage Ref w/ username
// Create a Storage Ref w/ username
        val storageRefmeme = storageRef.child(user + "/profilePicture/"+System.currentTimeMillis()+".jpg");

        // Create a reference to "mountains.jpg"

        val mountainsRef = storageRef.child("" +System.currentTimeMillis()+".jpg")



        // Create a reference to 'images/mountains.jpg'

        val mountainImagesRef = storageRef.child("user/"+user + "/images/"+System.currentTimeMillis()+".jpg")



        // While the file names are the same, the references point to different files

        mountainsRef.name == mountainImagesRef.name    // true

        mountainsRef.path == mountainImagesRef.path    // false

        // [END upload_create_reference]



        // [START upload_memory]

        // Get the data from an ImageView as bytes





        val baos = ByteArrayOutputStream()

        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        }

        val data = baos.toByteArray()



        var uploadTask = mountainsRef.putBytes(data)



        val urlTask = uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            return@Continuation mountainsRef.downloadUrl
        }).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                 downloadUri = task.result
            } else {
                // Handle failures
                // ...
            }
        }


        // taskSnapshot.metadata contains file metadata such as size, content-type, etc.

            // ...




        // [END upload_memory]




        // [START upload_with_metadata]

        // Create file metadata including the content type





        // Upload the file and metadata





        // Pause the upload

      /*  uploadTask.pause()



        // Resume the upload

        uploadTask.resume()



        // Cancel the upload

        uploadTask.cancel()

        // [END manage_uploads]



        // [START monitor_upload_progress]

        // Observe state change events such as progress, pause, and resume

        uploadTask.addOnProgressListener { taskSnapshot ->

            val progress = (100.0 * taskSnapshot.bytesTransferred) / taskSnapshot.totalByteCount

            System.out.println("Upload is $progress% done")

        }.addOnPausedListener {

            System.out.println("Upload is paused")

        }

        // [END monitor_upload_progress]



        // [START upload_complete_example]

        // File or Blob





        // Create the file metadata




        // Listen for state changes, errors, and completion of the upload.

        uploadTask.addOnProgressListener { taskSnapshot ->

            val progress = (100.0 * taskSnapshot.bytesTransferred) / taskSnapshot.totalByteCount

            System.out.println("Upload is $progress% done")

        }.addOnPausedListener {

            System.out.println("Upload is paused")

        }.addOnFailureListener {

            // Handle unsuccessful uploads

        }.addOnSuccessListener {

            // Handle successful uploads on complete

            // ...

        }

        // [END upload_complete_example]



        // [START upload_get_download_url]





*/


    }
    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY

            // Enables regular immersive mode.
            // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
            // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                    // Set the content to appear under the system bars so that the
                    // content doesn't resize when the system bars hide and show.
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    // Hide the nav bar and status bar
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)



    }


    private fun makeinvisible(){
        cameraView.visibility =View.GONE
        buttonRecognize.visibility = View.GONE
        getWindow().getDecorView().setBackgroundColor(Color.BLACK)
        progressBar.visibility = View.VISIBLE
    }

    private fun makevisible(){
        cameraView.visibility =View.VISIBLE
        progressBar.visibility = View.GONE
        buttonRecognize.visibility =View.VISIBLE
        buttonRecognize.isEnabled = true ///make main activity start right here (this represents when loading is finished

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.Provendor.Provendor.R.layout.activity_main)

        job = Job()

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads")
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads")

        makeinvisible()

        initializeTensorClassifier()
        buttonRecognize.setOnClickListener {
            setVisibilityOnCaptured(false)
            cameraView.captureImage {
                onImageCaptured(it)

            }
        }
        imageclose.setOnClickListener {
            imageCaptured.visibility = View.GONE ///After button is clicked, before it is done processing
            textResult.visibility = View.GONE
            textResult2.visibility = View.GONE
            cameraView.visibility =View.VISIBLE
            buttonRecognize.visibility =View.VISIBLE
            progressBar.visibility = View.GONE
            imageclose.visibility = View.GONE
            }
        }


    private fun onImageCaptured(it: CameraKitImage) {
        val bitmap = Bitmap.createScaledBitmap(it.bitmap, INPUT_WIDTH, INPUT_HEIGHT, false)
        showCapturedImage(bitmap)
        classifier?.let {
            try {
                showRecognizedResult(it.recognizeImage(bitmap))
            } catch (e: java.lang.RuntimeException) {
                Log.e(TAG, "Crashing due to classification.closed() before the recognizer finishes!")
            }
        }
    }

    fun getImageData(bmp: Bitmap) {

        val bao = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, bao) // bmp is bitmap from user image file
        bmp.recycle()
        val byteArray = bao.toByteArray()
        val imageB64 = Base64.encodeToString(byteArray, Base64.DEFAULT)
        //  store & retrieve this string to firebase
    }
    private fun showRecognizedResult(results: MutableList<Classifier.Recognition>) {
        runOnUiThread {
            setVisibilityOnCaptured(true)
            if (results.isEmpty()) {
                textResult.text = getString(com.Provendor.Provendor.R.string.result_no_name)
                textResult2.text = getString(com.Provendor.Provendor.R.string.result_no_hero_found)
                uploady.setDisease("None")
                uploady.setConfidence(0.0f)
            } else {
                val hero = results[0].title
                val confidence = results[0].confidence
                if (confidence > .7) {
                    uploady.setDisease(hero)
                    uploady.setConfidence(confidence)
                } else {
                    uploady.setDisease("None")
                    uploady.setConfidence(0.0f)
                }
                textResult.text = when {
                    ((confidence > .7)) -> getString(com.Provendor.Provendor.R.string.Name, hero)


                    else -> getString(R.string.result_no_name)
                }
                textResult2.text = when {
                    ((confidence > .7)) -> getString(com.Provendor.Provendor.R.string.result_maybe_hero_found, hero)


                    else -> getString(R.string.result_no_hero_found)
                }

            }

        }
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {

            user?.let {
                Useruid = user.uid
            }
            time = "" + System.currentTimeMillis()
            db.collection("users").document(Useruid).collection("diagnoses").document(time).set(uploady)

        }

    }

    private fun showCapturedImage(bitmap: Bitmap?) {
        val storage = FirebaseStorage.getInstance()


        val currentFirebaseUser = FirebaseAuth.getInstance().currentUser


        // [START upload_create_reference]

        // Create a storage reference from our app

        val storageRef = storage.reference

        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {

            user?.let {
                Useruid = user.uid
            }

            val mountainsRef = storageRef.child("user/"+ Useruid +"/images/" +System.currentTimeMillis()+".jpg")



            // Create a reference to 'images/mountains.jpg'

            val mountainImagesRef = storageRef.child(""+System.currentTimeMillis()+".jpg");



            // While the file names are the same, the references point to different files

            mountainsRef.name == mountainImagesRef.name    // true

            mountainsRef.path == mountainImagesRef.path    // false

            // [END upload_create_reference]



            // [START upload_memory]

            // Get the data from an ImageView as bytes





            val baos = ByteArrayOutputStream()

            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            }

            val data = baos.toByteArray()



            var uploadTask = mountainsRef.putBytes(data)



            val urlTask = uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation mountainsRef.downloadUrl
            }).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    downloadUri = task.result
                    imageurl= downloadUri.toString()
                    uploady.setImageUrl(downloadUri.toString())
                    uploady.setdate()

                } else {
                    // Handle failures
                    // ...
                }
            }


            // User is signed in
        }


// Create a Storage Ref w/ username

        // Create a reference to "mountains.jpg"

        // taskSnapshot.metadata contains file metadata such as size, content-type, etc.

        // ...




        // [END upload_memory]




        // [START upload_with_metadata]

        // Create file metadata including the content type





        // Upload the file and metadata





        // Pause the upload

        /*  uploadTask.pause()



          // Resume the upload

          uploadTask.resume()



          // Cancel the upload

          uploadTask.cancel()

          // [END manage_uploads]



          // [START monitor_upload_progress]

          // Observe state change events such as progress, pause, and resume

          uploadTask.addOnProgressListener { taskSnapshot ->

              val progress = (100.0 * taskSnapshot.bytesTransferred) / taskSnapshot.totalByteCount

              System.out.println("Upload is $progress% done")

          }.addOnPausedListener {

              System.out.println("Upload is paused")

          }

          // [END monitor_upload_progress]



          // [START upload_complete_example]

          // File or Blob





          // Create the file metadata




          // Listen for state changes, errors, and completion of the upload.

          uploadTask.addOnProgressListener { taskSnapshot ->

              val progress = (100.0 * taskSnapshot.bytesTransferred) / taskSnapshot.totalByteCount

              System.out.println("Upload is $progress% done")

          }.addOnPausedListener {

              System.out.println("Upload is paused")

          }.addOnFailureListener {

              // Handle unsuccessful uploads

          }.addOnSuccessListener {

              // Handle successful uploads on complete

              // ...

          }

          // [END upload_complete_example]



          // [START upload_get_download_url]





  */


        runOnUiThread {
            imageCaptured.setImageBitmap(bitmap)

        }
    }



    private fun setVisibilityOnCaptured(isDone: Boolean) {
        buttonRecognize.isEnabled = isDone
        if (isDone) { ///After processing is done
            progressBar.visibility = View.GONE
            imageCaptured.visibility = View.VISIBLE
            textResult.visibility = View.VISIBLE
            textResult2.visibility = View.VISIBLE
            buttonRecognize.visibility =View.GONE
            cameraView.visibility =View.GONE
            imageclose.visibility=View.VISIBLE
        } else {
            imageCaptured.visibility = View.GONE ///After button is clicked, before it is done processing
            textResult.visibility = View.GONE
            textResult2.visibility = View.GONE
            cameraView.visibility =View.GONE
            buttonRecognize.visibility =View.GONE
            progressBar.visibility = View.VISIBLE
            imageclose.visibility=View.GONE
        }
    }

    private fun initializeTensorClassifier() {
       GlobalScope.launch {
            try {
                classifier = TensorFlowImageClassifier.create(
                        assets, MODEL_FILE, LABEL_FILE, INPUT_WIDTH, INPUT_HEIGHT,
                        IMAGE_MEAN, IMAGE_STD, INPUT_NAME, OUTPUT_NAME)

                runOnUiThread {
                    makevisible()
                }
            } catch (e: Exception) {
                throw RuntimeException("Error initializing TensorFlow!", e)
            }
        }
    }
     val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    private fun clearTensorClassifier() {
        initializeJob?.cancel()
        classifier?.close()
    }

    override fun onResume() {
        super.onResume()
        cameraView.start()
    }

    override fun onPause() {
        super.onPause()
        cameraView.stop()
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()

        clearTensorClassifier()
    }

}
