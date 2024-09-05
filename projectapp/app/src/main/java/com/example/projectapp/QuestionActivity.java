package com.example.projectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.UUID;

public class QuestionActivity extends AppCompatActivity {

    EditText qus,c1,c2,c3,c4;
    Button uq;
    RadioButton r1,r2,r3,r4;

    RadioGroup rg;
    private String quizId;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        qus=findViewById(R.id.questionTitle);
        c1=findViewById(R.id.choice1);
        c2=findViewById(R.id.choice2);
        c3=findViewById(R.id.choice3);
        c4=findViewById(R.id.choice4);
        uq=findViewById(R.id.uploadQ);

        r1=findViewById(R.id.radio1);
        r2=findViewById(R.id.radio2);
        r3=findViewById(R.id.radio3);
        r4=findViewById(R.id.radio4);
        rg=findViewById(R.id.radioG);
        quizId=getIntent().getStringExtra("id");
        uq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer="";
                String questionId= UUID.randomUUID().toString();
                String question=qus.getText().toString();
                String choice1=c1.getText().toString();
                String choice2=c2.getText().toString();
                String choice3=c3.getText().toString();
                String choice4=c4.getText().toString();

                RadioButton selectedRadio = (RadioButton)findViewById(rg.getCheckedRadioButtonId());
                String selectedText=selectedRadio.getText().toString();
                if(selectedText.equals("1")){
                    answer=choice1;
                }
                if(selectedText.equals("2")){
                    answer=choice2;
                }
                if(selectedText.equals("3")){
                    answer=choice3;
                }
                if(selectedText.equals("4")){
                    answer=choice4;
                }
                QuestionModel questionModel=new QuestionModel(questionId,quizId,question,choice1,choice2,choice3,choice4,answer);

                FirebaseDatabase database = FirebaseDatabase.getInstance();

                DatabaseReference myRef = database.getReference("questions");

// Create HashMap with data
                HashMap<String, Object> userData = new HashMap<>();
                userData.put("questionId",questionModel.getQuestionId());
                userData.put("quizId",questionModel.getQuizId());
                userData.put("question",questionModel.getQuestion());
                userData.put("choice1",questionModel.getChoice1());
                userData.put("choice2",questionModel.getChoice2());
                userData.put("choice3",questionModel.getChoice3());
                userData.put("choice4",questionModel.getChoice4());
                userData.put("answer",questionModel.getCorrectOne());


// Insert data into Firebase Realtime Database and set success listener
                myRef.child(questionModel.getQuestionId()).setValue(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Data inserted successfully
                        Toast.makeText(QuestionActivity.this, "question uploaded", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Data insertion failed

                    }
                });
            }
        });
    }
}