package org.meicode.menadzerwydatkow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;

public class startPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FirestoreAdapter.OnItemClickListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView saldoCounter;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth fAuth;
    private RecyclerView mwydatkiList;
    private FirestoreAdapter adapter;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        firebaseFirestore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        mwydatkiList = findViewById(R.id.wydatkiList5);
        saldoCounter = findViewById(R.id.saldoCount);

        userID  = fAuth.getCurrentUser().getUid();
        //Query
        Query query = firebaseFirestore.collection("users").document(userID).collection("wydatki")
                .orderBy("Data", Query.Direction.DESCENDING).limit(5);
        //RecyclerOptions
        FirestoreRecyclerOptions<WydatkiModel> options = new FirestoreRecyclerOptions.Builder<WydatkiModel>()
                .setLifecycleOwner(this)
                .setQuery(query,WydatkiModel.class)
                .build();

        adapter = new FirestoreAdapter(options,this);

        mwydatkiList.setHasFixedSize(true);
        mwydatkiList.setLayoutManager(new LinearLayoutManager(this));
        mwydatkiList.setAdapter(adapter);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_start);


        DocumentReference docRef = firebaseFirestore.collection("users").document(userID).collection("ustawienia").document("saldo");
        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                saldoCounter.setText(value.getString("bilans")+" PLN");
            }
        });





        final Button DodajWydatek = findViewById(R.id.dodajWydatekBtn);

        DodajWydatek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(startPage.this, dodajWydatekPage.class));
            }
        });

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
                Intent a = new Intent(startPage.this, wydatkiPage.class);
                startActivity(a);
                break;

            case R.id.nav_statystyki:
                Intent b = new Intent(startPage.this, statystykiPage.class);
                startActivity(b);
                break;

            case R.id.nav_ustawienia:
                Intent c = new Intent(startPage.this, ustawieniaPage.class);
                startActivity(c);
                break;

            case R.id.nav_start:
                Intent d = new Intent(startPage.this, startPage.class);
                startActivity(d);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }


    @Override
    public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

    }
}