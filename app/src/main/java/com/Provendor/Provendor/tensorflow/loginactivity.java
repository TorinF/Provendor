package com.Provendor.Provendor.tensorflow;

import android.app.Activity;
import android.content.Intent;

import android.os.Build;
import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.text.TextUtils;

import android.util.Log;

import android.view.View;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import android.widget.EditText;

import android.widget.TextView;

import android.widget.Toast;


import com.Provendor.Provendor.R;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;

import com.google.firebase.auth.FirebaseAuth;
import com.jaeger.library.StatusBarUtil;
import com.provendor.MainActivity;


public class loginactivity extends AppCompatActivity {



    private FirebaseAuth mAuth;

    private EditText password;

    private EditText email;

    private Button button_register;

    private Button button_login;


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);


        Window mWindow = getWindow();


        email = (EditText) findViewById(R.id.signup_email_input);

        password =(EditText) findViewById(R.id.signup_password_input);

        button_register = (Button)findViewById(R.id.button_register);

        button_login = (Button)findViewById(R.id.button_login);

        mAuth = FirebaseAuth.getInstance();





        button_register.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                if (v == button_register){

                    RegisterUser();

                }

            }

        });

        button_login.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                if (v == button_login){

                    startActivity(new Intent(loginactivity.this, LoginActivity2.class));

                }

            }

        });

    }

    public void RegisterUser(){

        String Email = email.getText().toString().trim();

        String Password = password.getText().toString().trim();

        if (TextUtils.isEmpty(Email)){

            Toast.makeText(this, "A Field is Empty", Toast.LENGTH_SHORT).show();

            return;

        }

        if (TextUtils.isEmpty(Password)){

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

                                Toast.makeText(loginactivity.this, "registration successful",

                                        Toast.LENGTH_SHORT).show();

                                finish();

                                startActivity(new Intent(loginactivity.this, MainActivity.class));


                            }else{

                                Toast.makeText(loginactivity.this, "Couldn't register, try again",

                                        Toast.LENGTH_SHORT).show();

                            }

                        }catch (Exception e){

                            e.printStackTrace();

                        }

                    }

                });

    }

}