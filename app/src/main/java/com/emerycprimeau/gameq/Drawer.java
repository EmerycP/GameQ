//package com.emerycprimeau.gameq;
//
//import android.content.Intent;
//import android.os.Bundle;
//
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.snackbar.Snackbar;
//
//import android.view.View;
//
//import androidx.core.view.GravityCompat;
//import androidx.appcompat.app.ActionBarDrawerToggle;
//
//import android.view.MenuItem;
//
//import com.google.android.material.navigation.NavigationView;
//
//import androidx.drawerlayout.widget.DrawerLayout;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//
//import android.view.Menu;
//import android.widget.Button;
//import android.widget.Toast;
//
//public class Drawer extends AppCompatActivity
//        implements NavigationView.OnNavigationItemSelectedListener {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        DrawerLayout drawer = findViewById(R.id.DrawerMain);
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//        navigationView.setNavigationItemSelectedListener(this);
//    }
//
//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = findViewById(R.id.DrawerMain);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.drawer, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.nav_toComplete) {
//            Toast.makeText(getApplicationContext(), "Item: " + item, Toast.LENGTH_SHORT).show();
//
//        } else if (id == R.id.nav_Completed) {
//
//        } else if (id == R.id.nav_AddGame) {
//
//        } else if (id == R.id.nav_LogOut) {
//            Intent intentLogIn = new Intent(getApplicationContext(), LogIn.class);
//            startActivity(intentLogIn);
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_toComplete) {
//
//        } else if (id == R.id.nav_Completed) {
//
//        } else if (id == R.id.nav_AddGame) {
//
//        } else if (id == R.id.nav_LogOut) {
//            Intent intentLogIn = new Intent(getApplicationContext(), LogIn.class);
//            startActivity(intentLogIn);
//        }
//
//        DrawerLayout drawer = findViewById(R.id.DrawerMain);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
//}
