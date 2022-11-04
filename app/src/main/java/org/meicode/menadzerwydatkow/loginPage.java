package org.meicode.menadzerwydatkow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import  android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginPage extends AppCompatActivity {

    EditText Login, Password;
    Button LoginBtn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        Login           = findViewById(R.id.Login);
        Password        = findViewById(R.id.Password);
        fAuth           = FirebaseAuth.getInstance();
        LoginBtn        = findViewById(R.id.zalogujBtn);


        final Button RegisterBtn = findViewById(R.id.zarejestrujBtn);


        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Login.getText().toString().trim();
                String password = Password.getText().toString().trim();

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(loginPage.this, "Wprowadź dane logowania!", Toast.LENGTH_SHORT).show();
                }

                //authenticate the user

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           Toast.makeText(loginPage.this, "Zalogowano", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(getApplicationContext(),startPage.class));
                       }else {
                           Toast.makeText(loginPage.this, "Błąd logowania! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                       }

                    }
                });


            }
        });







        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginPage.this, registerPage.class));
            }
        });

    }
}