package com.provendor.ui.main;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.provendor.R;
import com.provendor.ui.main.Items.XPBoost;
import com.provendor.users.ProfileClass;

import java.util.HashMap;

public class Store extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            DocumentReference docRef = db.collection("userdata").document(currentUser.getUid());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    final ProfileClass profile = documentSnapshot.toObject(ProfileClass.class);

                    HashMap<String,Object> inventory = new HashMap<>();
                    int gold = 0;

                    try {
                        inventory = profile.getInventory();
                        gold = profile.getGold();
                    }
                    catch (NullPointerException nullptr) {
                        Toast.makeText(Store.this, "Error: Null value detected.",
                                Toast.LENGTH_LONG).show();
                    }

                    //set amount of gold display
                    TextView goldAmt = findViewById(R.id.GoldLabel);
                    goldAmt.setText("Gold: " + gold);

                    //item buttons
                    ImageView XPBoostButton = findViewById(R.id.XPBoostButton);
                    ImageView ChangeUserButton = findViewById(R.id.ChangeUserButton);
                    ImageView ChangePicButton = findViewById(R.id.ChangePicButton);

                    //item costs
                    final int XPBOOSTCOST = 200;
                    final int USERCOST = 100;
                    final int PICCOST = 100;

                    XPBoostButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            if (profile.getGold() > XPBOOSTCOST)
                            {
                                if (profile.getFromInventory("XPBoost") != null)
                                {
                                    ((XPBoost)profile.getFromInventory("XPBoost")).increaseBoost();
                                }
                                else
                                {
                                    profile.addToInventory("XPBoost", new XPBoost());
                                }

                                profile.setGold(profile.getGold() - XPBOOSTCOST);
                            }
                            else
                            {
                                Toast.makeText(Store.this, "You don't have enough gold to buy this item.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    ChangeUserButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            if (profile.getGold() > USERCOST)
                            {
                                final AlertDialog.Builder cancelDialog = new AlertDialog.Builder(Store.this);
                                View view = getLayoutInflater().inflate(R.layout.changeusername_popup, null);

                                BootstrapButton changeButton = view.findViewById(R.id.ChangeButton);
                                BootstrapButton cancelButton = view.findViewById(R.id.CancelButton);
                                final EditText usernameInput = view.findViewById(R.id.UsernameInput);

                                final AlertDialog dialog = cancelDialog.create();

                                dialog.setView(view);
                                dialog.show();

                                changeButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();

                                        profile.setUserName("" + usernameInput.getText());

                                        profile.setGold(profile.getGold() - USERCOST);
                                    }
                                });

                                cancelButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });
                            }
                            else
                            {
                                Toast.makeText(Store.this, "You don't have enough gold to buy this item.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    ChangePicButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            if (profile.getGold() > PICCOST)
                            {
                                final AlertDialog.Builder cancelDialog = new AlertDialog.Builder(Store.this);
                                View view = getLayoutInflater().inflate(R.layout.changeusername_popup, null);

                                BootstrapButton changeButton = view.findViewById(R.id.ChangeButton);
                                BootstrapButton cancelButton = view.findViewById(R.id.CancelButton);
                                ImageButton picSelect = view.findViewById(R.id.PicSelect);

                                final AlertDialog dialog = cancelDialog.create();

                                dialog.setView(view);
                                dialog.show();

                                picSelect.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();



                                        profile.setGold(profile.getGold() - PICCOST);
                                    }
                                });

                                changeButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();

                                        //let user choose picture from camera roll
                                        //https://stackoverflow.com/questions/31218928/how-can-i-access-the-camera-roll-photos-on-android
                                        /*
                                            public void pickUser(View view) {
                                                Intent intent;

                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                                    intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                                                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                                                } else {
                                                    intent = new Intent(Intent.ACTION_GET_CONTENT);
                                                }

                                                intent.setType("image/*");
                                                startActivityForResult(intent, 3645);
                                            }
                                         */

                                        profile.setGold(profile.getGold() - PICCOST);
                                    }
                                });

                                cancelButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });
                            }
                            else
                            {
                                Toast.makeText(Store.this, "You don't have enough gold to buy this item.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        } else {
            //send to sign-in screen
        }
    }
}