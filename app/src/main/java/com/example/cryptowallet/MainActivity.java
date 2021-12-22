package com.example.cryptowallet;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cuberto.liquid_swipe.LiquidPager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    LiquidPager pager;
    com.example.cryptowallet.viewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), BottomTab.class));
        } else {
            pager = findViewById(R.id.pager);
            Objects.requireNonNull(getSupportActionBar()).hide();
            viewPager = new viewPager(getSupportFragmentManager(), 1);
            pager.setAdapter(viewPager);
        }
    }
}