package com.marianoroces.sireba.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.marianoroces.sireba.R;

public class CreateAccountActivity extends AppCompatActivity {

    CheckBox cbTerms;
    Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cbTerms = findViewById(R.id.createActCBTerms);
        btnContinue = findViewById(R.id.createActButton);

        cbTerms.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                switch (compoundButton.getId()) {
                    case R.id.createActCBTerms:
                        if (isChecked) {
                            btnContinue.setEnabled(true);
                        } else {
                            btnContinue.setEnabled(false);
                        }
                        break;
                }
            }
        });

        btnContinue.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogIn = new Intent(CreateAccountActivity.this, StartScreenActivity.class);
                startActivity(intentLogIn);
            }
        });
    }
}