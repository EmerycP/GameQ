package com.emerycprimeau.gameq.http.mock;

import com.emerycprimeau.gameq.models.transfer.gameCompletedResponse;
import com.emerycprimeau.gameq.models.transfer.gameRequest;
import com.emerycprimeau.gameq.models.transfer.gameToCompleteResponse;
import com.emerycprimeau.gameq.models.transfer.loginRequest;
import com.emerycprimeau.gameq.models.transfer.loginResponse;
import com.emerycprimeau.gameq.models.transfer.signupRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceMock {

    @POST("login")
    Call<loginResponse> toLogin(loginRequest logR);

    @POST("signup")
    Call<loginResponse> toSignUp (loginRequest logR);

    @GET("getToComplete")
    Call<List<gameToCompleteResponse>> getToCompleteList (gameRequest gR);


    @GET("getCompleted")
    Call<List<gameCompletedResponse>> getCompletedList (gameRequest gR);

}
