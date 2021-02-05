package com.marianoroces.sireba.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.marianoroces.sireba.R;
import com.marianoroces.sireba.model.Category;
import com.marianoroces.sireba.repositories.CategoryRepository;
import com.marianoroces.sireba.utils.OnCategoryResultCallback;

import java.util.ArrayList;
import java.util.List;

public class CreateReportActivity extends AppCompatActivity implements OnCategoryResultCallback {

    EditText etDate;
    EditText etDescription;
    EditText etLocation;
    Button btnSelectDate;
    Button btnSelectLocation;
    Button btnTakePicture;
    Button btnContinue;
    Spinner spCategories;
    List<String> categoryNames;
    CategoryRepository categoryRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report);

        categoryRepository = new CategoryRepository(this);

        initializeComponents();

        categoryNames = new ArrayList<String>();

        categoryRepository.getAll();
    }

    @Override
    public void onResult(List<Category> categoriesList) {
        for(Category aux : categoriesList) {
            categoryNames.add(aux.getCategoryName());
        }

        ArrayAdapter<String> adapterCategorias = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categoryNames);

        spCategories.setAdapter(adapterCategorias);

        adapterCategorias.notifyDataSetChanged();
    }

    @Override
    public void onResult(Category category) {

    }

    private void initializeComponents(){
        etDate = findViewById(R.id.createRpTxtDate);
        etDescription = findViewById(R.id.createRpTxtDescription);
        etLocation = findViewById(R.id.createRpTxtLocation);
        spCategories = findViewById(R.id.createRpSpinnerCategory);
        btnSelectDate = findViewById(R.id.createRpBtnDate);
        btnContinue = findViewById(R.id.createRpBtnContinue);
        btnSelectLocation = findViewById(R.id.createRpBtnSelectOnMap);
        btnTakePicture = findViewById(R.id.createRpBtnTakePicture);
    }
}