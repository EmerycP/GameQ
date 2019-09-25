package com.emerycprimeau.gameq.models;

public class gameCompleted {
    public int gameId;
    public String date;
    public String name;
    public int score;
    public gameCompleted(String pDate, String pName, int pScore){
        date = pDate;
        name = pName;
        score = pScore;
    }
}
