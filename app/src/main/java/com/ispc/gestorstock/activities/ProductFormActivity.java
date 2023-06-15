package com.ispc.gestorstock.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ispc.gestorstock.R;
import com.ispc.gestorstock.helpers.DatabaseHelper;
import com.ispc.gestorstock.models.Product;
import com.ispc.gestorstock.providers.AuthProvider;

public class ProductFormActivity extends AppCompatActivity {

    TextView mTitle;

    Button mAcceptButton;
    Button mCancelButton;

    EditText mNameField;
    EditText mStockField;
    EditText mPriceField;

    boolean isEditing = false;

    AuthProvider mAuth;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);

        Intent intent = getIntent();
        String message = intent.getStringExtra(HomeActivity.EDIT_PRODUCT_MESSAGE);
        Log.d("PRODUCT", "message: " + message);

        dbHelper = DatabaseHelper.getInstance(this);
        mAuth = new AuthProvider();

        mTitle = findViewById(R.id.product_form_title);

        if(message != null && !message.isBlank()){
            mTitle.setText(R.string.product_form_edit_product_title);
            isEditing = true;
        }

        mAcceptButton = findViewById(R.id.accept_button);
        mAcceptButton.setOnClickListener(view -> {
            onAcceptClick();
        });

        mCancelButton = findViewById(R.id.cancel_button);
        mCancelButton.setOnClickListener(view -> {
            NavUtils.navigateUpFromSameTask(this);
        });

        mNameField = findViewById(R.id.editTextName);
        mStockField = findViewById(R.id.editTextStock);
        mPriceField = findViewById(R.id.editTextPrice);
    }

    private void onAcceptClick(){
        String name = mNameField.getText().toString();
        String stock = mStockField.getText().toString();
        String price = mPriceField.getText().toString();
        String userID = mAuth.getUID();

        Product product = new Product();
        product.setName(name);
        product.setPrice(Float.parseFloat(price));
        product.setStock(Integer.parseInt(stock));
        product.setUserID(userID);

        dbHelper.insertProduct(product);
        NavUtils.navigateUpFromSameTask(this);
    }
}