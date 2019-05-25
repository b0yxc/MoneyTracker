package com.example.moneytracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;

    Button registerButton, loginButton;
    EditText emailEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();
    }

    private void setupUI() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        registerButton = findViewById(R.id.register_button);
        loginButton = findViewById(R.id.login_button);

        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);

        registerButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (checkUserInput()) {
            if (v.getId() == R.id.register_button) {
                firebaseAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(MainActivity.this, ScannerActivity.class));
                                } else {
                                    Toast.makeText(MainActivity.this, getString(R.string.registration_error), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else if (v.getId() == R.id.login_button) {
                firebaseAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
//                                    firebaseDatabase.getReference().child(firebaseUser.getUid()).setValue(firebaseUser);
                                    startActivity(new Intent(MainActivity.this, ScannerActivity.class));
                                } else {
                                    Toast.makeText(MainActivity.this, getString(R.string.login_error), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
    }

    private boolean checkUserInput() {
        boolean areInputsValid = true;
        if (emailEditText.getText().toString().isEmpty()) {
            emailEditText.setError("Obavezno polje");
            areInputsValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches()) {
            emailEditText.setError("Pogrešan format e-maila");
            areInputsValid = false;
        }

        if (passwordEditText.getText().toString().isEmpty()) {
            passwordEditText.setError("Obavezno polje");
            areInputsValid = false;
        }
        return areInputsValid;
    }
}