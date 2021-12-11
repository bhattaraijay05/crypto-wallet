package com.example.cryptowallet.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cryptowallet.R;
import com.example.cryptowallet.databinding.FragmentHomeBinding;
import com.example.cryptowallet.model.CryptoData;
import com.example.cryptowallet.model.PlaceHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//API Key: BE8FD9B2-EA0A-40D4-AA71-BDA225A11989

public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;

    private String ACCESS_TOKEN = "b3ae9792-4fcc-4a8f-9376-42ad354f9bd0";
    ArrayList<String> coinList = new ArrayList<String>();

    private static boolean runOnce = false;


    private String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?limit=100";
    private String jsonURL = "https://jsonplaceholder.typicode.com/todos";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (runOnce) {
            return;
        } else {
//            sendAndRequestResponse();
//            loadCoinList();
            loadJsonList();
            runOnce = true;
        }
        binding = null;
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final ListView listview = binding.mobileList;
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.activity_listview, coinList);
        listview.setAdapter(adapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



//    private void loadHeroList() {
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject obj = new JSONObject(response);
//                            JSONArray heroArray = obj.getJSONArray("superheros");
//                            for (int i = 0; i < heroArray.length(); i++) {
//                                JSONObject heroObject = heroArray.getJSONObject(i);
//                                SuperHeroes superHero = new SuperHeroes(heroObject.getString("name"), heroObject.getString("power"));
//
//                                superHeroList.add(superHero.getName());
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
//        requestQueue.add(stringRequest);
//    }
//

//
    private void loadJsonList() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, jsonURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray obj = new JSONArray(response);
                            for (int i = 0; i < obj.length(); i++) {
                                JSONObject heroObject = obj.getJSONObject(i);
                                PlaceHolder ph = new PlaceHolder(heroObject.getString("title"));
                                coinList.add(ph.getTitle());
                            }
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
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }


    private void loadCoinList() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray cryptoArray = obj.getJSONArray("data");
                            for (int i = 0; i < cryptoArray.length(); i++) {
                                JSONObject cryptoObject = cryptoArray.getJSONObject(i);
                                CryptoData coin = new CryptoData(cryptoObject.getString("name"));
                                coinList.add(coin.getName());
                            }
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
        requestQueue.add(stringRequest);
    }


}