package com.emerycprimeau.gameq.GUI.connexion;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.emerycprimeau.gameq.models.transfer.LoginResponse;
import com.emerycprimeau.gameq.models.transfer.SignupRequest;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Inscription extends AppCompatActivity {

    ProgressDialog progressD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        final Service s = GameRetrofit.getReal();

        Button buttonLogIn = findViewById(R.id.bSingUp);
        final TextView userSign = findViewById(R.id.emailTextSign);
        final TextView passSign = findViewById(R.id.passwordTextSign);
        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignupRequest sR = new SignupRequest();
                sR.user = userSign.getText().toString();
                sR.password = passSign.getText().toString();
                progressD = ProgressDialog.show(Inscription.this, getString(R.string.PleaseWait),
                        getString(R.string.messOp), true);

        s.toSignUp(sR).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if(response.isSuccessful()) {
                    CurrentUser.currentId = response.body().Id;
                    CurrentUser.user = response.body().emailCleaned;
                    Intent intentMain = new Intent(getApplicationContext(), ToComplete.class);
                    startActivity(intentMain);
                    Toast.makeText(getApplicationContext(), getString(R.string.Hello) + " " + CurrentUser.user + " !", Toast.LENGTH_SHORT).show();
                    progressD.dismiss();
                }
                else
                {
                    try {
                        String mess = response.errorBody().string();
                        if(mess.equals("BlankException"))
                            Toast.makeText(Inscription.this, R.string.OneCharacterEmpty, Toast.LENGTH_SHORT).show();
                        if(mess.equals("UsernameExist"))
                            Toast.makeText(Inscription.this, R.string.UsernameExist, Toast.LENGTH_SHORT).show();
                        if(mess.equals("NoSpace"))
                            Toast.makeText(Inscription.this, R.string.FieldSpace, Toast.LENGTH_SHORT).show();
                        if(mess.equals("MaxLength"))
                            Toast.makeText(Inscription.this, R.string.CharaLimit20, Toast.LENGTH_SHORT).show();

                        progressD.dismiss();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                progressD.dismiss();
                Toast.makeText(getApplicationContext(), getString(R.string.Error) + t, Toast.LENGTH_SHORT).show();

            }
        });
            }

        });


    }
}
