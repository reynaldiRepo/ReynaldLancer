package com.reynaldlancer.reynaldlancer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class RvHistoryAdapter extends RecyclerView.Adapter<RvHistoryAdapter.ViewHolder> {
    ArrayList<HistoryModel> historyModels;
    Context context;

    public RvHistoryAdapter(ArrayList<HistoryModel> historyModels, Context context) {
        this.historyModels = historyModels;
        this.context = context;
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

        HistoryModel history = historyModels.get(position);
        holder.amount.setText(history.amount);
        holder.date.setText(history.date);
        holder.stautus.setText(history.status);

        if (history.status == "Proccess"){
            holder.detail.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return historyModels.size();
    }
}