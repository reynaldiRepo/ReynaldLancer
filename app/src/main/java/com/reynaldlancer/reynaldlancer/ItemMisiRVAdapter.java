package com.reynaldlancer.reynaldlancer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemMisiRVAdapter  extends RecyclerView.Adapter<com.reynaldlancer.reynaldlancer.ItemMisiRVAdapter.ViewHolder> {
    ArrayList<ItemMisiModel> itemMisiModel;
    Context context;

    public ItemMisiRVAdapter(ArrayList<ItemMisiModel> model, Context context) {
        this.itemMisiModel = model;
        this.context = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_item, cancel_btn;
        EditText harga, komisi, target;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_item = itemView.findViewById(R.id.img_item_misi);
            harga = itemView.findViewById(R.id.harga_item_misi);
            komisi = itemView.findViewById(R.id.komisi_item_misi);
            target = itemView.findViewById(R.id.target_jual_misi);
            cancel_btn = itemView.findViewById(R.id.delete_item_misi);
        }
    }

    @NonNull
    @Override
    public com.reynaldlancer.reynaldlancer.ItemMisiRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_misi_rv, parent, false);
        return new com.reynaldlancer.reynaldlancer.ItemMisiRVAdapter.ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final com.reynaldlancer.reynaldlancer.ItemMisiRVAdapter.ViewHolder holder, final int position) {

        final ItemMisiModel model = itemMisiModel.get(position);
        holder.cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                itemMisiModel.remove(pos);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(pos, itemMisiModel.size());
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemMisiModel.size();
    }
}
