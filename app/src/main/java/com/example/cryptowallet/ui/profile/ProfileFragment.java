package com.example.cryptowallet.ui.profile;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.cryptowallet.MainActivity;
import com.example.cryptowallet.MyCoinAdapter;
import com.example.cryptowallet.TransactionAdapter;
import com.example.cryptowallet.custom.CircularTextView;
import com.example.cryptowallet.databinding.FragmentProfileBinding;
import com.example.cryptowallet.model.TransactionModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    ListView listView;
    private FragmentProfileBinding binding;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final CircularTextView userName = binding.textNotifications;
        listView = binding.myCoins;
        final Button logoutButton = binding.logoutButton;
        userName.setText(String.valueOf(user.getDisplayName().toString().charAt(0)));
        userName.setStrokeWidth(1);
        userName.setStrokeColor("#ffffff");
        userName.setSolidColor("#f5d488");
        userName.setTextColor(Color.parseColor("#ffffff"));

        getListItems();
        logoutButton.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
        });
        return root;
    }

    private void getListItems() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        db.collection("users").document(user.getUid()).collection("coins").orderBy("time", Query.Direction.DESCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<TransactionModel> transactions = new ArrayList<>();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            document.toObject(TransactionModel.class);
                            TransactionModel p = document.toObject(TransactionModel.class);
                            transactions.add(p);
                        }
                        if (transactions.size() > 0) {
                            MyCoinAdapter adapter = new MyCoinAdapter(getContext(), transactions);
                            listView.setAdapter(adapter);
                        } else {
                            Toast.makeText(getContext(), "No transactions found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}