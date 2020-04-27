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

public class TugasRvApapter extends RecyclerView.Adapter<TugasRvApapter.ViewHolder> {
    ArrayList<TugasModel> tugasModels;
    Context context;

    public TugasRvApapter(ArrayList<TugasModel> tugasModels, Context context) {
        this.tugasModels = tugasModels;
        this.context = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView foto;
        TextView nama;
        TextView judul;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foto = itemView.findViewById(R.id.user_foto_tugas);
            nama = itemView.findViewById(R.id.nama_tugas);
            judul = itemView.findViewById(R.id.judul_tugas);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.tugas_rv, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TugasModel tugas = tugasModels.get(position);
        holder.foto.setImageResource(tugas.getFoto());
        holder.judul.setText(tugas.getJudul());
        holder.nama.setText(tugas.getNama());

    }

    @Override
    public int getItemCount() {
        return tugasModels.size();
    }


}
