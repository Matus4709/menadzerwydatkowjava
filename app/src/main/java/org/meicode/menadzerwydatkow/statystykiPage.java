package org.meicode.menadzerwydatkow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView ;

import java.util.ArrayList;

import kotlin.jvm.internal.Lambda;

public class statystykiPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statystyki_page);

        PieChart pieChart = findViewById(R.id.pieChart);

        ArrayList<PieEntry> tmp = new ArrayList<>();
        tmp.add(new PieEntry(400,"Żywność i napoje"));
        tmp.add(new PieEntry(100,"Inne"));
        tmp.add(new PieEntry(400,"Podatki"));
        tmp.add(new PieEntry(350,"Transport"));

        PieDataSet pieDataSet = new PieDataSet(tmp, "Kategoria");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        pieChart.animateY(1000, Easing.EaseInOutCubic);
        pieChart.setDrawHoleEnabled(false);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(16f);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(true);
        pieChart.setCenterText("");
        pieChart.animate();




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
