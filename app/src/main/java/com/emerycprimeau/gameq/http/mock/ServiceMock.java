package com.emerycprimeau.gameq.http.mock;

import com.emerycprimeau.gameq.models.transfer.GameCompletedResponse;
import com.emerycprimeau.gameq.models.transfer.GameRequest;
import com.emerycprimeau.gameq.models.transfer.GameToCompleteResponse;
import com.emerycprimeau.gameq.models.transfer.LoginRequest;
import com.emerycprimeau.gameq.models.transfer.LoginResponse;
import com.emerycprimeau.gameq.models.transfer.LogoutRequest;
import com.emerycprimeau.gameq.models.transfer.LogoutResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServiceMock {

    @POST("login")
    Call<LoginResponse> toLogin(LoginRequest logR);

    @POST("signup")
    Call<LoginResponse> toSignUp (LoginRequest logR);

    @GET("getToComplete")
    Call<List<GameToCompleteResponse>> getToCompleteList (GameRequest gR);

    @GET("getCompleted")
    Call<List<GameCompletedResponse>> getCompletedList (GameRequest gR);

    @POST("logOut")
    Call<LogoutResponse> toLogOut (LogoutRequest logoutRequest);

}
