package com.marianoroces.sireba.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.marianoroces.sireba.R;

public class LogInActivity extends AppCompatActivity {

    Button btnLogin;
    Button btnCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

}