package com.emerycprimeau.gameq.GUI.completed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.emerycprimeau.gameq.GUI.AddGame;
import com.emerycprimeau.gameq.GUI.connexion.LogIn;
import com.emerycprimeau.gameq.GUI.toComplete.ToComplete;
import com.emerycprimeau.gameq.R;
import com.emerycprimeau.gameq.http.GameRetrofit;
import com.emerycprimeau.gameq.http.mock.ServiceMock;
import com.emerycprimeau.gameq.models.CurrentUser;
import com.emerycprimeau.gameq.models.transfer.GameCompletedResponse;
import com.emerycprimeau.gameq.models.transfer.GameRequest;
import com.emerycprimeau.gameq.models.transfer.LogoutRequest;
import com.emerycprimeau.gameq.models.transfer.LogoutResponse;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completed);
        final ServiceMock serviceMock = GameRetrofit.get();


        //region recyclerView


        recyclerView = findViewById(R.id.recyclerViewCompleted);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        GameRequest gameRequest = new GameRequest();
        gameRequest.userId = CurrentUser.currentId;

        serviceMock.getCompletedList(gameRequest).enqueue(new Callback<List<GameCompletedResponse>>() {
            @Override
            public void onResponse(Call<List<GameCompletedResponse>> call, Response<List<GameCompletedResponse>> response) {
                if(response.isSuccessful())
                {
                    mAdapter = new MonAdapteurCompleted(response.body(), getApplicationContext());
                    recyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<GameCompletedResponse>> call, Throwable t) {

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

}
