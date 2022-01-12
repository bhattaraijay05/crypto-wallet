package com.example.cryptowallet.ui.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cryptowallet.MainActivity;
import com.example.cryptowallet.MyCoinAdapter;
import com.example.cryptowallet.R;
import com.example.cryptowallet.SellFragment;
import com.example.cryptowallet.custom.CircularTextView;
import com.example.cryptowallet.databinding.FragmentProfileBinding;
import com.example.cryptowallet.model.CryptoModel;
import com.example.cryptowallet.model.TransactionModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class ProfileFragment extends Fragment {
    ListView listView;
    private FragmentProfileBinding binding;
    TextView userBalance;
    ArrayList<TransactionModel> transactions;

    List<CryptoModel> cryptoList;
    private String ACCESS_TOKEN = "b3ae9792-4fcc-4a8f-9376-42ad354f9bd0";
    private String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?limit=100";

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final CircularTextView userName = binding.textNotifications;
        listView = binding.myCoins;
        userBalance = binding.balance;
        final Button logoutButton = binding.logoutButton;

        transactions = new ArrayList<TransactionModel>();
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


        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        String balance = task.getResult().getString("Amount");
                        userBalance.setText("Balance   $" + balance);
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity().getApplicationContext(), SellFragment.class);
                intent.putExtra("coin", transactions.get(position));
                startActivity(intent);
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            int mLastFirstVisibleItem = 0;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (view.getId() == listView.getId()) {
                    final int currentFirstVisibleItem = listView.getFirstVisiblePosition();
                    if (currentFirstVisibleItem > mLastFirstVisibleItem) {
                        BottomNavigationView navigation = getActivity().findViewById(R.id.nav_view);
                        navigation.animate().translationY(navigation.getHeight()).setInterpolator(new AccelerateInterpolator(2));
                    } else if (currentFirstVisibleItem < mLastFirstVisibleItem) {
                        BottomNavigationView navigation = getActivity().findViewById(R.id.nav_view);
                        navigation.animate().translationY(0).setInterpolator(new AccelerateInterpolator(2));
                    }
                    mLastFirstVisibleItem = currentFirstVisibleItem;
                }
            }
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
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            if (Objects.equals(document.getString("type"), "buy")) {
                                document.toObject(TransactionModel.class);
                                TransactionModel p = document.toObject(TransactionModel.class);
                                transactions.add(p);
                            }
                        }
                        if (transactions.size() > 0) {
                            MyCoinAdapter adapter = new MyCoinAdapter(getContext(), transactions);
                            listView.setAdapter(adapter);
                        } else {
                            Toast.makeText(getContext(), "No coins found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}