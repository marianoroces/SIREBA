package com.marianoroces.sireba.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.marianoroces.sireba.R;

public class LogInActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin, btnCreateAccount;
    private FirebaseAuth firebaseAuth;
    private final static String NOTIFICATION_CHANNEL_ID = "10001";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        btnLogin = findViewById(R.id.loginButton);
        btnCreateAccount = findViewById(R.id.loginCreateAccountBtn);
        etEmail = findViewById(R.id.loginTxtEmail);
        etPassword = findViewById(R.id.loginTxtPassword);

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
                if (!etEmail.getText().toString().equals("") && !etPassword.getText().toString().equals("")) {
                    firebaseAuth.signInWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                            .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("DEBUG", "Crear cuenta con firebase exitoso");
                                        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                                        updateUI(currentUser);
                                    } else {
                                        Log.d("DEBUG", "Crear cuenta con firebase fallido");
                                        updateUI(null);
                                    }
                                }
                            });
                } else {
                    showErrorMessage();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        createNotificationChannel();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser != null) {
            Log.d("DEBUG", "Se autentico el usuario: "+currentUser.getUid());
            Intent intentLogin = new Intent(LogInActivity.this, StartScreenActivity.class);
            startActivity(intentLogin);
        } else {
            Log.d("DEBUG", "No hay usuario autenticado");
        }
    }

    private void showErrorMessage() {
        Toast.makeText(this, "Completar todos los campos", Toast.LENGTH_LONG).show();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}