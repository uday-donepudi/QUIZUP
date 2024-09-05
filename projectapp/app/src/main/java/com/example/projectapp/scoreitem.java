package com.example.projectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class scoreitem extends AppCompatActivity {

    RecyclerView recyclerView;
    ScoreAdapter scoreAdapter;
    String qid;
    static String name;

    public static void sendMessage(String us) {
        name=us;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreitem);

        qid=getIntent().getStringExtra("qid");

        scoreAdapter = new ScoreAdapter(this);
        recyclerView=findViewById(R.id.recyclerViewscore);

        recyclerView.setAdapter(scoreAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loaddata();
    }

    private void loaddata() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("result");
// Retrieve data of all users from Firebase Realtime Database

        myRef.addValueEventListener(new ValueEventListener() {

            @Override


            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Data retrieved successfully
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HashMap<String, Object> userData = (HashMap<String, Object>) snapshot.getValue();
                    String quizid=userData.get("quizId").toString();
                    if(quizid.equals(qid)){
                        String uname=userData.get("username").toString();
                        String sc=userData.get("score").toString();
                        ScoreType scoreType=new ScoreType(uname,sc);
                        scoreAdapter.addScore(scoreType);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Data retrieval failed
                Toast.makeText(scoreitem.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}