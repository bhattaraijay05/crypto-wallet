package com.example.cryptowallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptowallet.model.CryptoModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

// on below line we are creating our adapter class
// in this class we are passing our array list
// and our View Holder class which we have created.
public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyViewholder> {
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private ArrayList<CryptoModel> currencyModals;
    private Context context;

    public CurrencyAdapter(ArrayList<CryptoModel> currencyModals, Context context) {
        this.currencyModals = currencyModals;
        this.context = context;
    }


    @NonNull
    @Override
    public CurrencyAdapter.CurrencyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // this method is use to inflate the layout file
        // which we have created for our recycler view.
        // on below line we are inflating our layout file.
        View view = LayoutInflater.from(context).inflate(R.layout.activity_listview, parent, false);
        return new CurrencyAdapter.CurrencyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyAdapter.CurrencyViewholder holder, int position) {
        // on below line we are setting data to our item of
        // recycler view and all its views.
        CryptoModel modal = currencyModals.get(position);
        holder.nameTV.setText(modal.getName());
        holder.rateTV.setText("$ " + df2.format(modal.getPrice()));
        holder.symbolTV.setText(modal.getSymbol());
    }

    @Override
    public int getItemCount() {
        // on below line we are returning
        // the size of our array list.
        return currencyModals.size();
    }

    // on below line we are creating our view holder class
    // which will be used to initialize each view of our layout file.
    public class CurrencyViewholder extends RecyclerView.ViewHolder {
        private TextView symbolTV, rateTV, nameTV;

        public CurrencyViewholder(@NonNull View itemView) {
            super(itemView);
            // on below line we are initializing all
            // our text views along with  its ids.
            symbolTV = itemView.findViewById(R.id.symbol);
            rateTV = itemView.findViewById(R.id.price);
            nameTV = itemView.findViewById(R.id.name);
        }
    }
}