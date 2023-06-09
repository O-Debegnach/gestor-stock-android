package com.ispc.gestorstock.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ispc.gestorstock.R;
import com.ispc.gestorstock.providers.AuthProvider;

public class LoginActivity extends AppCompatActivity {

    Button mLoginButton;
    EditText mEmailField;
    EditText mPasswordField;
    AuthProvider mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = new AuthProvider();

        mLoginButton = findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(view -> {
            login();
        });

        mEmailField = findViewById(R.id.editTextEmail);
        mPasswordField = findViewById(R.id.editTextPassword);
    }

    private void login() {
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        if(email.isBlank() || password.isBlank()){
            Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_LONG).show();
        }

        Log.d("LOGIN", email + ":::::" + password);
        mAuth.loginWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

}