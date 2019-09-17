package com.emerycprimeau.gameq.GUI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.emerycprimeau.gameq.GUI.completed.Completed;
import com.emerycprimeau.gameq.GUI.connexion.LogIn;
import com.emerycprimeau.gameq.GUI.toComplete.toComplete;
import com.emerycprimeau.gameq.R;
import com.google.android.material.navigation.NavigationView;

public class EditGame extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_game);


        //region Buttons
        final Button buttonCompleted = findViewById(R.id.buttonComplete);
        final Button buttonToComplete = findViewById(R.id.buttonToComplete);
        Button buttonOk = findViewById(R.id.buttonOk);
        TextView gameName = findViewById(R.id.gameName);

        final EditText editTextScore = findViewById(R.id.Score);
        editTextScore.setVisibility(View.INVISIBLE);

        Intent i = getIntent();

        String name = i.getStringExtra("gameName");
        boolean valeur = i.getBooleanExtra("Completed", true);
        final String score = i.getStringExtra("score");

        if(valeur)
        {
            editTextScore.setVisibility(View.VISIBLE);

            buttonCompleted.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            buttonCompleted.setTextColor(Color.WHITE);

            buttonToComplete.setBackgroundColor(Color.LTGRAY);
            buttonToComplete.setTextColor(Color.BLACK);

            gameName.setText(name);
            editTextScore.setText(score);
        }
        else
        {
            editTextScore.setVisibility(View.INVISIBLE);

            buttonToComplete.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            buttonToComplete.setTextColor(Color.WHITE);

            buttonCompleted.setBackgroundColor(Color.LTGRAY);
            buttonCompleted.setTextColor(Color.BLACK);
            gameName.setText(name);
        }

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextScore.getVisibility() == View.VISIBLE )
                {
                    Intent intentCompleted = new Intent(getApplicationContext(), Completed.class);
                    startActivity(intentCompleted);
                }
                else
                {
                    Intent intentMain = new Intent(getApplicationContext(), toComplete.class);
                    startActivity(intentMain);
                }
            }
        });

        buttonCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editTextScore.setVisibility(View.VISIBLE);

                buttonCompleted.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                buttonCompleted.setTextColor(Color.WHITE);

                buttonToComplete.setBackgroundColor(Color.LTGRAY);
                buttonToComplete.setTextColor(Color.BLACK);

                editTextScore.setText(score);

            }
        });
        buttonToComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextScore.setVisibility(View.INVISIBLE);

                buttonToComplete.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                buttonToComplete.setTextColor(Color.WHITE);

                buttonCompleted.setBackgroundColor(Color.LTGRAY);
                buttonCompleted.setTextColor(Color.BLACK);

                editTextScore.setText("0");
            }
        });
        //endregion

        //region Drawer Code
        drawerLayout = findViewById(R.id.DrawerEdit);

        NavigationView navigationView = findViewById(R.id.nav_view);

        //Set le nom de la personne connecté
        View headerView = navigationView.getHeaderView(0);
        TextView nameLog = (TextView) headerView.findViewById(R.id.logInName);
        nameLog.setText("Test");


        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.nav_toComplete) {
                    Intent intentToComplete = new Intent(getApplicationContext(), toComplete.class);
                    startActivity(intentToComplete);

                } else if (id == R.id.nav_Completed) {
                    Intent intentComplete = new Intent(getApplicationContext(), Completed.class);
                    startActivity(intentComplete);
                } else if (id == R.id.nav_AddGame) {
                    Intent intentAdd = new Intent(getApplicationContext(), AddGame.class);
                    startActivity(intentAdd);
                } else if (id == R.id.nav_LogOut) {
                    Intent intentLogIn = new Intent(getApplicationContext(), LogIn.class);
                    startActivity(intentLogIn);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        if(drawerLayout != null)
            drawerLayout.addDrawerListener(actionBarDrawerToggle);
        if(actionBarDrawerToggle != null)
            actionBarDrawerToggle.syncState();

        //endregion
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item) && actionBarDrawerToggle != null)
        {
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}
