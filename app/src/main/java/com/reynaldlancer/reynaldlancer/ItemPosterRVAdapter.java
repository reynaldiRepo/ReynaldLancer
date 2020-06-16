package com.reynaldlancer.reynaldlancer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class ItemPosterRVAdapter extends RecyclerView.Adapter<ItemPosterRVAdapter.ViewHolder> {

    ArrayList<ItemposterModel> posterModel;
    Context context;

    public ItemPosterRVAdapter(ArrayList<ItemposterModel> posterModel, Context context) {
        this.posterModel = posterModel;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ImageItemPoster, cancel_btn;
        EditText NamaItemPoster, HargaItemPoster;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ImageItemPoster = itemView.findViewById(R.id.img_item_poster);
            cancel_btn = itemView.findViewById(R.id.delete_item_poster);
            NamaItemPoster = itemView.findViewById(R.id.nama_item_poster);
            HargaItemPoster = itemView.findViewById(R.id.harga_item_poster);
        }
    }

    @NonNull
    @Override
    public ItemPosterRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_poster_rv, parent, false);
        return new ItemPosterRVAdapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final ItemPosterRVAdapter.ViewHolder holder, int position) {

        ItemposterModel model = posterModel.get(position);
        holder.cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                posterModel.remove(pos);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(pos, posterModel.size());
            }
        });

    }

    @Override
    public int getItemCount() {
        return posterModel.size();
    }

}
