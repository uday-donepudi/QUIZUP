package com.example.projectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText id,pass;
    TextView sign;
    Button btn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id=findViewById(R.id.userid);
        pass=findViewById(R.id.password);

        sign=findViewById(R.id.signup);
        btn=findViewById(R.id.loginButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u=id.getText().toString().trim();
                String p=pass.getText().toString().trim();
                if((u.length()!=0)&&(p.length()!=0))
                {
                    // Initialize Firebase Realtime Database
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Users data");
                    final String[] user1 = {""};
                    final String[] pass1 = {""};
                    final String[] mail1 = {""};
                    final int[] j = {0};
// Retrieve data of all users from Firebase Realtime Database
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // Data retrieved successfully
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                HashMap<String, String> userData = (HashMap<String, String>) snapshot.getValue();
                                user1[0] =userData.get("username");
                                pass1[0] =userData.get("password");
                                mail1[0] =userData.get("email");
                                if(((user1[0]+pass1[0]).equals(u+p))||((mail1[0]+pass1[0]).equals(u+p)))
                                {
                                    Intent i=new Intent(MainActivity.this,index_page.class);
                                    i.putExtra("userdata",u);
                                    startActivity(i);
                                    j[0] = j[0] +1;
                                }

                            }
                            if(j[0] ==0) {
                                Toast.makeText(MainActivity.this, "Enter valid data", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Data retrieval failed
                            Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                        }
                    });



                }
                else {
                    Toast.makeText(MainActivity.this, "Fill the details", Toast.LENGTH_SHORT).show();
                }
            }
        });


        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,signup_page.class);
                startActivity(i);
            }
        });
    }
}