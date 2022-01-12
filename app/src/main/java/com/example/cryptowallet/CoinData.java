package com.example.cryptowallet;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cryptowallet.model.CryptoModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ncorti.slidetoact.SlideToActView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CoinData extends AppCompatActivity {

    TextView coinPrice, totalPrice, balance;
    private EditText editCoins;
    String coinName;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_data);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editCoins = (EditText) findViewById(R.id.editCoins);
        totalPrice = (TextView) findViewById(R.id.totalPrice);
        coinPrice = (TextView) findViewById(R.id.coinPrice);
        balance = (TextView) findViewById(R.id.balance);
        SlideToActView sta = (SlideToActView) findViewById(R.id.example);

        Intent intent = getIntent();
        CryptoModel coins = (CryptoModel) intent.getSerializableExtra("coinData");
        coinName = coins.getName();
        coinPrice.setText("$" + coins.getPrice());
        buyCoins(String.valueOf(coins.getPrice()));

        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        String bal = task.getResult().getString("Amount");
                        balance.setText("Balance" + "  " + bal);
                    }
                });

        sta.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSlideComplete(SlideToActView slideToActView) {
                if (editCoins.getText().toString().isEmpty()) {
                    Toast.makeText(CoinData.this, "Please enter a valid number of coins", Toast.LENGTH_SHORT).show();
                } else {
                    addToDatabase(coins.getName(), String.valueOf(coins.getPrice()), coins.getSymbol());
                }
            }
        });
    }


    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radio_button_1:
                if (checked)
                    editCoins.setText("100");
                break;
            case R.id.radio_button_2:
                if (checked)
                    editCoins.setText("200");
                break;
            case R.id.radio_button_3:
                if (checked)
                    editCoins.setText("300");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
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
                    totalPrice.setText(String.format("%.09f", (Float.parseFloat(editCoins.getText().toString()) / tp)));
                }
                if (TextUtils.isEmpty(editCoins.getText().toString())) {
                    totalPrice.setText("");
                }
            }
        });
    }

    String uniqueID = UUID.randomUUID().toString();

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addToDatabase(String coinName, String buyPrice, String coinSymbol) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String bal = balance.getText().toString().replaceAll("[^0-9]", "");
        float amt = Float.parseFloat(bal) - Float.parseFloat(editCoins.getText().toString());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        if (amt > 10 && Float.parseFloat(editCoins.getText().toString()) < Float.parseFloat(bal)) {
            DocumentReference docRef = db.collection("users").document(user.getUid()).collection("coins").document(uniqueID);
            DocumentReference transRef = db.collection("users").document(user.getUid()).collection("transactions").document(uniqueID);
            Map<String, Object> use = new HashMap<>();
            use.put("name", coinName);
            use.put("price", buyPrice);
            use.put("id", uniqueID);
            use.put("symbol", coinSymbol);
            use.put("type", "buy");
            use.put("count", totalPrice.getText().toString());
            use.put("total", editCoins.getText().toString());
            use.put("time", String.valueOf(dtf.format(now)));
            docRef.set(use);
            transRef.set(use);

            db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .update("Amount", String.valueOf(amt));
        } else {
            Toast.makeText(CoinData.this, "Insufficient Funds", Toast.LENGTH_SHORT).show();
        }
    }
}