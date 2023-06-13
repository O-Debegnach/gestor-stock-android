package com.ispc.gestorstock.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

    public void loadProducts(){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_home, null);

        TableLayout table = (TableLayout) v.findViewById(R.id.table_products);


        for (Product p :
                products) {
            TableRow tr = getTableRow(p);
            table.addView(tr);
        }

        // Display the view
        setContentView(v);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_item_sign_out) {
            mDialog.show(getSupportFragmentManager(), "signout dialog");
        }
        return super.onOptionsItemSelected(item);
    }

    public void doPositiveClick(){
        mAuth.logout();
        finish();
    }

    public void doNegativeClick(){
        mDialog.dismiss();
    }

    public int dpToPixel(int dp){
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp*scale + 0.5f);
    }

    private TextView getTableCell(String content){
        TextView textView = new TextView(this);
        textView.setText(content);
        textView.setPadding(dpToPixel(15), 0, dpToPixel(15), 0);

        return textView;
    }

    private TextView getTableCell(int content){
        return getTableCell(String.valueOf(content));
    }

    private TextView getTableCell(float content){
        return getTableCell(String.valueOf(content));
    }

    private TableRow getTableRow(Product product){
        TableRow tr = new TableRow(this);
        TextView tvName = getTableCell(product.getName());
        tr.addView(tvName);

        TextView tvStock = getTableCell(product.getStock());
        tvStock.setGravity(Gravity.CENTER);
        tr.addView(tvStock);

        TextView tvPrice = getTableCell("$ " + String.valueOf(product.getPrice()));
        tvPrice.setGravity(Gravity.END);
        tr.addView(tvPrice);

        return tr;
    }
}