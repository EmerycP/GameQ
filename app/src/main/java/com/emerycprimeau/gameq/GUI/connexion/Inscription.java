package com.emerycprimeau.gameq.GUI.connexion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.emerycprimeau.gameq.GUI.toComplete.toComplete;
import com.emerycprimeau.gameq.R;

public class Inscription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        Button buttonLogIn = findViewById(R.id.bSingUp);
        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogIn = new Intent(getApplicationContext(), toComplete.class);
                startActivity(intentLogIn);
            }
        });

    }
}
