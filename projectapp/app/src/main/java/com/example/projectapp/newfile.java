package com.example.projectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class newfile extends AppCompatActivity {
    Button btn;
    EditText name1;
    QuizAdapter quizAdapter;
    RecyclerView recylview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newfile);

        btn=findViewById(R.id.addquiz);
        name1=findViewById(R.id.quizname);
        quizAdapter=new QuizAdapter(this);
        recylview=findViewById(R.id.quizrecycle);
        String userid=getIntent().getStringExtra("hi");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id= UUID.randomUUID().toString();
                String name2=name1.getText().toString();
                quizmoudle quizmoudle1 = new quizmoudle(id, name2, 0, userid);
                uploadquiz(quizmoudle1);
                // Get a reference to the Recycl

                // Get a reference to the RecyclerView


// Set the item animator to null
                recylview.setItemAnimator(null);

// Get the adapter that is attached to the RecyclerView
                QuizAdapter adapter = (QuizAdapter) recylview.getAdapter();

// Clear the adapter
                if (adapter != null) {
                    adapter.clear();
                }





            }
        });
        recylview.setAdapter(quizAdapter);
        recylview.setLayoutManager(new LinearLayoutManager(this));


        loadQuiz();
    }

    private void loadQuiz() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("quiz");
// Retrieve data of all users from Firebase Realtime Database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userid=getIntent().getStringExtra("hi");
                // Data retrieved successfully
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HashMap<String, Object> userData = (HashMap<String, Object>) snapshot.getValue();
                    if(userid.equals(userData.get("userid").toString())) {
                        long t = (long) userData.get("duration");
                        quizmoudle quizmoudle1 = new quizmoudle((String) userData.get("quizid"), (String) userData.get("quizname"), t, userData.get("userid").toString());
                        quizAdapter.addQuiz(quizmoudle1);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Data retrieval failed
                Toast.makeText(newfile.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadquiz(quizmoudle quizmoudle1) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("quiz");

// Create HashMap with data
        HashMap<String, Object> userData = new HashMap<>();
        userData.put("quizname",quizmoudle1.getName());
        userData.put("quizid",quizmoudle1.getQuizid());
        userData.put("duration",quizmoudle1.getDuration());
        userData.put("userid",quizmoudle1.getUserid());


// Insert data into Firebase Realtime Database and set success listener
        myRef.child(quizmoudle1.getQuizid()).setValue(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Data inserted successfully

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Data insertion failed

            }
        });


    }
    @Override
    public void onBackPressed() {
        // Your code to handle the back button click
        // ...
        RecyclerView rect=findViewById(R.id.quizRecycler);
        rect.removeAllViews();
        // Call super.onBackPressed() to allow the default back button behavior
        super.onBackPressed();
    }

}