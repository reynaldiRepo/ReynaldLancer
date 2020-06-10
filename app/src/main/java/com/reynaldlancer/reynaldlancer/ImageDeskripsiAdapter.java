package com.reynaldlancer.reynaldlancer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ImageDeskripsiAdapter extends RecyclerView.Adapter<com.reynaldlancer.reynaldlancer.ImageDeskripsiAdapter.ViewHolder> {
    ArrayList<ImageDeskripsiModel> imageDeskripsiModel;
    Context context;

    public ImageDeskripsiAdapter(ArrayList<ImageDeskripsiModel> imageDeskripsiModel, Context context) {
        this.imageDeskripsiModel = imageDeskripsiModel;
        this.context = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout foto;
        ImageView cancel_logo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foto = itemView.findViewById(R.id.image_deskripsi_prev);
            cancel_logo = itemView.findViewById(R.id.cancel_upload);
        }
    }

    @NonNull
    @Override
    public com.reynaldlancer.reynaldlancer.ImageDeskripsiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.image_desc_rv, parent, false);
        return new com.reynaldlancer.reynaldlancer.ImageDeskripsiAdapter.ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull com.reynaldlancer.reynaldlancer.ImageDeskripsiAdapter.ViewHolder holder, int position) {

        ImageDeskripsiModel model = imageDeskripsiModel.get(position);
        if (model.image == R.drawable.upload_img){
            holder.cancel_logo.setVisibility(View.GONE);
        }
        holder.foto.setBackgroundResource(model.image);

    }

    @Override
    public int getItemCount() {
        return imageDeskripsiModel.size();
    }
}
