package com.example.projectapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.MyViewHolder> {
    private static Context context;
    private static List<quizmoudle> quizmoudleList;

    public QuizAdapter(Context context) {
        this.context = context;
        quizmoudleList = new ArrayList<>();
    }

    public void addQuiz(quizmoudle q){
        quizmoudleList.add(q);
        notifyDataSetChanged();
    }

    public void removeQuiz(int pos){
        quizmoudleList.remove(pos);
        notifyDataSetChanged();
    }

    public void clear(){
        quizmoudleList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QuizAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.quiz_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizAdapter.MyViewHolder holder, int position) {
        quizmoudle quizModle = quizmoudleList.get(position);

        holder.bindViews(quizModle);

    }

    @Override
    public int getItemCount() {
        return quizmoudleList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

       private Button upadteBtn;
       RecyclerView recylview;
       TextView quizname;
       ImageButton delbtn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            quizname=itemView.findViewById(R.id.quizname);
            upadteBtn=itemView.findViewById(R.id.addquiz);
            delbtn=itemView.findViewById(R.id.delete);
            recylview=itemView.findViewById(R.id.quizrecycle);
        }


        public void bindViews(quizmoudle quizModle) {

            quizname.setText("QuizName:-"+quizModle.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(context, QuestionActivity.class);
                    i.putExtra("id",quizModle.getQuizid());
                    context.startActivity(i);
                }
            });
            delbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   int k=quizmoudleList.indexOf(quizModle);
                   removeQuiz(k);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                    DatabaseReference myRef = database.getReference("quiz/"+quizModle.getQuizid());
                    myRef.removeValue();


// Get the adapter that is attached to the RecyclerView


// Clear the adapter
                    quizmoudleList.clear();
                    }

            });


        }
    }
}
