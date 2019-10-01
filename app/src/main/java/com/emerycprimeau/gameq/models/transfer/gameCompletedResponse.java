package com.emerycprimeau.gameq.models.transfer;

public class gameCompletedResponse {
    public Long gameId;
    public String date;
    public String name;
    public int score;

    public gameCompletedResponse(Long gameId, String date, String name, int score) {
        this.gameId = gameId;
        this.date = date;
        this.name = name;
        this.score = score;
    }
}
