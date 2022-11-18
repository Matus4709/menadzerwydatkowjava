package org.meicode.menadzerwydatkow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class wydatkiPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth fAuth;
    private RecyclerView mwydatkiList;
    private FirestoreRecyclerAdapter adapter;
    private String userID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wydatki_page);

        firebaseFirestore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        mwydatkiList = findViewById(R.id.wydatkiList);

        userID  = fAuth.getCurrentUser().getUid();
        //Query
        Query query = firebaseFirestore.collection("users").document(userID).collection("wydatki")
                .orderBy("Data", Query.Direction.DESCENDING);
        //RecyclerOptions
        FirestoreRecyclerOptions<WydatkiModel> options = new FirestoreRecyclerOptions.Builder<WydatkiModel>()
                .setQuery(query,WydatkiModel.class)
                .build();

         adapter = new FirestoreRecyclerAdapter<WydatkiModel, WydatkiViewHolder>(options) {
            @NonNull
            @Override
            public WydatkiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent,false);
                return new WydatkiViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull WydatkiViewHolder holder, int position, @NonNull WydatkiModel model) {
                holder.list_name.setText(model.getNazwa());
                holder.list_category.setText(model.getKategoria());
                holder.list_date.setText(model.getData()+"");
                holder.list_price.setText(model.getKwota()+"");
            }
        };

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

        navigationView.setCheckedItem(R.id.nav_wydatki);


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
                Intent a = new Intent(wydatkiPage.this, wydatkiPage.class);
                startActivity(a);
                break;

            case R.id.nav_statystyki:
                Intent b = new Intent(wydatkiPage.this, statystykiPage.class);
                startActivity(b);
                break;

            case R.id.nav_ustawienia:
                Intent c = new Intent(wydatkiPage.this, ustawieniaPage.class);
                startActivity(c);
                break;

            case R.id.nav_start:
                Intent d = new Intent(wydatkiPage.this, startPage.class);
                startActivity(d);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    private class WydatkiViewHolder extends RecyclerView.ViewHolder{

        private TextView list_name;
        private TextView list_category;
        private TextView list_date;
        private TextView list_price;

        public WydatkiViewHolder(@NonNull View itemView) {
            super(itemView);

            list_name = itemView.findViewById(R.id.list_name);
            list_category = itemView.findViewById(R.id.list_category);
            list_date = itemView.findViewById(R.id.list_date);
            list_price = itemView.findViewById(R.id.list_price);

        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}