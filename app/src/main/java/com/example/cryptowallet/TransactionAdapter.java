package com.example.cryptowallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cryptowallet.model.CryptoModel;
import com.example.cryptowallet.model.TransactionModel;

import java.util.ArrayList;

public class TransactionAdapter extends BaseAdapter {

    Context context;
    ArrayList<TransactionModel> arrayList;

    public TransactionAdapter(Context context, ArrayList<TransactionModel> arrayList) {
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
        if (convertView ==  null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_listview, parent, false);
        }
        TextView name, symbol, price,my_letter;
        name = (TextView) convertView.findViewById(R.id.name);
        symbol = (TextView) convertView.findViewById(R.id.symbol);
        price = (TextView) convertView.findViewById(R.id.price);
        my_letter = (TextView) convertView.findViewById(R.id.my_letter);


        symbol.setText(arrayList.get(position).getName());
        name.setText(arrayList.get(position).getSymbol());
        price.setText(arrayList.get(position).getPrice());
        my_letter.setText(String.valueOf(name.getText().toString().charAt(0)));

        return convertView;
    }
}