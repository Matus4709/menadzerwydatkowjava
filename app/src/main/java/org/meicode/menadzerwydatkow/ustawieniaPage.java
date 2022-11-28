package org.meicode.menadzerwydatkow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
public class ustawieniaPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button btnlightdark;
    Boolean isDarkModeOn=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ustawienia_page);
        btnlightdark = findViewById(R.id.btndarklight);

        isDarkModeOn = getDarkModeStatus();
        if(isDarkModeOn){
            btnlightdark.setText("On Light Mode");
        }else {
            btnlightdark.setText("On Dark Mode");
        }

        btnlightdark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isDarkModeOn){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    btnlightdark.setText("On Dark Mode");
                    isDarkModeOn=false;
                }else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    btnlightdark.setText("On Light Mode");
                    isDarkModeOn=true;
                }
            }
        });


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_ustawienia);

    }

    private boolean getDarkModeStatus(){
        int nightModeFlags =
                ustawieniaPage.this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags){
            case Configuration.UI_MODE_NIGHT_YES:
                return true;
            case Configuration.UI_MODE_NIGHT_NO:
                return false;
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                return false;
        }
        return false;
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

            drawerLayout.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();

        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.nav_wydatki:
                Intent a = new Intent(ustawieniaPage.this, wydatkiPage.class);
                startActivity(a);
                break;

            case R.id.nav_statystyki:
                Intent b = new Intent(ustawieniaPage.this, statystykiPage.class);
                startActivity(b);
                break;

            case R.id.nav_ustawienia:
                Intent c = new Intent(ustawieniaPage.this, ustawieniaPage.class);
                startActivity(c);
                break;

            case R.id.nav_start:
                Intent d = new Intent(ustawieniaPage.this, startPage.class);
                startActivity(d);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),loginPage.class));
        finish();
    }
}