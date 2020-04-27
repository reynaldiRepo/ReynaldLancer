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

public class PosterRvAdapter extends RecyclerView.Adapter<PosterRvAdapter.ViewHolder> {
    ArrayList<TugasModel> posterModels;
    Context context;

    public PosterRvAdapter(ArrayList<TugasModel> misiModels, Context context) {
        this.posterModels = misiModels;
        this.context = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView foto;
        TextView nama;
        TextView judul;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foto = itemView.findViewById(R.id.user_foto_poster);
            nama = itemView.findViewById(R.id.nama_poster);
            judul = itemView.findViewById(R.id.judul_poster);

        }
    }

    @NonNull
    @Override
    public PosterRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.poster_rv, parent, false);
        return new PosterRvAdapter.ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull PosterRvAdapter.ViewHolder holder, int position) {

        TugasModel tugas = posterModels.get(position);
        holder.foto.setImageResource(tugas.getFoto());
        holder.judul.setText(tugas.getJudul());
        holder.nama.setText(tugas.getNama());

    }

    @Override
    public int getItemCount() {
        return posterModels.size();
    }
}
