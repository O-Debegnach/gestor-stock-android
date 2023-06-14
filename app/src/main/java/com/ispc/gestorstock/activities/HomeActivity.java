package com.ispc.gestorstock.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDialog = new SignOutDialog();
        mAuth = new AuthProvider();

        for (int i = 0; i < 300; i++){
            products.add(new Product(String.valueOf(i), "Test " + i, 10, 10));
        }
        Log.d("HOME", products.toString());
        loadProducts();

        mAddProductButton = findViewById(R.id.add_product_button);
        mAddProductButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, ProductFormActivity.class);
            startActivity(intent);
        });

        mDeleteProductButton = findViewById(R.id.delete_product_button);

        mEditProductButton = findViewById(R.id.edit_product_button);
        mEditProductButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, ProductFormActivity.class);
            intent.putExtra(EDIT_PRODUCT_MESSAGE, selectedProduct.getId());

            startActivity(intent);
        });
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
        if(selectedRow != null){
            selectedRow.setBackground(null);
        }
        tr.setBackground(getDrawable(R.drawable.table_selected_row_background));
        mDeleteProductButton.setVisibility(View.VISIBLE);
        mEditProductButton.setVisibility(View.VISIBLE);
        selectedRow = tr;
        selectedProduct = product;
    }
}