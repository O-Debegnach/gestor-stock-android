package com.ispc.gestorstock.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ispc.gestorstock.R;
import com.ispc.gestorstock.dialogs.SignOutDialog;
import com.ispc.gestorstock.helpers.DatabaseHelper;
import com.ispc.gestorstock.models.Product;
import com.ispc.gestorstock.providers.AuthProvider;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    public static final String EDIT_PRODUCT_MESSAGE = "EDIT_PRODUCT";

    TableRow selectedRow;
    Product selectedProduct;
    SignOutDialog mDialog;
    AuthProvider mAuth;
    List<Product> products = new ArrayList<>();
    ImageButton mAddProductButton;
    ImageButton mEditProductButton;
    ImageButton mDeleteProductButton;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDialog = new SignOutDialog();
        mAuth = new AuthProvider();
        dbHelper = DatabaseHelper.getInstance(this);
        loadProducts();

        mAddProductButton = findViewById(R.id.add_product_button);
        mAddProductButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, ProductFormActivity.class);
            startActivity(intent);
            Log.d("HOME", "ADD PRODUCT CLICKED");
        });

        mDeleteProductButton = findViewById(R.id.delete_product_button);

        mEditProductButton = findViewById(R.id.edit_product_button);
        mEditProductButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, ProductFormActivity.class);
            intent.putExtra(EDIT_PRODUCT_MESSAGE, String.valueOf(selectedProduct.getId()));

            startActivity(intent);
        });

        mDeleteProductButton.setOnClickListener(view -> {
            deleteProductFromDataBase();
        });
    }

    private void deleteProductFromDataBase() {
        dbHelper.deleteProduct(selectedProduct.getId());
        loadProducts();
    }

    @Override
    protected void onRestart() {
        Log.d("HOME", "RESTART");
        loadProducts();
        Log.d("HOME", products.toString());
        super.onRestart();
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

        products =  dbHelper.getProductsOfUser(mAuth.getUID());
        for (Product p :
                products) {
            TableRow tr = getTableRow(p);
            table.addView(tr);
        }

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
        textView.setPadding(dpToPixel(15), dpToPixel(5), dpToPixel(15), dpToPixel(5));

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

        tr.setOnClickListener(view -> {
            Log.d("TABLE", "Item clickeado ::::::: " + product.getName());
            selectProduct(tr, product);
        });

        return tr;
    }

    private void selectProduct(TableRow tr, Product product){
        Log.d("HOME", "TABLE SELECT PRODUCT");
        if(selectedRow != null){
            selectedRow.setBackground(null);
        }
        tr.setBackground(getDrawable(R.drawable.table_selected_row_background));

        Activity act = (Activity)this;
        act.runOnUiThread(new Runnable(){
            @Override
            public void run() {
                mDeleteProductButton.setVisibility(View.VISIBLE);
                mEditProductButton.setVisibility(View.VISIBLE);
            } });

        Log.d("HOME", "BUTTON VISIBILITY:::" + mEditProductButton.getVisibility());

        selectedRow = tr;
        selectedProduct = product;
    }
}