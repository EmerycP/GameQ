package com.emerycprimeau.gameq.models.transfer;

public class gameToCompleteResponse  {
    public Long gameId;
    public String date;
    public String name;

    public gameToCompleteResponse(Long gameId, String date, String name) {
        this.gameId = gameId;
        this.date = date;
        this.name = name;
    }
}
