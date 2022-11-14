package org.meicode.menadzerwydatkow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView ;

public class statystykiPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statystyki_page);



        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_statystyki);
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
        public boolean onNavigationItemSelected (@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()) {

                case R.id.nav_wydatki:
                    Intent a = new Intent(statystykiPage.this, wydatkiPage.class);
                    startActivity(a);
                    break;

                case R.id.nav_statystyki:
                    Intent b = new Intent(statystykiPage.this, statystykiPage.class);
                    startActivity(b);
                    break;

                case R.id.nav_ustawienia:
                    Intent c = new Intent(statystykiPage.this, ustawieniaPage.class);
                    startActivity(c);
                    break;

                case R.id.nav_start:
                    Intent d = new Intent(statystykiPage.this, startPage.class);
                    startActivity(d);
                    break;
            }

            drawerLayout.closeDrawer(GravityCompat.START);

            return true;
        }
    }
