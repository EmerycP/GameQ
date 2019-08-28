package com.emerycprimeau.gameq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Inscription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        //getSupportActionBar().setTitle("Sign Up");

        Button buttonLogIn = findViewById(R.id.bSingUp);
        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBack2LogIn = new Intent(getApplicationContext(), LogIn.class);
                startActivity(intentBack2LogIn);
            }
        });

    }
}
