package com.example.cryptowallet.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class TransactionModel implements Serializable {

    private String name, symbol, price, id;

    public TransactionModel() {
    }

    public TransactionModel(String name, String symbol, String price, String id) {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @NonNull
    @Override
    public String toString() {
        return this.name + " " + this.symbol + " " + this.price + " " + this.id;
    }

}
