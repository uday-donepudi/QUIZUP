package com.example.projectapp;

public class ScoreType {
    private String userName;
    private String score;

    public ScoreType(String userName, String score) {
        this.userName = userName;
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public String getScore() {
        return score;
    }
}

