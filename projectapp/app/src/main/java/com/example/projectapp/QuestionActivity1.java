package com.example.projectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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

public class QuestionActivity1 extends AppCompatActivity {

    String quizId;
    TextView qm;
    RadioButton o1,o2,o3,o4;
    Button btn;
    RadioGroup radioGroup;
    private int position=0;
    private static String answer=null;
    private int totalQs;
    int right=0;
    int wrong=0;
    static String user;
  private List<QuestionModel> questionModellist;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question1);

        quizId=getIntent().getStringExtra("id");
        qm=findViewById(R.id.quizQuestion1);
        o1=findViewById(R.id.option1);
        o2=findViewById(R.id.option2);
        o3=findViewById(R.id.option3);
        o4=findViewById(R.id.option4);
        btn=findViewById(R.id.submit);
        radioGroup=findViewById(R.id.optionGroup);
        loadQuestions();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questionModellist!=null && answer!=null){

                    RadioButton radioButton=findViewById(radioGroup.getCheckedRadioButtonId());
                    String selectedRadioText=radioButton.getText().toString();
                    if(selectedRadioText.equals(answer)){
                        right+=1;
                        Toast.makeText(QuestionActivity1.this, "correct", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        wrong+=1;
                        Toast.makeText(QuestionActivity1.this, "Wrong", Toast.LENGTH_SHORT).show();
                    }
                    position=position+1;
                    showQsOneByone(position);
                }
            }
        });

    }

    private void loadQuestions() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("questions");
// Retrieve data of all users from Firebase Realtime Database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Data retrieved successfully
                questionModellist=new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HashMap<String, Object> userData = (HashMap<String, Object>) snapshot.getValue();
                    String qi=userData.get("quizId").toString().trim();
                    if(qi.equals(quizId)) {
                        String ans=userData.get("answer").toString().trim();
                        String c1=userData.get("choice1").toString().trim();
                        String c2=userData.get("choice2").toString().trim();
                        String c3=userData.get("choice3").toString().trim();
                        String c4=userData.get("choice4").toString().trim();
                        String qus=userData.get("question").toString().trim();
                        String qid=userData.get("questionId").toString().trim();


                        QuestionModel quizmoudle1 = new QuestionModel(qid,qi,qus,c1,c2,c3,c4,ans);
                        questionModellist.add(quizmoudle1);
                        totalQs=questionModellist.size();
                    }
                }

                showQsOneByone(position);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(QuestionActivity1.this, "hi", Toast.LENGTH_SHORT).show();
            }


        });
    }
    private void showQsOneByone(int i) {
        if (i >= 0 && i < questionModellist.size()) {
            QuestionModel questionModel = questionModellist.get(i);
            answer = questionModel.getCorrectOne();
            qm.setText(questionModel.getQuestion());
            o1.setText(questionModel.getChoice1());
            o2.setText(questionModel.getChoice2());
            o3.setText(questionModel.getChoice3());
            o4.setText(questionModel.getChoice4());
        }
        if(totalQs<=i) {
            AlertDialog.Builder builder=new AlertDialog.Builder(QuestionActivity1.this);
            builder.setTitle("Result").setMessage("You got score: "+right+"/"+(right+wrong));
            builder.setPositiveButton("ok", (dialog, which) -> {
                dialog.cancel();
                finish();
            });
            builder.create().show();


            FirebaseDatabase database = FirebaseDatabase.getInstance();

            DatabaseReference myRef = database.getReference("result");

// Create HashMap with data
            HashMap<String, Object> userData = new HashMap<>();
            userData.put("username",user);
            userData.put("quizId",quizId);
            userData.put("score",right);



// Insert data into Firebase Realtime Database and set success listener
            myRef.child(user).setValue(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // Data inserted successfully
                    Toast.makeText(QuestionActivity1.this, "question uploaded", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Data insertion failed

                }
            });
        }
    }

    public static void sendMessage(String message) {
        // Process the message here
       user=message;
    }

}