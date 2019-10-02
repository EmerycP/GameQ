package com.emerycprimeau.gameq.http.mock;

import com.emerycprimeau.gameq.GUI.completed.Sorted;
import com.emerycprimeau.gameq.models.transfer.GameCompletedResponse;
import com.emerycprimeau.gameq.models.transfer.GameRequest;
import com.emerycprimeau.gameq.models.transfer.GameRequestEdit;
import com.emerycprimeau.gameq.models.transfer.GameResponseAdd;
import com.emerycprimeau.gameq.models.transfer.GameResponseEdit;
import com.emerycprimeau.gameq.models.transfer.GameToCompleteResponse;
import com.emerycprimeau.gameq.models.transfer.LoginRequest;
import com.emerycprimeau.gameq.models.transfer.LoginResponse;
import com.emerycprimeau.gameq.models.transfer.LogoutRequest;
import com.emerycprimeau.gameq.models.transfer.LogoutResponse;

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
    public Call<LoginResponse> toLogin(LoginRequest logR) {
        LoginResponse lR = new LoginResponse();
        lR.Id = 12;
        lR.emailCleaned = "emeryc@login.ca";
        return this.delegate.returningResponse(lR).toLogin(logR);
    }

    @Override
    public Call<LoginResponse> toSignUp(LoginRequest logR) {
        LoginResponse lR = new LoginResponse();
        lR.Id = 19;
        lR.emailCleaned = "emeryc@signup.ca";
        return this.delegate.returningResponse(lR).toLogin(logR);
    }

    @Override
    public Call<List<GameToCompleteResponse>> getToCompleteList(GameRequest gR) {
        List<GameToCompleteResponse> game = new ArrayList<>();

        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy  HH:mm");
        String date = format.format(Date.parse(Calendar.getInstance().getTime().toString()));

        game.add(new GameToCompleteResponse(1L, date, "The Witcher 3: Wild Hunt"));
        game.add(new GameToCompleteResponse(2L, date, "Spider-Man PS4"));
        game.add(new GameToCompleteResponse(3L, date, "Red Dead Redemption 2"));
        game.add(new GameToCompleteResponse(4L, date, "Crash Bandicoot N-Sane Trilogy"));
        game.add(new GameToCompleteResponse(5L, date, "Call of Duty: Modern Warfare"));
        game.add(new GameToCompleteResponse(6L, date, "Madden NFL 20"));
        game.add(new GameToCompleteResponse(7L, date, "Bordelands 3"));
        game.add(new GameToCompleteResponse(8L, date, "The Legends of Zelda: Breath of the Wild"));
        game.add(new GameToCompleteResponse(9L, date, "Cyberpunk 2077"));
        game.add(new GameToCompleteResponse(10L, date, "Dragon Quest XI"));
        game.add(new GameToCompleteResponse(11L, date, "FIFA 20"));
        game.add(new GameToCompleteResponse(12L, date, "Tetris"));
        game.add(new GameToCompleteResponse(13L, date, "Minecraft"));



        return this.delegate.returningResponse(game).getToCompleteList(gR);
    }

    @Override
    public Call<List<GameCompletedResponse>> getCompletedList(GameRequest gR) {
        List<GameCompletedResponse> game = new ArrayList<>();

        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy  HH:mm");
        String date = format.format(Date.parse(Calendar.getInstance().getTime().toString()));

        game.add(new GameCompletedResponse(75L, date, "The Last of Us Part II", 96));
        game.add(new GameCompletedResponse(76L, date, "The Surge 2", 79));
        game.add(new GameCompletedResponse(77L, date, "Doom Eternal", 80));
        game.add(new GameCompletedResponse(78L, date, "Destiny 2", 76));
        game.add(new GameCompletedResponse(79L, date, "Call of Duty: Black Ops IIII", 78));
        Collections.sort(game, new Sorted());

        return this.delegate.returningResponse(game).getCompletedList(gR);
    }

    @Override
    public Call<LogoutResponse> toLogOut(LogoutRequest logoutRequest) {
        LogoutResponse logoutresponse = new LogoutResponse();
        logoutresponse.estDeconnecte = true;
        return this.delegate.returningResponse(logoutresponse).toLogOut(logoutRequest);
    }

    @Override
    public Call<GameResponseEdit> getToEdit(int gameID) {
        GameResponseEdit game = new GameResponseEdit();
        game.gameID = 45;
        game.name = "League of Legends";
        game.score = 78;
        game.estComplete = true;

        return this.delegate.returningResponse(game).getToEdit(gameID);
    }

    @Override
    public Call<Boolean> toEdit(GameRequestEdit gameRequestEdit) {
        return  this.delegate.returningResponse(true).toEdit(gameRequestEdit);
    }

    @Override
    public Call<Boolean> toAdd(GameResponseAdd gameResponseAdd) {
        return  this.delegate.returningResponse(true).toAdd(gameResponseAdd);
    }
}
