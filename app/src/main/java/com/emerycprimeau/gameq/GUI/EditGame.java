package com.emerycprimeau.gameq.GUI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.emerycprimeau.gameq.GUI.completed.Completed;
import com.emerycprimeau.gameq.GUI.connexion.LogIn;
import com.emerycprimeau.gameq.GUI.toComplete.ToComplete;
import com.emerycprimeau.gameq.R;
import com.emerycprimeau.gameq.http.GameRetrofit;
import com.emerycprimeau.gameq.http.mock.ServiceMock;
import com.emerycprimeau.gameq.models.CurrentUser;
import com.emerycprimeau.gameq.models.transfer.GameRequestEdit;
import com.emerycprimeau.gameq.models.transfer.GameResponseEdit;
import com.emerycprimeau.gameq.models.transfer.LogoutRequest;
import com.emerycprimeau.gameq.models.transfer.LogoutResponse;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditGame extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ServiceMock serviceMock = GameRetrofit.get();
    private GameRequestEdit game = new GameRequestEdit();
    private String name;
    public int score;
    public Button buttonCompleted;
    public Button buttonToComplete;
    public Button buttonOk;
    public TextView gameName;
    public EditText editTextScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_game);
        final ServiceMock serviceMock = GameRetrofit.get();

        //region Buttons
        buttonCompleted = findViewById(R.id.buttonComplete);
        buttonToComplete = findViewById(R.id.buttonToComplete);
        buttonOk = findViewById(R.id.buttonOk);
        gameName = findViewById(R.id.gameName);
        editTextScore = findViewById(R.id.Score);
        editTextScore.setVisibility(View.INVISIBLE);


        Intent i = getIntent();
        int idIntent = i.getIntExtra("id", 0);
        serviceMock.getToEdit(idIntent).enqueue(new Callback<GameResponseEdit>() {
            @Override
            public void onResponse(Call<GameResponseEdit> call, Response<GameResponseEdit> response) {
                if (response.isSuccessful()) {
                    gestionGameReponse(response.body());
                    game.gameID = response.body().gameID;
                    game.name = response.body().name;
                    game.estComplete = response.body().estComplete;
                    game.score = response.body().score;
                }
            }

            @Override
            public void onFailure(Call<GameResponseEdit> call, Throwable t) {

            }
        });
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextScore.getVisibility() == View.VISIBLE )
                {
                    game.name = gameName.getText().toString();
                    if( !editTextScore.getText().toString().equals("") && editTextScore.getText().toString().length() > 0 )
                        game.score = Integer.parseInt(editTextScore.getText().toString());
                    serviceMock.toEdit(game).enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            Intent intentCompleted = new Intent(getApplicationContext(), Completed.class);
                            startActivity(intentCompleted);
                            Toast.makeText(getApplicationContext(), "Le jeu " + game.name + " été modifié.", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Erreur", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else
                {
                    Intent intentMain = new Intent(getApplicationContext(), ToComplete.class);
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

                game.estComplete = true;
                editTextScore.setText(game.score+"");

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

                game.estComplete = false;
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
        nameLog.setText(CurrentUser.email);


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
                    serviceMock.toLogOut(lR).enqueue(new Callback<LogoutResponse>() {
                        @Override
                        public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                            if(response.isSuccessful())
                            {
                                Intent intentLogIn = new Intent(getApplicationContext(), LogIn.class);
                                startActivity(intentLogIn);
                                Toast.makeText(getApplicationContext(), "Au revoir " + CurrentUser.email + " !", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<LogoutResponse> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
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

    public void gestionGameReponse(GameResponseEdit pResponse)
    {
        if(pResponse.estComplete)
        {
            editTextScore.setVisibility(View.VISIBLE);

            buttonCompleted.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            buttonCompleted.setTextColor(Color.WHITE);

            buttonToComplete.setBackgroundColor(Color.LTGRAY);
            buttonToComplete.setTextColor(Color.BLACK);

            gameName.setText(pResponse.name);
            editTextScore.setText(pResponse.score+"");
        }
        else
        {
            editTextScore.setVisibility(View.INVISIBLE);

            buttonToComplete.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            buttonToComplete.setTextColor(Color.WHITE);

            buttonCompleted.setBackgroundColor(Color.LTGRAY);
            buttonCompleted.setTextColor(Color.BLACK);
            gameName.setText(pResponse.name);
        }
    }
}
