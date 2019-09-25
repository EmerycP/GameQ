package com.emerycprimeau.gameq.http.mock;

import com.emerycprimeau.gameq.models.transfer.loginRequest;
import com.emerycprimeau.gameq.models.transfer.loginResponse;

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
        lR.emailCleaned = "emeryc@primeau.ca";
        return this.delegate.returningResponse(lR).toLogin(logR);
    }
}
