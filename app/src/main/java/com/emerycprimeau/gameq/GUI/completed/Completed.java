package com.emerycprimeau.gameq.GUI.completed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.emerycprimeau.gameq.GUI.AddGame;
import com.emerycprimeau.gameq.GUI.connexion.Inscription;
import com.emerycprimeau.gameq.GUI.connexion.LogIn;
import com.emerycprimeau.gameq.GUI.toComplete.ToComplete;
import com.emerycprimeau.gameq.R;
import com.emerycprimeau.gameq.http.GameRetrofit;
import com.emerycprimeau.gameq.http.Service;
import com.emerycprimeau.gameq.models.CurrentUser;
import com.emerycprimeau.gameq.models.Game;
import com.emerycprimeau.gameq.models.transfer.LogoutRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Completed extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    //RecyclerView
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private RelativeLayout pB;
    ProgressDialog progressD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completed);
        pB = findViewById(R.id.progressBar);
        GameRetrofit.getReal();
        final Service service = GameRetrofit.service;

        //region recyclerView


        recyclerView = findViewById(R.id.recyclerViewCompleted);
        recyclerView.setHasFixedSize(true);


        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        startDownload();


        service.getCompletedList(CurrentUser.currentId).enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if(response.isSuccessful())
                {
                    mAdapter = new MonAdapteurCompleted(response.body(), getApplicationContext());
                    recyclerView.setAdapter(mAdapter);
                    endDownload();
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getString(R.string.Error) + t, Toast.LENGTH_SHORT).show();
                endDownload();
            }
        });
        //endregion

        //region Floating Button

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAddGame = new Intent(getApplicationContext(), AddGame.class);
                startActivity(intentAddGame);
            }
        });
        //endregion

        //region Drawer Code

        drawerLayout = findViewById(R.id.DrawerCompleted);

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
                    progressD = ProgressDialog.show(Completed.this, getString(R.string.PleaseWait),
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
                                        Toast.makeText(Completed.this, R.string.NoUserConnected, Toast.LENGTH_SHORT).show();
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

    private void startDownload(){
        pB.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void endDownload(){
        pB.setVisibility(View.GONE);//INVISIBLE occupe de l'espace GONE non
        recyclerView.setVisibility(View.VISIBLE);
    }

}
