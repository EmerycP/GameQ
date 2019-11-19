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
import com.emerycprimeau.gameq.GUI.connexion.Inscription;
import com.emerycprimeau.gameq.GUI.connexion.LogIn;
import com.emerycprimeau.gameq.GUI.toComplete.ToComplete;
import com.emerycprimeau.gameq.R;
import com.emerycprimeau.gameq.http.GameRetrofit;
import com.emerycprimeau.gameq.http.Service;
import com.emerycprimeau.gameq.http.mock.ServiceMock;
import com.emerycprimeau.gameq.models.CurrentUser;
import com.emerycprimeau.gameq.models.Game;
import com.emerycprimeau.gameq.models.transfer.GameRequestEdit;
import com.emerycprimeau.gameq.models.transfer.LogoutRequest;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditGame extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ServiceMock serviceMock = GameRetrofit.get();
    private GameRequestEdit game = new GameRequestEdit();
    private Game gameR = new Game();
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

        final Service service = GameRetrofit.getReal();

        //region Buttons
        buttonCompleted = findViewById(R.id.buttonComplete);
        buttonToComplete = findViewById(R.id.buttonToComplete);
        buttonOk = findViewById(R.id.buttonOk);
        gameName = findViewById(R.id.gameName);
        editTextScore = findViewById(R.id.Score);
        editTextScore.setVisibility(View.INVISIBLE);


        Intent i = getIntent();
        int idIntent = i.getIntExtra("id", 0);
        service.getToEdit(CurrentUser.currentId, idIntent).enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                if (response.isSuccessful()) {
                    gestionGameReponse(response.body());
                    gameR.ID = response.body().ID;
                    game.gameID = response.body().ID;
                    game.userID = CurrentUser.currentId;
                    gameR.Name = response.body().Name;
                    gameR.EstCompleter = response.body().EstCompleter;
                    gameR.Score = response.body().Score;
                }
                else
                {
                    try {
                        String mess = response.errorBody().string();
                        if(mess.equals("GameSelectedDontExist"))
                            Toast.makeText(EditGame.this, R.string.SelectedDontExist, Toast.LENGTH_SHORT).show();

                    }

                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {

            }
        });
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextScore.getVisibility() == View.VISIBLE )
                {

                    game.name = gameName.getText().toString();
                    game.estComplete = true;
                    if( !editTextScore.getText().toString().equals("") && editTextScore.getText().toString().length() > 0 )
                        game.score = Integer.parseInt(editTextScore.getText().toString());
                    service.gameEdit(game).enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if(response.isSuccessful()) {


                                Intent intentCompleted = new Intent(getApplicationContext(), Completed.class);
                                startActivity(intentCompleted);
                                Toast.makeText(getApplicationContext(), R.string.GameEdited, Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                try {
                                    String mess = response.errorBody().string();
                                    if(mess.equals("GameExist"))
                                        Toast.makeText(EditGame.this, R.string.gameExist, Toast.LENGTH_SHORT).show();
                                    if(mess.equals("Score"))
                                        Toast.makeText(EditGame.this, R.string.ScoreError, Toast.LENGTH_SHORT).show();
                                    if(mess.equals("MaxLength"))
                                        Toast.makeText(EditGame.this, getString(R.string.MaxLength) + gameName.length(), Toast.LENGTH_SHORT).show();
                                    if(mess.equals("BlankException"))
                                        Toast.makeText(EditGame.this, R.string.BlankName, Toast.LENGTH_SHORT).show();
                                    if(mess.equals("BlankScore"))
                                        Toast.makeText(EditGame.this, R.string.BlankScore, Toast.LENGTH_SHORT).show();
                                }
                                catch(Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), R.string.Error, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else
                {
                    game.name = gameName.getText().toString();
                    game.estComplete = false;
                    service.gameEdit(game).enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if(response.isSuccessful())
                            {
                                Intent intentCompleted = new Intent(getApplicationContext(), ToComplete.class);
                                startActivity(intentCompleted);
                                Toast.makeText(getApplicationContext(), R.string.GameEdited, Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                try {
                                    String mess = response.errorBody().string();
                                    if(mess.equals("GameExist"))
                                        Toast.makeText(EditGame.this, R.string.gameExist, Toast.LENGTH_SHORT).show();
                                    if(mess.equals("Score"))
                                        Toast.makeText(EditGame.this, R.string.ScoreError, Toast.LENGTH_SHORT).show();
                                    if(mess.equals("MaxLength"))
                                        Toast.makeText(EditGame.this, getString(R.string.MaxLength) + gameName.length(), Toast.LENGTH_SHORT).show();
                                    if(mess.equals("BlankException"))
                                        Toast.makeText(EditGame.this, R.string.BlankName, Toast.LENGTH_SHORT).show();
                                    if(mess.equals("BlankScore"))
                                        Toast.makeText(EditGame.this, R.string.BlankScore, Toast.LENGTH_SHORT).show();
                                }

                                catch(Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), R.string.Error, Toast.LENGTH_SHORT).show();
                        }
                    });
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

                gameR.EstCompleter = true;
                editTextScore.setText(gameR.Score+"");

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
                    service.toLogOut(lR).enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if(response.isSuccessful())
                            {
                                Intent intentLogIn = new Intent(getApplicationContext(), LogIn.class);
                                startActivity(intentLogIn);
                                Toast.makeText(getApplicationContext(), getString(R.string.SeeYa) + " " + CurrentUser.user + " !", Toast.LENGTH_SHORT).show();
                            }
                            else

                                try {
                                    String mess = response.errorBody().string();
                                    if(mess.equals("NoUserConnected"))
                                    {
                                        Toast.makeText(EditGame.this, R.string.NoUserConnected, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getApplicationContext(), R.string.Error, Toast.LENGTH_SHORT).show();
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

    public void gestionGameReponse(Game pResponse)
    {
        if(pResponse.EstCompleter)
        {
            editTextScore.setVisibility(View.VISIBLE);

            buttonCompleted.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            buttonCompleted.setTextColor(Color.WHITE);

            buttonToComplete.setBackgroundColor(Color.LTGRAY);
            buttonToComplete.setTextColor(Color.BLACK);

            gameName.setText(pResponse.Name);
            editTextScore.setText(pResponse.Score+"");
        }
        else
        {
            editTextScore.setVisibility(View.INVISIBLE);

            buttonToComplete.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            buttonToComplete.setTextColor(Color.WHITE);

            buttonCompleted.setBackgroundColor(Color.LTGRAY);
            buttonCompleted.setTextColor(Color.BLACK);
            gameName.setText(pResponse.Name);
            editTextScore.setText("0");
        }
    }
}
