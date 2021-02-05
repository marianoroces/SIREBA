package com.marianoroces.sireba.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.marianoroces.sireba.R;

public class LogInActivity extends AppCompatActivity {

    Button btnLogin, btnCreateAccount;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        signInAnonimously();

        btnLogin = findViewById(R.id.loginButton);
        btnCreateAccount = findViewById(R.id.loginCreateAccountBtn);

        btnCreateAccount.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCreateAccount = new Intent(LogInActivity.this, CreateAccountActivity.class);
                startActivity(intentCreateAccount);
            }
        });

        btnLogin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogin = new Intent(LogInActivity.this, StartScreenActivity.class);
                startActivity(intentLogin);
            }
        });
    }

    private void signInAnonimously() {
        firebaseAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.d("DEBUG", "Firebase log in exitoso");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                        } else {
                            Log.d("DEBUG", "Firebase log in fallido");
                            Log.d("DEBUG", task.getException().toString());
                        }
                    }
                });
    }

}