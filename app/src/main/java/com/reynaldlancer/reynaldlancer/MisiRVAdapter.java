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

public class MisiRVAdapter extends RecyclerView.Adapter<MisiRVAdapter.ViewHolder> {

    ArrayList<TugasModel> misiModels;
    Context context;

    public MisiRVAdapter(ArrayList<TugasModel> misiModels, Context context) {
        this.misiModels = misiModels;
        this.context = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView foto;
        TextView nama;
        TextView judul;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foto = itemView.findViewById(R.id.user_foto_misi);
            nama = itemView.findViewById(R.id.nama_misi);
            judul = itemView.findViewById(R.id.judul_misi);

        }
    }

    @NonNull
    @Override
    public MisiRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.misi_rv, parent, false);
        return new MisiRVAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MisiRVAdapter.ViewHolder holder, int position) {

        TugasModel tugas = misiModels.get(position);
        holder.foto.setImageResource(tugas.getFoto());
        holder.judul.setText(tugas.getJudul());
        holder.nama.setText(tugas.getNama());

    }

    @Override
    public int getItemCount() {
        return misiModels.size();
    }

}
