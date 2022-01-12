package com.example.cryptowallet;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cryptowallet.custom.CircularTextView;
import com.example.cryptowallet.model.CryptoModel;
import com.example.cryptowallet.model.TransactionModel;

import java.util.ArrayList;
import java.util.Objects;

public class MyCoinAdapter extends BaseAdapter {

    Context context;
    ArrayList<TransactionModel> arrayList;

    public MyCoinAdapter(Context context, ArrayList<TransactionModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.my_coins, parent, false);
        }
        TextView coinName, count, returns, currentPrice, investedPrice;
        CircularTextView my_letter;
        coinName = convertView.findViewById(R.id.coinName);
        count = convertView.findViewById(R.id.count);
        returns = convertView.findViewById(R.id.returns);
        currentPrice = convertView.findViewById(R.id.currentPrice);
        investedPrice = convertView.findViewById(R.id.investedPrice);


        my_letter = (CircularTextView) convertView.findViewById(R.id.circularTextView);

        my_letter.setStrokeWidth(1);
        my_letter.setStrokeColor("#ffffff");
        my_letter.setSolidColor("#f5d488");
        my_letter.setTextColor(Color.parseColor("#ffffff"));
        my_letter.setSolidColor("#5097a4");

        coinName.setText(arrayList.get(position).getName());
        count.setText(arrayList.get(position).getCount() + "");
//        returns.setText(arrayList.get(position).getReturns());
//        currentPrice.setText(arrayList.get(position).getCurrentPrice());
        investedPrice.setText("Invested\n $"+arrayList.get(position).getTotal());


        my_letter.setText(String.valueOf(coinName.getText().toString().charAt(0)));

        return convertView;
    }
}