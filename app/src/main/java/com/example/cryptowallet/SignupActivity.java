package com.example.cryptowallet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextFullName;
    private Button buttonSignup;
    private TextView textViewSignin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    private FirebaseFirestore firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.frag1Color));


        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), BottomTab.class));
        }


        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextFullName = (EditText) findViewById(R.id.editTextFullName);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        progressDialog = new ProgressDialog(this);
        buttonSignup.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(fullName)) {
            Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser userr = FirebaseAuth.getInstance().getCurrentUser();
                             if (userr != null) {
                                 String uid = userr.getUid();
                                 firebaseDatabase = FirebaseFirestore.getInstance();
                                 DocumentReference users = firebaseDatabase.collection("users").document(uid);
                                 Map<String, Object> use = new HashMap<>();
                                 use.put("Name", fullName);
                                 use.put("Email", email);
                                 use.put("uid", uid);
                                 users.set(use);
                             }
                            startActivity(new Intent(getApplicationContext(), BottomTab.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Registration Error", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view == buttonSignup) {
            registerUser();
        }
        if (view == textViewSignin) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}