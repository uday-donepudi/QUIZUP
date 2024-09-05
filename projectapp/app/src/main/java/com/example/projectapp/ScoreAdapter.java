package com.example.projectapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {

    private List<ScoreType> scoreItems;
    public Context context;

    public ScoreAdapter(Context context) {
        this.context=context;
        scoreItems=new ArrayList<>();
    }
    public void addScore(ScoreType scoreType){
        scoreItems.add(scoreType);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scoreview, parent, false);
        return new ScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        ScoreType scoreItem = scoreItems.get(position);
        holder.userNameTextView.setText(scoreItem.getUserName());
        holder.scoreTextView.setText(scoreItem.getScore());
    }

    @Override
    public int getItemCount() {
        return scoreItems.size();
    }

    public class ScoreViewHolder extends RecyclerView.ViewHolder {
        TextView userNameTextView;
        TextView scoreTextView;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTextView = itemView.findViewById(R.id.userNameTextView);
            scoreTextView = itemView.findViewById(R.id.scoreid);
        }

    }
}

