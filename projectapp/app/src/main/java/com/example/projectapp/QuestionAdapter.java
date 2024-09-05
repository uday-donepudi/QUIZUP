package com.example.projectapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {
    static Context context;
    private List<quizmoudle> quizmoudleList;

    public QuestionAdapter(Context context) {
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
    public QuestionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_quiz_view1, parent, false);
        return new QuestionAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.MyViewHolder holder, int position) {
        quizmoudle quizModle = quizmoudleList.get(position);
        holder.bindViews(quizModle);

    }

    @Override
    public int getItemCount() {
        return quizmoudleList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView quizname;

        RecyclerView recylview;
        ImageButton imageButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            quizname=itemView.findViewById(R.id.quizname);
            recylview=itemView.findViewById(R.id.quizRecycler);
            imageButton=itemView.findViewById(R.id.scoreid);

        }

        public void bindViews(quizmoudle quizModle) {

            quizname.setText(quizModle.getName());
            itemView.setOnClickListener(v -> {
                Intent i=new Intent(context, QuestionActivity1.class);
                i.putExtra("id",quizModle.getQuizid());
                context.startActivity(i);
            });
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(context,scoreitem.class);
                    i.putExtra("qid",quizModle.getQuizid());
                    context.startActivity(i);
                }
            });

        }
    }
}
