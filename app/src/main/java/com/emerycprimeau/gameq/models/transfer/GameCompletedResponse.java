package com.emerycprimeau.gameq.models.transfer;

public class GameCompletedResponse {
    public Long gameId;
    public String date;
    public String name;
    public int score;

    public GameCompletedResponse(Long gameId, String date, String name, int score) {
        this.gameId = gameId;
        this.date = date;
        this.name = name;
        this.score = score;
    }
}
