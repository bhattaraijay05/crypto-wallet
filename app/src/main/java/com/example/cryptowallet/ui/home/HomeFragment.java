package com.example.cryptowallet.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cryptowallet.CoinAdapter;
import com.example.cryptowallet.CoinData;
import com.example.cryptowallet.databinding.FragmentHomeBinding;
import com.example.cryptowallet.model.CryptoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//API Key: BE8FD9B2-EA0A-40D4-AA71-BDA225A11989

public class HomeFragment extends Fragment {
    ListView listView;
    List<CryptoModel> cryptoList;
    private FragmentHomeBinding binding;
    private String ACCESS_TOKEN = "b3ae9792-4fcc-4a8f-9376-42ad354f9bd0";
    private String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?limit=100";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listView = binding.currencyList;
        cryptoList = new ArrayList<CryptoModel>();
        loadCoinList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CryptoModel item = cryptoList.get(position);
                Intent intent = new Intent(getActivity().getApplicationContext(), CoinData.class);
                intent.putExtra("coinData", item);
                startActivity(intent);

            }
        });

        return root;
    }


    private void loadCoinList() {
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray cryptoArray = response.getJSONArray("data");
                            for (int i = 0; i < cryptoArray.length(); i++) {
                                JSONObject cryptoObject = cryptoArray.getJSONObject(i);
                                String symbol = cryptoObject.getString("symbol");
                                String name = cryptoObject.getString("name");
                                JSONObject quote = cryptoObject.getJSONObject("quote");
                                JSONObject USD = quote.getJSONObject("USD");
                                double price = USD.getDouble("price");
                                CryptoModel coin = new CryptoModel(name, symbol, price);
                                cryptoList.add(coin);
//                                cryptoList.add(coin.getSymbol());
//                                currencyModalArrayList.add(new CryptoData(name, symbol));
                            }
                            CoinAdapter adapter = new CoinAdapter(getActivity(), (ArrayList<CryptoModel>) cryptoList);
                            listView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("X-CMC_PRO_API_KEY", ACCESS_TOKEN);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(objectRequest);
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
}