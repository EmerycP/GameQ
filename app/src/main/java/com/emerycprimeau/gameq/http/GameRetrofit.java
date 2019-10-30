package com.emerycprimeau.gameq.http;

import com.emerycprimeau.gameq.http.mock.Mock;
import com.emerycprimeau.gameq.http.mock.ServiceMock;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

public class GameRetrofit {

  public static Service getReal(){
    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient())
            .baseUrl("http://10.0.2.2:8080/api/")
            .build();

    Service service = retrofit.create(Service.class);
    return service;
  }


    public static OkHttpClient getClient(){
        try {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            // Adds logging capability to see http exchanges on Android Monitor
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder = builder.addInterceptor(interceptor);
            return builder.build();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


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
