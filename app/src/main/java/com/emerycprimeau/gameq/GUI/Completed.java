package com.emerycprimeau.gameq.GUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.emerycprimeau.gameq.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class Completed extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completed);



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
