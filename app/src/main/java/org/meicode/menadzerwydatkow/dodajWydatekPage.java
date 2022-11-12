package org.meicode.menadzerwydatkow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class dodajWydatekPage extends AppCompatActivity {

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

        nazwaWydatku    = findViewById(R.id.Nazwa_wydatku);
        kategoria       = findViewById(R.id.kategoria);
        kwota           = findViewById(R.id.Suma);
        dodajWydatekBtn = findViewById(R.id.DodajWydatekButton);


        fStore = FirebaseFirestore.getInstance();
        fAuth  = FirebaseAuth.getInstance();

        //NAWIGACJA
        final TextView Start = findViewById(R.id.navStart);
        final TextView Wydatki = findViewById(R.id.navWydatki);
        final TextView Statystyki = findViewById(R.id.navStatystyki);
        final TextView Ustawienia = findViewById(R.id.navUstawienia);

        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dodajWydatekPage.this, startPage.class));
            }
        });
        Wydatki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dodajWydatekPage.this, wydatkiPage.class));
            }
        });
        Statystyki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dodajWydatekPage.this, statystykiPage.class));
            }
        });
        Ustawienia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dodajWydatekPage.this, ustawieniaPage.class));
            }
        });
        //KONIEC NAWIGACJI


        //Dodawanie wydatku
        dodajWydatekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nazwaWdk = nazwaWydatku.getText().toString();
                String category = kategoria.getText().toString();
                String suma = kwota.getText().toString();
                userID = fAuth.getCurrentUser().getUid();
                DocumentReference docReference = fStore.collection("users").document(userID).collection("wydatki").document();
                Map<String, Object> wydatek = new HashMap<>();
                wydatek.put("Nazwa",nazwaWdk);
                wydatek.put("Kategoria", category);
                wydatek.put("Kwota",suma);
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
}