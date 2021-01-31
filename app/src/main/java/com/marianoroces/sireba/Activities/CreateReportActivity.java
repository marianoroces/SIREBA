package com.marianoroces.sireba.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.marianoroces.sireba.R;

public class CreateReportActivity extends AppCompatActivity {

    Spinner spCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report);

        spCategories = findViewById(R.id.createRpSpinnerCategory);

        String[] categorias = {"BACHE", "ARBOL CAIDO", "OTRO"};

        ArrayAdapter<String> adapterCategorias = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categorias);

        spCategories.setAdapter(adapterCategorias);
    }
}