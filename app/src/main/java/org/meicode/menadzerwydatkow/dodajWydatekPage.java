package org.meicode.menadzerwydatkow;

import static org.meicode.menadzerwydatkow.App.CHANNEL_1_ID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.gms.tasks.Continuation;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;

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

    private FirebaseAuth fAuth;
    private FirestoreAdapter adapter;
    private String userID;


    private NotificationManagerCompat notificationManager;
    private String editTextTitle;
    private String editTextMessage;

    private FirebaseFirestore firebaseFirestore;
    public static final String TAG = "Wydatek";
    EditText nazwaWydatku,kategoria,kwota;
    Button dodajWydatekBtn;
    FirebaseFirestore fStore;
    boolean zdjecietf;
    private double dLimit;



    Spinner languageSpinner;
    // on below line we are creating a variable for our list of data to be displayed in spinner.
    String[] languages = {"Spożywcze","Podatki","Chemia"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_wydatek_page);
        TextView katwyd = findViewById(R.id.KategoriaWydatku);
        notificationManager = NotificationManagerCompat.from(this);

        //aparat
        ZdjecieButton   = findViewById(R.id.ZdjecieButton);
        imageViewPicture= findViewById(R.id.imageViewPicture);
        zdjecietf = false;


        ZdjecieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntnet = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntnet,REQUEST_CODE);
                zdjecietf=true;
            }
        });
        //

        nazwaWydatku    = findViewById(R.id.Nazwa_wydatku);
        //spinner kategoria
        Spinner spinner = (Spinner)findViewById(R.id.kategoriaspinner);
        String textSpinner = spinner.getSelectedItem().toString();
        kwota           = findViewById(R.id.Suma);
        dodajWydatekBtn = findViewById(R.id.DodajWydatekButton);
        //String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        Date currentTime = Calendar.getInstance().getTime();

        fStore = FirebaseFirestore.getInstance();
        fAuth  = FirebaseAuth.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

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
                String category = textSpinner.toString();
                String suma = kwota.getText().toString();
                Date data = currentTime;
                String urlAdres;
                userID = fAuth.getCurrentUser().getUid();
                DocumentReference docReference = fStore.collection("users").document(userID).collection("wydatki").document();
                Map<String, Object> wydatek = new HashMap<>();
                wydatek.put("Nazwa",nazwaWdk);
                wydatek.put("Kategoria", category);
                wydatek.put("Kwota",suma);
                wydatek.put("Data",data);

                if(zdjecietf){
                    //dodawanie zdjecia z imageview do bazy danych
                    imageViewPicture.setDrawingCacheEnabled(true);
                    imageViewPicture.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) imageViewPicture.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] sdata = baos.toByteArray();


                    UploadTask uploadTask = storageRef.child("users/" + userID + "/wydatki/" + data).putBytes(sdata);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.d("PHOTOX","Nie udalo sie przeslac zdjecia");
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Log.d("PHOTOX","Udało sie przesłać zdjęcie");

                        }
                    });
                    final StorageReference ref = storageRef.child("users/" + userID + "/wydatki/" + docReference.getId());
                    uploadTask = ref.putBytes(sdata);

                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return ref.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                            } else {
                                // Handle failures
                                // ...
                            }
                        }
                    });
                    urlAdres = String.valueOf(ref.getDownloadUrl());
                    wydatek.put("URL",urlAdres);

                }



                docReference.set(wydatek).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("TESTID", "Wydatek zapisany!"+docReference.getId());

                        if (getSumaWydatkow()>=getLimit())
                        {
                            sendOnChannel1();
                        }

                        startActivity(new Intent(getApplicationContext(),startPage.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TESTID", "Nie udało się zapisać wydatku!");
                    }
                });
            }
        });
        userID = fAuth.getCurrentUser().getUid();
        DocumentReference docRef = fStore.collection("users").document(userID).collection("ustawienia").document("limit");
        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                String strLimit = value.getString("maxKwota");
                dLimit = Double.parseDouble(strLimit);
            }
        });
    }
    public double getSumaWydatkow()
    {
        return 130;
    }
    public double getLimit() {
        return dLimit;
    }
    public void sendOnChannel1()
    {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Notification notification = new Notification.Builder(this, CHANNEL_1_ID)
                    .setSmallIcon(R.drawable.ic_baseline_attach_money_24)
                    .setContentTitle("Menadżer Wydatków")
                    .setContentText("Przekroczyłeś limit wydatków!")
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .build();
            notificationManager.notify(1,notification);
        }
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
