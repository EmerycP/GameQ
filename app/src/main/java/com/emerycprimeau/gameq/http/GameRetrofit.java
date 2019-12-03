package com.emerycprimeau.gameq.http;

import com.emerycprimeau.gameq.http.mock.Mock;
import com.emerycprimeau.gameq.http.mock.ServiceMock;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

public class GameRetrofit {

  public static Service service = null;


    private static GameRetrofit single_instance = null;

    public GameRetrofit(){
    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient())
            .baseUrl("https://10.0.2.2:8443/api/")
            .build();

    service = retrofit.create(Service.class);
  }

    public static GameRetrofit getReal()
    {
        if (single_instance == null)
            single_instance = new GameRetrofit();

        return single_instance;
    }

    public static class MyCookieJar implements CookieJar {

        private List<Cookie> cookies;

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            this.cookies =  cookies;
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> res = new ArrayList<>();
            if (cookies != null){
                for(Cookie c : cookies){
                    if (c.expiresAt() > System.currentTimeMillis()) res.add(c);
                }
            }
            return res;
        }
    }

    public static OkHttpClient getClient(){
        try {
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                CookieJar cookieJar =
                    new MyCookieJar();
                builder = builder.cookieJar(cookieJar);
                final TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                            }

                            @Override
                            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                            }

                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return new java.security.cert.X509Certificate[]{};
                            }
                        }
                };
                // Install the all-trusting trust manager
                final SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                // Create an ssl socket factory with our all-trusting manager
                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                // configure the builder to accept all SSL certificates
                builder = builder.sslSocketFactory(sslSocketFactory);
                // configure the builder to accept all hostnames includint localhost
                builder = builder.hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
                // Adds logging capability to see http exchanges on Android Monitor
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder = builder.addInterceptor(interceptor);
                return builder.build();
            } catch (Exception e) {
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
