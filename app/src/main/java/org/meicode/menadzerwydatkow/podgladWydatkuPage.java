package org.meicode.menadzerwydatkow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class podgladWydatkuPage extends AppCompatActivity {

    TextView nazwa,kategoria,data,kwota;
    Button deleteBtn, cancelBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    String wID;


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
                nazwa.setText(value.getString("Nazwa"));
                kategoria.setText(value.getString("Kategoria"));
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
                String string  = dateFormat.format(value.getDate("Data"));
                data.setText(string);
                kwota.setText(value.getString("Kwota"));
            }
        });









    }




}