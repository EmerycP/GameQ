package com.emerycprimeau.gameq.http;

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
import com.emerycprimeau.gameq.models.transfer.SignupRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Service {

    @POST("login")
    Call<LoginResponse> toLogin(@Body LoginRequest logR);

    @POST("signup")
    Call<LoginResponse> toSignUp (@Body SignupRequest logR);

    @GET("getToComplete")
    Call<List<GameToCompleteResponse>> getToCompleteList (GameRequest gR);

    @GET("getCompleted")
    Call<List<GameCompletedResponse>> getCompletedList (GameRequest gR);

    @POST("logOut")
    Call<LogoutResponse> toLogOut (LogoutRequest logoutRequest);

    @GET("GameEdit")
    Call<GameResponseEdit> getToEdit (int gameID);

    @POST("GameEdited")
    Call<Boolean> toEdit(GameRequestEdit gameRequestEdit);

    @POST("GameAdded")
    Call<Boolean> toAdd(GameResponseAdd gameResponseAdd);

}
