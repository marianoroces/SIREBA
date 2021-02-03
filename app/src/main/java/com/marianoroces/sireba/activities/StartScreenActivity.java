package com.marianoroces.sireba.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.marianoroces.sireba.R;

public class StartScreenActivity extends AppCompatActivity {

    Button btnCreateReport;
    Button btnCheckReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnCreateReport = findViewById(R.id.startScreenBtnCreate);
        btnCheckReports = findViewById(R.id.startScreenBtnCheck);

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
    }
}