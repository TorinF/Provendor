package com.provendor.tensorflow;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.provendor.R;
import com.provendor.users.Inbox;
import com.provendor.users.Profile;
import com.provendor.users.ProfileClass;

import java.util.HashMap;


public class loginactivity extends AppCompatActivity {


    public static ProfileClass newUser;
    private FirebaseAuth mAuth;
    private EditText password;
    private EditText email;
    private Button button_register;
    private Button button_login;
    private FirebaseFirestore db;

    private String uid;


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);


        Window mWindow = getWindow();


        email = findViewById(R.id.signup_email_input);

        password = findViewById(R.id.signup_password_input);

        button_register = findViewById(R.id.button_register);

        button_login = findViewById(R.id.button_login);

        mAuth = FirebaseAuth.getInstance();


        button_register.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                if (v == button_register) {

                    RegisterUser();

                }

            }

        });

        button_login.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                if (v == button_login) {

                    startActivity(new Intent(loginactivity.this, LoginActivity2.class));

                }

            }

        });

    }

    public void RegisterUser() {

        String Email = email.getText().toString().trim();

        String Password = password.getText().toString().trim();

        if (TextUtils.isEmpty(Email)) {

            Toast.makeText(this, "A Field is Empty", Toast.LENGTH_SHORT).show();

            return;

        }

        if (TextUtils.isEmpty(Password)) {

            Toast.makeText(this, "A Field is Empty", Toast.LENGTH_SHORT).show();

            return;

        }

        mAuth.createUserWithEmailAndPassword(Email, Password)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {

                        try {

                            //check if successful

                            if (task.isSuccessful()) {

                                //User is successfully registered and logged in

                                //start Profile Activity here
                                db = FirebaseFirestore.getInstance();
                                FirebaseUser user = mAuth.getCurrentUser();
                                uid = user.getUid();
                                newUser = new ProfileClass(uid, 0, 0, "", null, 0, 0, 0, 1, 0, new HashMap<String, Object>());
                                Inbox newInbox = new Inbox();
                                FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification();

                                db.collection("userdata").document(uid).collection("notifications").document("notifications").set(newInbox);

                                Toast.makeText(loginactivity.this, "registration successful",

                                        Toast.LENGTH_SHORT).show();


                                finish();

                                startActivity(new Intent(loginactivity.this, Profile.class));


                            } else {

                                Toast.makeText(loginactivity.this, "Couldn't register, try again",

                                        Toast.LENGTH_SHORT).show();

                            }

                        } catch (Exception e) {

                            e.printStackTrace();

                        }

                    }

                });

    }

}