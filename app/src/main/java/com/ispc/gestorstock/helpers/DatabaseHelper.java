package com.ispc.gestorstock.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ispc.gestorstock.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper mInstance = null;

    private static final String DATABASE_NAME = "ProductsDB";
    private static final String DATABASE_TABLE = "products";
    private static final int DATABASE_VERSION = 1;

    public static DatabaseHelper getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }
    private DatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + DATABASE_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, userID TEXT, name TEXT, stock INTEGER, price FLOAT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Usuarios");
        sqLiteDatabase.execSQL("CREATE TABLE " + DATABASE_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, userID TEXT, name TEXT, stock INTEGER, price FLOAT)");
    }

    public void insertProduct(Product product){
        if(mInstance == null) return;

        var db = mInstance.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userID", product.getUserID());
        values.put("name", product.getName());
        values.put("stock", product.getStock());
        values.put("price", product.getPrice());

        db.insert(DATABASE_TABLE, null, values);
        db.close();
    }

    public List<Product> getProductsOfUser(String userID){
        List<Product> products = new ArrayList<>();
        if(mInstance == null) return null;
        Log.d("DB HELPER", "LOAD - userID::" + userID);

        var db = mInstance.getWritableDatabase();
        String[] selectionValues = {userID};
        Cursor cursor = db.query(
                DATABASE_TABLE,
                null,
                "userID = ?",
                selectionValues,
                null,
                null,
                "name ASC"
        );

        while(cursor.moveToNext()){
            var _id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            var _userID = cursor.getString(cursor.getColumnIndexOrThrow("userID"));
            var _name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            var _stock = cursor.getInt(cursor.getColumnIndexOrThrow("stock"));
            var _price = cursor.getFloat(cursor.getColumnIndexOrThrow("price"));

            Product p = new Product(_id, _userID, _name, _price, _stock);
            products.add(p);
        }

        Log.d("DB HELPER", "LOAD - products::" + products.toString());

        cursor.close();
        return products;
    }

    public Product getProductById(int id){
        if(mInstance == null) return null;

        var db = mInstance.getWritableDatabase();
        String[] selectionValues = {String.valueOf(id)};
        Cursor cursor = db.query(
                DATABASE_TABLE,
                null,
                "id = ?",
                selectionValues,
                null,
                null,
                "id ASC"
        );

        cursor.moveToFirst();
        var _id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        var _userID = cursor.getString(cursor.getColumnIndexOrThrow("userID"));
        var _name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        var _stock = cursor.getInt(cursor.getColumnIndexOrThrow("stock"));
        var _price = cursor.getFloat(cursor.getColumnIndexOrThrow("price"));

        cursor.close();

        return new Product(_id, _userID, _name, _price, _stock);
    }

    public void deleteProduct(int id){
        if(mInstance == null) return;
        var db = mInstance.getWritableDatabase();
        String[] values = {String.valueOf(id)};
        db.delete(DATABASE_TABLE, "id = ?", values);
    }

    public void updateProduct(Product product){
        if(mInstance == null) return;
        var db = mInstance.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("userID", product.getUserID());
        values.put("name", product.getName());
        values.put("stock", product.getStock());
        values.put("price", product.getPrice());

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(product.getId())};
        db.update(DATABASE_TABLE, values, selection, selectionArgs);
    }
}
