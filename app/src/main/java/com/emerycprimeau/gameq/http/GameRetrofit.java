package com.emerycprimeau.gameq.http;

import com.emerycprimeau.gameq.http.mock.Mock;
import com.emerycprimeau.gameq.http.mock.ServiceMock;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

public class GameRetrofit {

//  public static Service get(){
//    Retrofit retrofit = new Retrofit.Builder()
//            .addConverterFactory(ScalarsConverterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(client()) --> voir video joris pour la méthode
//            .baseUrl("https://api.github.com/")
//            .build();
//
//    Service service = retrofit.create(Service.class);
//  }

    public static ServiceMock get(){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/")
                .build();

        NetworkBehavior behavior = NetworkBehavior.create(); //Permet de simuler des trucs au réseau (Ex: délai, % erreur, etc.)
        MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit).networkBehavior(behavior).build();
        BehaviorDelegate<ServiceMock> delegate = mockRetrofit.create(ServiceMock.class);

        return new Mock(delegate);
    }

}
