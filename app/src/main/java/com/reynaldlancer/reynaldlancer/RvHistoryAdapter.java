package com.reynaldlancer.reynaldlancer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Callback;

class RvHistoryAdapter extends RecyclerView.Adapter<RvHistoryAdapter.ViewHolder> {
    ArrayList<ModelTransaksiSaldo> historyModels;
    Context context;

    public void setHistoryModels(ArrayList<ModelTransaksiSaldo> historyModels) {
        this.historyModels = historyModels;
    }

    onDetaliTransaksiClick onDetaliTransaksiClick;


    public RvHistoryAdapter(ArrayList<ModelTransaksiSaldo> historyModels, Context context, onDetaliTransaksiClick onDetaliTransaksiClick) {
        this.historyModels = historyModels;
        this.context = context;
        this.onDetaliTransaksiClick = onDetaliTransaksiClick;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView amount;
        TextView date;
        TextView stautus;
        TextView detail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.history_ammount);
            date = itemView.findViewById(R.id.history_date);
            stautus = itemView.findViewById(R.id.history_status);
            detail = itemView.findViewById(R.id.topup_detail);

        }
    }

    @NonNull
    @Override
    public RvHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.history_rv, parent, false);
        return new RvHistoryAdapter.ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull RvHistoryAdapter.ViewHolder holder, int position) {

        ModelTransaksiSaldo history = historyModels.get(position);
        holder.stautus.setText(history.getStatus());
        holder.amount.setText(new StringFormater().toCurrency(history.getJumlah().toString()));
        holder.date.setText(history.getTanggal());
//        Toast.makeText(context, history.getStatus(), Toast.LENGTH_SHORT).show();
        if (history.getStatus().equals("PROSES")){
            holder.detail.setVisibility(View.VISIBLE);
        }else{
            holder.detail.setVisibility(View.GONE);
        }
        holder.detail.setOnClickListener(v -> {onDetaliTransaksiClick.onItemTransaksiClick(history.get_id());});
    }

    @Override
    public int getItemCount() {
        return historyModels.size();
    }
}