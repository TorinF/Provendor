package com.provendor.tensorflow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.provendor.R;
import com.provendor.users.ProfileList;


public class LoginActivity2 extends AppCompatActivity {


    private EditText email;

    private EditText password;

    private FirebaseAuth mAuth;

    private FirebaseUser currentUser;

    private Button button;


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login2);


        email = findViewById(R.id.login_email_input);

        password = findViewById(R.id.login_password_input);

        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();

        button = findViewById(R.id.login);


        button.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                if (v == button) {

                    LoginUser();

                }

            }

        });

    }

    public void LoginUser() {

        String Email = email.getText().toString().trim();

        String Password = password.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(Email, Password)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            currentUser = mAuth.getCurrentUser();

                            finish();

                            startActivity(new Intent(LoginActivity2.this, ProfileList.class));


                        } else {

                            Toast.makeText(LoginActivity2.this, "couldn't login",

                                    Toast.LENGTH_SHORT).show();

                        }

                    }

                });

    }

}