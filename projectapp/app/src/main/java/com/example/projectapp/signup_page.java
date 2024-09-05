package com.example.projectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class signup_page extends AppCompatActivity {

    EditText uid,eid,pass1,pass2;
    Button b;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        uid=findViewById(R.id.userid);
        eid=findViewById(R.id.emailid);
        pass1=findViewById(R.id.password);
        pass2=findViewById(R.id.password2);
        b=findViewById(R.id.logout);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=uid.getText().toString().trim();
                String mail=eid.getText().toString().trim();
                String p1=pass1.getText().toString().trim();
                String p2=pass2.getText().toString().trim();
                if(user.length()!=0&&mail.length()!=0&&p1.length()!=0&&p2.length()!=0)
                {
                    if(p1.equals(p2))
                    {


                        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users data");
                        usersRef.orderByChild("username").equalTo(user).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    // User exists, print a message
                                    Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_SHORT).show();
                                } else {
                                    // User does not exist, continue with registration
                                    // Initialize Firebase Realtime Database
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                                    DatabaseReference myRef = database.getReference("Users data");

// Create HashMap with data
                                    HashMap<String, Object> userData = new HashMap<>();
                                    userData.put("username",user);
                                    userData.put("email", mail);
                                    userData.put("password",p1);


// Insert data into Firebase Realtime Database and set success listener
                                    myRef.child(user).setValue(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // Data inserted successfully
                                            Toast.makeText(signup_page.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                            Intent i=new Intent(signup_page.this,MainActivity.class);
                                            startActivity(i);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Data insertion failed
                                            Toast.makeText(signup_page.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                            Toast.makeText(signup_page.this, "Try again", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // Handle error
                            }
                        });





                    }
                    else {
                        Toast.makeText(signup_page.this, "password do not match", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(signup_page.this, "Fill all details", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}