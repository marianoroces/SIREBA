package com.marianoroces.sireba.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.marianoroces.sireba.R;

public class ViewReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}