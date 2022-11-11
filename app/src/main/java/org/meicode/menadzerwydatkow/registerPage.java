package org.meicode.menadzerwydatkow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class registerPage extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText regMail,regLogin,regPassword,regCoPassword;
    Button regBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar3;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        regMail         = findViewById(R.id.regMail);
        regLogin        = findViewById(R.id.regLogin);
        regPassword     = findViewById(R.id.regPassword);
        regCoPassword   = findViewById(R.id.regCoPassword);
        regBtn          = findViewById(R.id.regBtn);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar3 = findViewById(R.id.progressBar3);

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(),startPage.class));
            finish();
        }



        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = regMail.getText().toString().trim();
                String password = regPassword.getText().toString().trim();
                String login = regLogin.getText().toString();
                if(TextUtils.isEmpty(email)){
                    regMail.setError("Email id Required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    regPassword.setError("Password is Required");
                    return;
                }

                if (password.length() < 6) {
                    regPassword.setError("Password must be >=6 characters");
                    return;
                }


                progressBar3.setVisibility(View.VISIBLE);

                //register the user in firebase

               fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()) {
                           Toast.makeText(registerPage.this, "User Created.", Toast.LENGTH_SHORT).show();
                           userID = fAuth.getCurrentUser().getUid();
                           DocumentReference documentReference = fStore.collection("users").document(userID);
                           Map<String,Object> user = new HashMap<>();
                           user.put("login",login);
                           user.put("email",email);
                           documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void unused) {
                                   Log.d(TAG,"onSuccess: user Profile is created for "+ userID);
                               }
                           });
                           startActivity(new Intent(getApplicationContext(),startPage.class));

                       }else {
                           Toast.makeText(registerPage.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                       }

                   }
               });
            }
        });


    }
}