package com.example.cryptowallet.model;

public class CryptoData {

    String name, symbol;
    public CryptoData(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }
    public String getSymbol() {
        return symbol;
    }
}
