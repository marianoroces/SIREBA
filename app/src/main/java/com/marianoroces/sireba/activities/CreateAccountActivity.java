package com.marianoroces.sireba.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.marianoroces.sireba.R;
import com.marianoroces.sireba.model.User;
import com.marianoroces.sireba.repositories.UserRepository;
import com.marianoroces.sireba.utils.OnUserResultCallback;

import java.util.List;

public class CreateAccountActivity extends AppCompatActivity implements OnUserResultCallback {

    EditText etEmail, etPassword;
    CheckBox cbTerms;
    Button btnContinue;
    private FirebaseAuth firebaseAuth;
    private UserRepository userRepository;
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        userRepository = new UserRepository(this);

        cbTerms = findViewById(R.id.createActCBTerms);
        btnContinue = findViewById(R.id.createActButton);
        etEmail = findViewById(R.id.createActTxtEmail);
        etPassword = findViewById(R.id.createActTxtPassword);

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
                if (!etEmail.getText().toString().equals("") && !etPassword.getText().toString().equals("")) {
                    firebaseAuth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                            .addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("DEBUG", "Crear cuenta con firebase exitoso");
                                        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                                        user.setUid(currentUser.getUid());
                                        user.setEmail(etEmail.getText().toString());
                                        user.setPassword(etPassword.getText().toString());

                                        userRepository.save(user);
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

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser != null) {
            Intent intentLogin = new Intent(CreateAccountActivity.this, StartScreenActivity.class);
            startActivity(intentLogin);
        } else {
            Log.d("DEBUG", "No hay usuario autenticado");
        }
    }

    @Override
    public void onUserResult(User user) {
        this.user = user;
    }

    @Override
    public void onUserResult(List<User> userList) {

    }

    private void showErrorMessage() {
        Toast.makeText(this, "Completar todos los campos", Toast.LENGTH_LONG).show();
    }
}