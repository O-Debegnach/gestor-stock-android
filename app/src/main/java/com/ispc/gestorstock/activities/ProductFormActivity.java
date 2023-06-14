package com.ispc.gestorstock.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ispc.gestorstock.R;

public class ProductFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);

        Intent intent = getIntent();
        String message = intent.getStringExtra(HomeActivity.EDIT_PRODUCT_MESSAGE);
        Log.d("PRODUCT", "message: " + message);
    }
}