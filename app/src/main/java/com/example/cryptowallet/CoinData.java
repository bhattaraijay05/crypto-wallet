package com.example.cryptowallet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class CoinData extends AppCompatActivity {

    TextView receiver_msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_data);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        receiver_msg = (TextView)findViewById(R.id.received_value_id);
        Intent intent = getIntent();

        String str = intent.getStringExtra("coinData");

        receiver_msg.setText(str);
    }
}