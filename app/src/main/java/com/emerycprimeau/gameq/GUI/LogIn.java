package com.emerycprimeau.gameq.GUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.emerycprimeau.gameq.R;

public class LogIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        TextView buttonSignUp = findViewById(R.id.signUp);
        Button buttonLogIn = findViewById(R.id.bLogIn);

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMain = new Intent(getApplicationContext(), toComplete.class);
                startActivity(intentMain);
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
