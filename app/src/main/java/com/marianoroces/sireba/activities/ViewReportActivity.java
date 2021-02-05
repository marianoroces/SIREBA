package com.marianoroces.sireba.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.marianoroces.sireba.R;

public class ViewReportActivity extends AppCompatActivity {

    EditText etDate, etCategory, etDescription, etLocation;
    Button btnViewOnMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etDate = findViewById(R.id.viewRpTxtDate);
        etCategory = findViewById(R.id.viewRpTxtCategory);
        etDescription = findViewById(R.id.viewRpTxtDescription);
        etLocation = findViewById(R.id.viewRpTxtLocation);
        btnViewOnMap = findViewById(R.id.viewRpButton);

        etDate.setText(getIntent().getStringExtra("date"));
        etCategory.setText(getIntent().getStringExtra("category"));
        etDescription.setText(getIntent().getStringExtra("description"));
        etLocation.setText(getIntent().getStringExtra("location"));
    }
}