package com.ispc.gestorstock.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ispc.gestorstock.R;
import com.ispc.gestorstock.helpers.StringHelpers;
import com.ispc.gestorstock.models.User;
import com.ispc.gestorstock.providers.AuthProvider;
import com.ispc.gestorstock.providers.UserProvider;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    Button mSignUpButton;
    EditText mNameField;
    EditText mEmailField;
    EditText mPasswordField;
    EditText mConfirmPasswordField;

    AuthProvider mAuth;
    UserProvider mUserProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mSignUpButton = findViewById(R.id.sign_up_button);
        mSignUpButton.setOnClickListener(view -> {
            signUp();
        });

        mNameField = findViewById(R.id.editTextName);
        mEmailField = findViewById(R.id.editTextEmail);
        mPasswordField = findViewById(R.id.editTextPassword);
        mConfirmPasswordField = findViewById(R.id.editTextConfirmPassword);

        mAuth = new AuthProvider();
        mUserProvider = new UserProvider();
    }


    public void signUp() {
        String name = mNameField.getText().toString();
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();
        String confirmPassword = mConfirmPasswordField.getText().toString();

        if(name.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!StringHelpers.isValidEmail(email)) {
            Toast.makeText(this, "El correo tiene un formato invalido", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.length() < 6){
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!password.equals(confirmPassword)){
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUser(name, email, password).addOnCompleteListener(task -> {
            String message = task.isSuccessful() ? "Usuario creado correctamente" : "Ha ocurrido un error";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}