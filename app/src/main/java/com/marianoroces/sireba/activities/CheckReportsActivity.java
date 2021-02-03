package com.marianoroces.sireba.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.marianoroces.sireba.R;

public class CheckReportsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_reports);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}