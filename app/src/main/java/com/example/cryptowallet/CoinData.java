package com.example.cryptowallet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.cryptowallet.model.CryptoModel;

import org.json.JSONException;
import org.json.JSONObject;

public class CoinData extends AppCompatActivity {

    TextView receiver_msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_data);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        receiver_msg = (TextView)findViewById(R.id.received_value_id);
        Intent intent = getIntent();
        CryptoModel coins = (CryptoModel) intent.getSerializableExtra("coinData");
        receiver_msg.setText(coins.getName());
//        Log.d("a", str);
//
//        try {
//            JSONObject json = new JSONObject(str);
//            receiver_msg.setText(str);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }
}