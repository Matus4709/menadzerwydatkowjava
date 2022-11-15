package org.meicode.menadzerwydatkow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class dodajWydatekPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //aparat
    Button ZdjecieButton;
    ImageView imageViewPicture;
    private static final int REQUEST_CODE =22;
    //

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    public static final String TAG = "Wydatek";
    EditText nazwaWydatku,kategoria,kwota;
    Button dodajWydatekBtn;
    FirebaseFirestore fStore;
    String userID;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_wydatek_page);

        //aparat
        ZdjecieButton   = findViewById(R.id.ZdjecieButton);
        imageViewPicture= findViewById(R.id.imageViewPicture);

        ZdjecieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntnet = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntnet,REQUEST_CODE);
            }
        });
        //

        nazwaWydatku    = findViewById(R.id.Nazwa_wydatku);
        kategoria       = findViewById(R.id.kategoria);
        kwota           = findViewById(R.id.Suma);
        dodajWydatekBtn = findViewById(R.id.DodajWydatekButton);
        //String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        Date currentTime = Calendar.getInstance().getTime();

        fStore = FirebaseFirestore.getInstance();
        fAuth  = FirebaseAuth.getInstance();

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

        //Dodawanie wydatku
        dodajWydatekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nazwaWdk = nazwaWydatku.getText().toString();
                String category = kategoria.getText().toString();
                String suma = kwota.getText().toString();
                Date data = currentTime;
                userID = fAuth.getCurrentUser().getUid();
                DocumentReference docReference = fStore.collection("users").document(userID).collection("wydatki").document();
                Map<String, Object> wydatek = new HashMap<>();
                wydatek.put("Nazwa",nazwaWdk);
                wydatek.put("Kategoria", category);
                wydatek.put("Kwota",suma);
                wydatek.put("Data",data);

                docReference.set(wydatek).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "Wydatek zapisany!");
                        startActivity(new Intent(getApplicationContext(),startPage.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Nie udało się zapisać wydatku!");
                    }
                });
            }
        });



    }

    //aparat
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageViewPicture.setImageBitmap(photo);
        }else {
            Toast.makeText(this, "Wyowałanie aparatu nie powiodło się!",Toast.LENGTH_SHORT).show();
            super.onActivityResult(requestCode, resultCode, data);
        }
    }//koniec aparat

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
                Intent a = new Intent(dodajWydatekPage.this, wydatkiPage.class);
                startActivity(a);
                break;

            case R.id.nav_statystyki:
                Intent b = new Intent(dodajWydatekPage.this, statystykiPage.class);
                startActivity(b);
                break;

            case R.id.nav_ustawienia:
                Intent c = new Intent(dodajWydatekPage.this, ustawieniaPage.class);
                startActivity(c);
                break;

            case R.id.nav_start:
                Intent d = new Intent(dodajWydatekPage.this, startPage.class);
                startActivity(d);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}
