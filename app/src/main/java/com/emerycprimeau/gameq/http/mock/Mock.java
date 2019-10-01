package com.emerycprimeau.gameq.http.mock;

import com.emerycprimeau.gameq.GUI.completed.sorted;
import com.emerycprimeau.gameq.models.transfer.gameCompletedResponse;
import com.emerycprimeau.gameq.models.transfer.gameRequest;
import com.emerycprimeau.gameq.models.transfer.gameToCompleteResponse;
import com.emerycprimeau.gameq.models.transfer.loginRequest;
import com.emerycprimeau.gameq.models.transfer.loginResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

public class Mock implements ServiceMock {

    private BehaviorDelegate<ServiceMock> delegate;
    public Mock(BehaviorDelegate<ServiceMock> delegate)
    {
        this.delegate = delegate;
    }


    @Override
    public Call<loginResponse> toLogin(loginRequest logR) {
        loginResponse lR = new loginResponse();
        lR.Id = 12;
        lR.emailCleaned = "emeryc@login.ca";
        return this.delegate.returningResponse(lR).toLogin(logR);
    }

    @Override
    public Call<loginResponse> toSignUp(loginRequest logR) {
        loginResponse lR = new loginResponse();
        lR.Id = 19;
        lR.emailCleaned = "emeryc@signup.ca";
        return this.delegate.returningResponse(lR).toLogin(logR);
    }

    @Override
    public Call<List<gameToCompleteResponse>> getToCompleteList(gameRequest gR) {
        List<gameToCompleteResponse> game = new ArrayList<>();

        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy  HH:mm");
        String date = format.format(Date.parse(Calendar.getInstance().getTime().toString()));

        game.add(new gameToCompleteResponse(1L, date, "The Witcher 3: Wild Hunt"));
        game.add(new gameToCompleteResponse(2L, date, "Spider-Man PS4"));
        game.add(new gameToCompleteResponse(3L, date, "Red Dead Redemption 2"));
        game.add(new gameToCompleteResponse(4L, date, "Crash Bandicoot N-Sane Trilogy"));
        game.add(new gameToCompleteResponse(5L, date, "Call of Duty: Modern Warfare"));
        game.add(new gameToCompleteResponse(6L, date, "Madden NFL 20"));
        game.add(new gameToCompleteResponse(7L, date, "Bordelands 3"));
        game.add(new gameToCompleteResponse(8L, date, "The Legends of Zelda: Breath of the Wild"));
        game.add(new gameToCompleteResponse(9L, date, "Cyberpunk 2077"));
        game.add(new gameToCompleteResponse(10L, date, "Dragon Quest XI"));
        game.add(new gameToCompleteResponse(11L, date, "FIFA 20"));
        game.add(new gameToCompleteResponse(12L, date, "Tetris"));
        game.add(new gameToCompleteResponse(13L, date, "Minecraft"));



        return this.delegate.returningResponse(game).getToCompleteList(gR);
    }

    @Override
    public Call<List<gameCompletedResponse>> getCompletedList(gameRequest gR) {
        List<gameCompletedResponse> game = new ArrayList<>();

        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy  HH:mm");
        String date = format.format(Date.parse(Calendar.getInstance().getTime().toString()));

        game.add(new gameCompletedResponse(75L, date, "The Last of Us Part II", 96));
        game.add(new gameCompletedResponse(76L, date, "The Surge 2", 79));
        game.add(new gameCompletedResponse(77L, date, "Doom Eternal", 80));
        game.add(new gameCompletedResponse(78L, date, "Destiny 2", 76));
        game.add(new gameCompletedResponse(79L, date, "Call of Duty: Black Ops IIII", 78));
        Collections.sort(game, new sorted());

        return this.delegate.returningResponse(game).getCompletedList(gR);
    }


}
