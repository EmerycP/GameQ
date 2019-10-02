package com.emerycprimeau.gameq.GUI.connexion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.emerycprimeau.gameq.GUI.toComplete.ToComplete;
import com.emerycprimeau.gameq.R;
import com.emerycprimeau.gameq.http.GameRetrofit;
import com.emerycprimeau.gameq.http.mock.ServiceMock;
import com.emerycprimeau.gameq.models.CurrentUser;
import com.emerycprimeau.gameq.models.transfer.LoginRequest;
import com.emerycprimeau.gameq.models.transfer.LoginResponse;

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
        final TextView emailLogin = findViewById(R.id.emailText);
        final TextView passLogin = findViewById(R.id.passwordText);

        final ServiceMock mockService = GameRetrofit.get();


        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginRequest lR = new LoginRequest();
                lR.email = emailLogin.getText().toString();
                lR.password = passLogin.getText().toString();
                mockService.toLogin(lR).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if(response.isSuccessful())
                        {
                            CurrentUser.currentId = response.body().Id;
                            CurrentUser.email = response.body().emailCleaned;
                            Intent intentMain = new Intent(getApplicationContext(), ToComplete.class);
                            startActivity(intentMain);
                            Toast.makeText(getApplicationContext(), "Bonjour " + CurrentUser.email + " !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
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
