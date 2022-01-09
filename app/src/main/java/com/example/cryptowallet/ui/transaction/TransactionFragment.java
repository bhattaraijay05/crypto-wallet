package com.example.cryptowallet.ui.transaction;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cryptowallet.CoinAdapter;
import com.example.cryptowallet.TransactionAdapter;
import com.example.cryptowallet.databinding.FragmentTransactionBinding;
import com.example.cryptowallet.model.CryptoModel;
import com.example.cryptowallet.model.TransactionModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class TransactionFragment extends Fragment {
    ListView listView;
    List<TransactionModel> currencyList;
    private FragmentTransactionBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentTransactionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listView = binding.transactionList;
        loadTransactions();


        return root;
    }

    private void loadTransactions() {
        getListItems();
    }

    private void getListItems() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("users").document(user.getUid()).collection("coins").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<TransactionModel> transactions = new ArrayList<>();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            TransactionModel p = document.toObject(TransactionModel.class);
                            transactions.add(p);
                        }
                        TransactionAdapter adapter = new TransactionAdapter(getContext(), transactions);
                        listView.setAdapter(adapter);
//                        Log.d("TAG", "onSuccess: " + transactions);

                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}