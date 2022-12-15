package org.meicode.menadzerwydatkow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class podgladWydatkuPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    TextView nazwa,kategoria,data,kwota;
    Button deleteBtn, cancelBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    String wID;
    private StorageReference mStorageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podglad_wydatku_page);

        nazwa = findViewById(R.id.NazwaTV);
        kategoria = findViewById(R.id.KategoriaTV);
        data = findViewById(R.id.DataTV);
        kwota = findViewById(R.id.KwotaTV);
        deleteBtn = findViewById(R.id.deleteBtn);
        cancelBtn = findViewById(R.id.cancelBtn);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String id = extras.getString("key");
            //The key argument here must match that used in the other activity
            Log.d("TEST","test: " +id);
            wID = id;

        }



        DocumentReference docRef = fStore.collection("users").document(userID).collection("wydatki").document(wID);

        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                String Nazwa1stUpper = (value.getString("Nazwa")).substring(0, 1).toUpperCase() + (value.getString("Nazwa")).substring(1);
                nazwa.setText(Nazwa1stUpper);
                kategoria.setText(value.getString("Kategoria"));
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
                String string  = dateFormat.format(value.getDate("Data"));
                data.setText(string);
                kwota.setText(value.getString("Kwota")+" PLN");
            }
        });

        mStorageReference = FirebaseStorage.getInstance().getReference().child("users/"+ userID + "/wydatki/"+ wID);

        try {
            final File localFile = File.createTempFile(wID,"jpg");
            mStorageReference.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Log.d("PHOTOX","Wczytano zdjecie!");
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            ((ImageView)findViewById(R.id.ticketView)).setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("PHOTOX","Nie udalo sie wczytac zdjecia!");
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(podgladWydatkuPage.this, wydatkiPage.class);
                startActivity(back);
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fStore.collection("users").document(userID).collection("wydatki").document(wID).delete();
                Intent del = new Intent(podgladWydatkuPage.this, wydatkiPage.class);
                startActivity(del);
                Toast.makeText(podgladWydatkuPage.this, "Usunięto wydatek!", Toast.LENGTH_SHORT).show();
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
                Intent a = new Intent(podgladWydatkuPage.this, wydatkiPage.class);
                startActivity(a);
                break;

            case R.id.nav_statystyki:
                Intent b = new Intent(podgladWydatkuPage.this, statystykiPage.class);
                startActivity(b);
                break;

            case R.id.nav_ustawienia:
                Intent c = new Intent(podgladWydatkuPage.this, ustawieniaPage.class);
                startActivity(c);
                break;

            case R.id.nav_start:
                Intent d = new Intent(podgladWydatkuPage.this, startPage.class);
                startActivity(d);
                break;


        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}