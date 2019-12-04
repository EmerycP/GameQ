package com.emerycprimeau.gameq.GUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.emerycprimeau.gameq.GUI.completed.Completed;
import com.emerycprimeau.gameq.GUI.connexion.Inscription;
import com.emerycprimeau.gameq.GUI.connexion.LogIn;
import com.emerycprimeau.gameq.GUI.toComplete.ToComplete;
import com.emerycprimeau.gameq.R;
import com.emerycprimeau.gameq.http.GameRetrofit;
import com.emerycprimeau.gameq.http.Service;
import com.emerycprimeau.gameq.models.CurrentUser;
import com.emerycprimeau.gameq.models.Game;
import com.emerycprimeau.gameq.models.transfer.LogoutRequest;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddGame extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    ProgressDialog progressD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_game);
        GameRetrofit.getReal();
        final Service service = GameRetrofit.service;

        //region Buttons
        final Button buttonCompleted = findViewById(R.id.buttonComplete);
        final Button buttonToComplete = findViewById(R.id.buttonToComplete);
        Button buttonOk = findViewById(R.id.buttonOk);

        final TextView gameName = findViewById(R.id.gameNameAdd);
        final Game gameR = new Game();
        final EditText editTextScore = findViewById(R.id.ScoreAdd);
        editTextScore.setVisibility(View.INVISIBLE);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                gameR.Name = gameName.getText().toString();

                if( !editTextScore.getText().toString().equals("") && editTextScore.getText().toString().length() > 0 )
                    gameR.Score = Integer.parseInt(editTextScore.getText().toString());

                progressD = ProgressDialog.show(AddGame.this, getString(R.string.PleaseWait),
                        getString(R.string.messOp), true);

                service.toAdd(gameR).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.isSuccessful())
                        {
                            if(editTextScore.getVisibility() == View.VISIBLE )
                            {
                                Intent intentCompleted = new Intent(getApplicationContext(), Completed.class);
                                startActivity(intentCompleted);
                                progressD.dismiss();
                            }
                            else
                            {
                                gameR.Score = 0;
                                Intent intentMain = new Intent(getApplicationContext(), ToComplete.class);
                                startActivity(intentMain);
                                progressD.dismiss();

                            }
                        }
                        else
                        {
                            try {
                                String mess = response.errorBody().string();
                                if(mess.equals("GameExist"))
                                    Toast.makeText(AddGame.this, R.string.gameExist, Toast.LENGTH_SHORT).show();
                                if(mess.equals("Score"))
                                    Toast.makeText(AddGame.this, R.string.ScoreError, Toast.LENGTH_SHORT).show();
                                if(mess.equals("MaxLength"))
                                    Toast.makeText(AddGame.this, getString(R.string.MaxLength) + gameName.length(), Toast.LENGTH_SHORT).show();
                                if(mess.equals("BlankException"))
                                    Toast.makeText(AddGame.this, R.string.BlankName, Toast.LENGTH_SHORT).show();
                                if(mess.equals("BlankScore"))
                                    Toast.makeText(AddGame.this, R.string.BlankScore, Toast.LENGTH_SHORT).show();

                                progressD.dismiss();

                            }

                            catch(Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        progressD.dismiss();

                        Toast.makeText(getApplicationContext(), getString(R.string.Error) + t, Toast.LENGTH_SHORT).show();
                    }
                });

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

                gameR.EstCompleter = true;

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

                gameR.EstCompleter = false;
                gameR.Score = 0;
            }
        });
        //endregion

        //region Drawer Code
        drawerLayout = findViewById(R.id.DrawerAdd);

        NavigationView navigationView = findViewById(R.id.nav_view);

        //Set le nom de la personne connecté
        View headerView = navigationView.getHeaderView(0);
        TextView nameLog = (TextView) headerView.findViewById(R.id.logInName);
        nameLog.setText(CurrentUser.user);


        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.nav_toComplete) {
                    Intent intentToComplete = new Intent(getApplicationContext(), ToComplete.class);
                    startActivity(intentToComplete);

                } else if (id == R.id.nav_Completed) {
                    Intent intentComplete = new Intent(getApplicationContext(), Completed.class);
                    startActivity(intentComplete);
                } else if (id == R.id.nav_AddGame) {
                    Intent intentAdd = new Intent(getApplicationContext(), AddGame.class);
                    startActivity(intentAdd);
                } else if (id == R.id.nav_LogOut) {
                    LogoutRequest lR = new LogoutRequest();
                    lR.userID = CurrentUser.currentId;
                    progressD = ProgressDialog.show(AddGame.this, getString(R.string.PleaseWait),
                            getString(R.string.messOp), true);
                    service.toLogOut().enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if(response.isSuccessful())
                            {
                                Intent intentLogIn = new Intent(getApplicationContext(), LogIn.class);
                                startActivity(intentLogIn);
                                progressD.dismiss();
                                Toast.makeText(getApplicationContext(), getString(R.string.SeeYa) + " " + CurrentUser.user + " !", Toast.LENGTH_SHORT).show();
                            }
                            else

                                try {
                                    String mess = response.errorBody().string();
                                    if(mess.equals("NoUserConnected"))
                                    {
                                        progressD.dismiss();
                                        Toast.makeText(AddGame.this, R.string.NoUserConnected, Toast.LENGTH_SHORT).show();
                                        Intent intentLogIn = new Intent(getApplicationContext(), LogIn.class);
                                        startActivity(intentLogIn);
                                    }

                                }
                                catch(Exception e)
                                {
                                    e.printStackTrace();
                                }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            progressD.dismiss();
                            Toast.makeText(getApplicationContext(), getString(R.string.Error) + t, Toast.LENGTH_SHORT).show();
                        }
                    });
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
