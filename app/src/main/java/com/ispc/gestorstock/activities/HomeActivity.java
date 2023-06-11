package com.ispc.gestorstock.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.ispc.gestorstock.R;
import com.ispc.gestorstock.dialogs.SignOutDialog;
import com.ispc.gestorstock.providers.AuthProvider;

public class HomeActivity extends AppCompatActivity {

    SignOutDialog mDialog;
    AuthProvider mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDialog = new SignOutDialog();
        mAuth = new AuthProvider();
    }

    @Override
    public void onBackPressed() {
        Log.d("HOME", "Back pressed");
        mDialog.show(getSupportFragmentManager(), "signout dialog");
    }

    public void doPositiveClick(){
        mAuth.logout();
        finish();
    }
    public void doNegativeClick(){
        mDialog.dismiss();
    }
}