package com.example.cryptowallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cryptowallet.model.CryptoModel;

import java.util.ArrayList;

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
        TextView name, email, price;
        name = (TextView) convertView.findViewById(R.id.name);
        email = (TextView) convertView.findViewById(R.id.symbol);
        price = (TextView) convertView.findViewById(R.id.price);

        name.setText(arrayList.get(position).getName());
        email.setText(arrayList.get(position).getSymbol());
        price.setText(String.format("%.02f", arrayList.get(position).getPrice()));

        return convertView;
    }
}