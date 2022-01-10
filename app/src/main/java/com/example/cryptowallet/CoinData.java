package com.example.cryptowallet;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cryptowallet.model.CryptoModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ncorti.slidetoact.SlideToActView;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CoinData extends AppCompatActivity {

    TextView coinPrice, totalPrice;
    private EditText editCoins;
    String coinName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_data);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editCoins = (EditText) findViewById(R.id.editCoins);
        totalPrice = (TextView) findViewById(R.id.totalPrice);
        coinPrice = (TextView) findViewById(R.id.coinPrice);
        SlideToActView sta = (SlideToActView) findViewById(R.id.example);


        Intent intent = getIntent();
        CryptoModel coins = (CryptoModel) intent.getSerializableExtra("coinData");
        coinName = coins.getName();
        coinPrice.setText("$" + coins.getPrice());
        buyCoins(String.valueOf(coins.getPrice()));

        sta.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideToActView slideToActView) {
                addToDatabase(coins.getName(), String.valueOf(coins.getPrice()), coins.getSymbol());
                if (totalPrice.getText().toString().equals("0")) {
                    Toast.makeText(CoinData.this, "Please enter a valid number of coins", Toast.LENGTH_SHORT).show();
                } else {
                    // add to db
                    addToDatabase(coins.getName(), String.valueOf(coins.getPrice()), coins.getSymbol());
                }
            }
        });
    }

    private void buyCoins(String coinPrice) {


        float tp = Float.parseFloat(coinPrice);
        editCoins.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(editCoins.getText().toString())) {
                    totalPrice.setText(String.format("%.09f",(Float.parseFloat(editCoins.getText().toString()) / tp)));
                }
                if (TextUtils.isEmpty(editCoins.getText().toString())) {
                    totalPrice.setText("");
                }
            }
        });
    }

    String uniqueID = UUID.randomUUID().toString();

    private void addToDatabase(String coinName, String buyPrice, String coinSymbol) {
//        add the coin to the database in separate document
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference docRef = db.collection("users").document(user.getUid()).collection("coins").document(uniqueID);
        Map<String, Object> use = new HashMap<>();
        use.put("name", coinName);
        use.put("price", buyPrice);
        use.put("id", uniqueID);
        use.put("symbol", coinSymbol);
        use.put("type", "buy");
        use.put("count", totalPrice.getText().toString());
        use.put("total", editCoins.getText().toString());
        use.put("time", FieldValue.serverTimestamp());
        docRef.set(use);
    }
}