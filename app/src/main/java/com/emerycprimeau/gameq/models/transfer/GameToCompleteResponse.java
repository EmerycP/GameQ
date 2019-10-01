package com.emerycprimeau.gameq.models.transfer;

public class GameToCompleteResponse {
    public Long gameId;
    public String date;
    public String name;

    public GameToCompleteResponse(Long gameId, String date, String name) {
        this.gameId = gameId;
        this.date = date;
        this.name = name;
    }
}
