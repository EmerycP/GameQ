package com.emerycprimeau.gameq.http.mock;

import com.emerycprimeau.gameq.models.transfer.loginRequest;
import com.emerycprimeau.gameq.models.transfer.loginResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceMock {

    @POST("login")
    Call<loginResponse> toLogin(loginRequest logR);

}
