package com.example.cryptowallet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cryptowallet.custom.CircularTextView;
import com.example.cryptowallet.model.CryptoModel;
import com.example.cryptowallet.model.TransactionModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ncorti.slidetoact.SlideToActView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class SellFragment extends AppCompatActivity {

    CircularTextView my_letter;
    TextView coinName, coinCount, amount, time;
    String userBalance;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sell_coins);

        SlideToActView sta = (SlideToActView) findViewById(R.id.example);

        Intent intent = getIntent();
        TransactionModel coins = (TransactionModel) intent.getSerializableExtra("coin");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String crypto = prefs.getString("cryptoList", null);
//
////        convert crypto to a list
//        String[] cryptoList = crypto.split(",");
//
////        convert string array to list
//
//        List<CryptoModel> stringList = new ArrayList<CryptoModel>((Collection<? extends CryptoModel>) Arrays.asList(cryptoList));
//
//        for (int i = 0; i < stringList.size(); i++) {
//            Log.d("crypto", stringList.get(i).getName());
//        }

        my_letter = (CircularTextView) findViewById(R.id.circularTextView);
        coinName = (TextView) findViewById(R.id.coinName);
        coinCount = (TextView) findViewById(R.id.coinCount);
        amount = (TextView) findViewById(R.id.amount);
        time = (TextView) findViewById(R.id.time);





        my_letter.setText(coins.getName().substring(0, 1));
        my_letter.setStrokeWidth(1);
        my_letter.setStrokeColor("#ffffff");
        my_letter.setSolidColor("#f5d488");
        my_letter.setTextColor(Color.parseColor("#ffffff"));
        my_letter.setSolidColor("#808080");

        coinName.setText(coins.getName());
        coinCount.setText(coins.getCount());
        amount.setText(coins.getTotal());
        time.setText(coins.getTime());

        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        userBalance = task.getResult().getString("Amount");

                    }
                });


        sta.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSlideComplete(SlideToActView slideToActView) {
                addToDatabase(coins.getName(), coins.getPrice(), coins.getSymbol(), coins.getTotal(), coins.getId());
            }
        });
    }

    String uniqueID = UUID.randomUUID().toString();

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addToDatabase(String coinName, String sellPrice, String coinSymbol, String total, String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        float amt = Float.parseFloat(userBalance) + Float.parseFloat(total);

        DocumentReference docRef = db.collection("users").document(user.getUid()).collection("transactions").document(uniqueID);
        DocumentReference transRef = db.collection("users").document(user.getUid()).collection("coins").document(id);

        Map<String, Object> use = new HashMap<>();
        use.put("name", coinName);
        use.put("price", sellPrice);
        use.put("id", uniqueID);
        use.put("symbol", coinSymbol);
        use.put("type", "sell");
        use.put("count", coinCount.getText().toString());
        use.put("total", total);
        use.put("time", String.valueOf(dtf.format(now)));
        docRef.set(use);
        transRef.update("type", "sell");

        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .update("Amount", String.valueOf(amt));
    }
}


