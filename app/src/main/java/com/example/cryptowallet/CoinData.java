package com.example.cryptowallet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.cryptowallet.model.CryptoModel;
import com.ncorti.slidetoact.SlideToActView;

import org.json.JSONException;
import org.json.JSONObject;

public class CoinData extends AppCompatActivity {

    TextView coinName,coinPrice;
    SlideToActView sta = (SlideToActView) findViewById(R.id.example);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_data);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        coinName = (TextView)findViewById(R.id.coinName);
        coinPrice = (TextView)findViewById(R.id.coinPrice);
        Intent intent = getIntent();
        CryptoModel coins = (CryptoModel) intent.getSerializableExtra("coinData");
        coinName.setText(coins.getName());
        coinPrice.setText(String.valueOf(coins.getPrice()));
    }
}