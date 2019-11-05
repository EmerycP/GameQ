package com.emerycprimeau.gameq.models;

public class Game {
    public int ID;
    public String Name;
    public int Score;
    public boolean EstCompleter;
    public String date;

    public Game () {}

    public Game(int gameId, String date, String name, int score, boolean EstCompleter) {
        this.ID = gameId;
        this.date = date;
        this.Name = name;
        this.Score = score;
        this.EstCompleter = EstCompleter;
    }
}
