package com.example.cryptowallet.model;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;

import java.io.Serializable;

public class TransactionModel implements Serializable {

    private String name, symbol, price, id, type, count, total, time;

    public TransactionModel() {

    }

    public TransactionModel(String name, String symbol, String price, String id, String type, String count, String total, String time) {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
        this.id = id;
        this.type = type;
        this.count = count;
        this.total = total;
        this.time = time;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
