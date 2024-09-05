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

public class profile_page extends AppCompatActivity {

    TextView user,mail,counting;
    Button btn;
    QuizAdapter quizAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

       user=findViewById(R.id.user);
       quizAdapter=new QuizAdapter(this);
       mail=findViewById(R.id.mail);
       btn=findViewById(R.id.logout);
       counting=findViewById(R.id.cont);


        String username=getIntent().getStringExtra("userinput");


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(profile_page.this,MainActivity.class);
                startActivity(i);
                Intent restartIntent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                restartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(restartIntent);

            }
        });

            // Initialize Firebase Realtime Database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Users data");
        final String[] user1 = new String[1];
        final String[] mail1 = new String[1];
            final int[] j = {0};
// Retrieve data of all users from Firebase Realtime Database
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Data retrieved successfully
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        HashMap<String, String> userData = (HashMap<String, String>) snapshot.getValue();
                        user1[0] =userData.get("username").toString();
                        mail1[0] =userData.get("email").toString();
                        if(user1[0].equals(username))
                        {
                            user.setText("username     : "+user1[0]);
                            mail.setText("Email Id         : "+mail1[0]);
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Data retrieval failed
                    Toast.makeText(profile_page.this, "error", Toast.LENGTH_SHORT).show();
                }
            });





            FirebaseDatabase database1 = FirebaseDatabase.getInstance();
            DatabaseReference myRef1 = database1.getReference("quiz");
// Retrieve data of all users from Firebase Realtime Database
            myRef1.addValueEventListener(new ValueEventListener() {
                int count=0;
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String userid=getIntent().getStringExtra("hi");
                    // Data retrieved successfully
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        HashMap<String, Object> userData = (HashMap<String, Object>) snapshot.getValue();
                        if(username.equals(userData.get("userid").toString())) {
                            long t = (long) userData.get("duration");
                            quizmoudle quizmoudle1 = new quizmoudle((String) userData.get("quizid"), (String) userData.get("quizname"), t, userData.get("userid").toString());
                            quizAdapter.addQuiz(quizmoudle1);
                            count+=1;
                        }

                    }
                    counting.setText("No of Quiz's  : "+count);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Data retrieval failed
                    Toast.makeText(profile_page.this, "error", Toast.LENGTH_SHORT).show();
                }
            });



    }
}