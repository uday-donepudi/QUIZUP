package com.example.projectapp;

public class quizmoudle
{
    private String quizid;
    private String name;
    private long duration;
    private String userid;



    public quizmoudle(String quizid, String name, long duration, String userid) {
        this.quizid = quizid;
        this.name = name;
        this.duration = duration;
        this.userid=userid;
    }

    public String getQuizid() {
        return quizid;
    }

    public void setQuizid(String quizid) {
        this.quizid = quizid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
