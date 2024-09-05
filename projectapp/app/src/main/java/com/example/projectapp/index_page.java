package com.example.projectapp;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class index_page extends AppCompatActivity {


   TextView hom2,ad1,prof;
   QuestionAdapter quizadapter;
    RecyclerView recylview1;
    String us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_page);

        hom2=findViewById(R.id.all);
        ad1=findViewById(R.id.newquiz);
        prof=findViewById(R.id.userprofile);
        quizadapter=new QuestionAdapter(this);
        recylview1=findViewById(R.id.quizRecycler);



        us = getIntent().getStringExtra("userdata");
        QuestionActivity1.sendMessage(us);
        scoreitem.sendMessage(us);

        ad1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(index_page.this, newfile.class);
                i.putExtra("hi",us);
                startActivity(i);

            }

        });

        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(index_page.this, profile_page.class);
                i.putExtra("userinput",us);
                startActivity(i);

            }
        });

        recylview1.setAdapter(quizadapter);
        recylview1.setLayoutManager(new LinearLayoutManager(this));



        loadQuiz();

    }
    private void loadQuiz() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("quiz");
// Retrieve data of all users from Firebase Realtime Database
        myRef.addValueEventListener(new ValueEventListener() {

            @Override


            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Data retrieved successfully
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HashMap<String, Object> userData = (HashMap<String, Object>) snapshot.getValue();

                        long t = (long) userData.get("duration");
                        quizmoudle quizmoudle1 = new quizmoudle((String) userData.get("quizid"), (String) userData.get("quizname"), t,userData.get("userid").toString());
                        quizadapter.addQuiz(quizmoudle1);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Data retrieval failed
                Toast.makeText(index_page.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
