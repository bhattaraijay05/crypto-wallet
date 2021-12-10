package com.example.cryptowallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cryptowallet.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Button btn = (Button) findViewById(R.id.button);
//        Button logbut = (Button) findViewById(R.id.login);
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), BottomTab.class);
//                startActivity(intent);
//            }
//        });
//
//
//        logbut.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(intent);
//            }
//        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            // Start home activity
            startActivity(new Intent(MainActivity.this, BottomTab.class));
        } else {
            // No user is signed in
            // start login activity
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        // close splash activity
        finish();
    }
}