package com.ispc.gestorstock.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ispc.gestorstock.R;
import com.ispc.gestorstock.dialogs.SignOutDialog;
import com.ispc.gestorstock.models.Product;
import com.ispc.gestorstock.providers.AuthProvider;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    SignOutDialog mDialog;
    AuthProvider mAuth;
    List<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDialog = new SignOutDialog();
        mAuth = new AuthProvider();

        for (int i = 0; i < 300; i++){
            products.add(new Product("1", "Test " + i, 10, 10));
        }
        Log.d("HOME", products.toString());
        loadProducts();

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

    public void loadProducts(){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_home, null);

        TableLayout table = (TableLayout) v.findViewById(R.id.table_products);

        TableRow tbRow = new TableRow(this);
        tbRow.setOrientation(LinearLayout.VERTICAL);

        for (Product p :
                products) {
            TableRow tr = new TableRow(this);
            TextView tvName = new TextView(this);
            tvName.setText(p.getName());
            tr.addView(tvName);

            TextView tvStock = new TextView(this);
            tvStock.setText(String.valueOf(p.getStock()));
            tvStock.setGravity(Gravity.CENTER);
            tr.addView(tvStock);

            TextView tvPrice = new TextView(this);
            tvPrice.setText("$ " + String.valueOf(p.getPrice()));
            tvPrice.setGravity(Gravity.END);
            tr.addView(tvPrice);

            table.addView(tr);
        }

        // Display the view
        setContentView(v);

    }


}