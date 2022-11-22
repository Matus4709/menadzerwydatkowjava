package org.meicode.menadzerwydatkow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class podgladWydatkuPage extends AppCompatActivity {

    TextView nazwa,kategoria,data,kwota;
    Button deleteBtn, cancelBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

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



        DocumentReference docRef = fStore.collection("users").document(userID).collection("wydatki").document();

        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                nazwa.setText(value.getString("Nazwa"));
                kategoria.setText(value.getString("Kategoria"));
               // data.setText(value.getData("Data").toString());
                kwota.setText(value.getString("Kwota"));
            }
        });









    }
}