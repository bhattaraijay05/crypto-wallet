package com.example.cryptowallet;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cryptowallet.custom.CircularTextView;
import com.example.cryptowallet.model.CryptoModel;

import java.util.ArrayList;
import java.util.Random;

public class CoinAdapter extends BaseAdapter {

    Context context;
    ArrayList<CryptoModel> arrayList;
    public CoinAdapter(Context context, ArrayList<CryptoModel> arrayList) {
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
    public  View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView ==  null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_listview, parent, false);
        }
        TextView name, symbol, price;
        CircularTextView my_letter;
        name = (TextView) convertView.findViewById(R.id.name);
        symbol = (TextView) convertView.findViewById(R.id.symbol);
        price = (TextView) convertView.findViewById(R.id.price);
        my_letter = (CircularTextView) convertView.findViewById(R.id.circularTextView);

        my_letter.setStrokeWidth(1);
        my_letter.setStrokeColor("#ffffff");
        my_letter.setSolidColor("#aab6fb");
        my_letter.setTextColor(Color.parseColor("#ffffff"));

        symbol.setText(arrayList.get(position).getName());
        name.setText(arrayList.get(position).getSymbol());
        price.setText("$" + String.format("%.05f", arrayList.get(position).getPrice()));
        my_letter.setText(String.valueOf(name.getText().toString().charAt(0)));

        return convertView;
    }
}