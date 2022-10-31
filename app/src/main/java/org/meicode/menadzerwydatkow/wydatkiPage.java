package org.meicode.menadzerwydatkow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class wydatkiPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wydatki_page);

        //NAWIGACJA
        final TextView Start = findViewById(R.id.navStart);
        final TextView Wydatki = findViewById(R.id.navWydatki);
        final TextView Statystyki = findViewById(R.id.navStatystyki);
        final TextView Ustawienia = findViewById(R.id.navUstawienia);

        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(wydatkiPage.this, startPage.class));
            }
        });
        Wydatki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(wydatkiPage.this, wydatkiPage.class));
            }
        });
        Statystyki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(wydatkiPage.this, statystykiPage.class));
            }
        });
        Ustawienia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(wydatkiPage.this, ustawieniaPage.class));
            }
        });
        //KONIEC NAWIGACJI
    }
}