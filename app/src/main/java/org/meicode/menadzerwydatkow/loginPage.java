package org.meicode.menadzerwydatkow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import  android.content.Intent;

public class loginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        final EditText Login = findViewById(R.id.entryLogin);
        final EditText Password = findViewById(R.id.entryPassword);

        final Button LoginBtn = findViewById(R.id.zalogujBtn);
        final Button RegisterBtn = findViewById(R.id.zarejestrujBtn);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String LoginTxt = Login.getText().toString();
                final String PasswordTxt = Password.getText().toString();

                if(LoginTxt.isEmpty() || PasswordTxt.isEmpty()){
                    Toast.makeText(loginPage.this, "Wprowadź dane logowania!", Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(new Intent(loginPage.this, startPage.class));
                }
            }
        });
    }
}