package com.emerycprimeau.gameq.GUI.connexion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.emerycprimeau.gameq.GUI.toComplete.toComplete;
import com.emerycprimeau.gameq.R;
import com.emerycprimeau.gameq.http.GameRetrofit;
import com.emerycprimeau.gameq.http.mock.ServiceMock;
import com.emerycprimeau.gameq.models.currentUser;
import com.emerycprimeau.gameq.models.transfer.loginRequest;
import com.emerycprimeau.gameq.models.transfer.loginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        TextView buttonSignUp = findViewById(R.id.signUp);
        Button buttonLogIn = findViewById(R.id.bLogIn);

        final ServiceMock mockService = GameRetrofit.get();


        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginRequest lR = new loginRequest();
                mockService.toLogin(lR).enqueue(new Callback<loginResponse>() {
                    @Override
                    public void onResponse(Call<loginResponse> call, Response<loginResponse> response) {
                        if(response.isSuccessful())
                        {
                            currentUser.currentId = response.body().Id;
                            currentUser.email = response.body().emailCleaned;
                            Intent intentMain = new Intent(getApplicationContext(), toComplete.class);
                            startActivity(intentMain);
                            Toast.makeText(getApplicationContext(), "Bonjour " + currentUser.email + " !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<loginResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Erreur", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentInscription = new Intent(getApplicationContext(), Inscription.class);
                startActivity(intentInscription);
            }
        });
    }
}
