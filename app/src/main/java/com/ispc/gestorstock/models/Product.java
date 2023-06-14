package com.ispc.gestorstock.models;

public class Product {
    int id;
    String userID;
    String name;
    float price;
    int stock;

    public Product(){}

    public Product(int id, String userID, String name, float price, int stock) {
        this.id = id;
        this.userID = userID;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

}
