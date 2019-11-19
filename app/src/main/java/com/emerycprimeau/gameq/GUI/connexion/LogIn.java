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
import com.emerycprimeau.gameq.http.Service;
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
        final TextView userLogin = findViewById(R.id.userText);
        final TextView passLogin = findViewById(R.id.passwordText);

        final Service Service = GameRetrofit.getReal();


        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginRequest lR = new LoginRequest();
                lR.user = userLogin.getText().toString();
                lR.password = passLogin.getText().toString();
                Service.toLogin(lR).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if(response.isSuccessful())
                        {
                            CurrentUser.currentId = response.body().Id;
                            CurrentUser.user = response.body().emailCleaned;
                            Intent intentMain = new Intent(getApplicationContext(), ToComplete.class);
                            startActivity(intentMain);
                            Toast.makeText(getApplicationContext(), getString(R.string.Hello) + " "  + CurrentUser.user + " !", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            try {
                                String mess = response.errorBody().string();
                                if(mess.equals("BlankException"))
                                    Toast.makeText(LogIn.this, R.string.OneCharacterEmpty, Toast.LENGTH_SHORT).show();
                                if(mess.equals("NoMatch"))
                                    Toast.makeText(LogIn.this, R.string.NoMatch, Toast.LENGTH_SHORT).show();
                            }
                            catch(Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), getString(R.string.Error) + t, Toast.LENGTH_SHORT).show();
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
