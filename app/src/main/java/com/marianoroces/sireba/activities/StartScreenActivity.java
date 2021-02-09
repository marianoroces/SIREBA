package com.marianoroces.sireba.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.marianoroces.sireba.R;

public class StartScreenActivity extends AppCompatActivity {

    Button btnCreateReport, btnCheckReports, btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        btnCreateReport = findViewById(R.id.startScreenBtnCreate);
        btnCheckReports = findViewById(R.id.startScreenBtnCheck);
        btnLogOut = findViewById(R.id.startScreenBtnLogOut);

        btnCreateReport.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCreateReport = new Intent(StartScreenActivity.this, CreateReportActivity.class);
                startActivity(intentCreateReport);
            }
        });

        btnCheckReports.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCreateReport = new Intent(StartScreenActivity.this, CheckReportsActivity.class);
                startActivity(intentCreateReport);
            }
        });

        btnLogOut.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view){
                FirebaseAuth.getInstance().signOut();
                Intent logOutIntent = new Intent(StartScreenActivity.this, LogInActivity.class);
                startActivity(logOutIntent);
            }
        });
    }
}