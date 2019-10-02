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
import com.emerycprimeau.gameq.models.transfer.LoginResponse;
import com.emerycprimeau.gameq.models.transfer.SignupRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Inscription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        final ServiceMock mockService = GameRetrofit.get();

        Button buttonLogIn = findViewById(R.id.bSingUp);
        final TextView emailSign = findViewById(R.id.emailTextSign);
        final TextView passSign = findViewById(R.id.passwordTextSign);
        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignupRequest sR = new SignupRequest();
                sR.email = emailSign.getText().toString();
                sR.password = passSign.getText().toString();
                mockService.toSignUp(sR).enqueue(new Callback<LoginResponse>() {
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
                        Toast.makeText(getApplicationContext(), "Erreur!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });


    }
}
