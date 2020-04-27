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

public class HeroAcademyRvAdapter  extends RecyclerView.Adapter<HeroAcademyRvAdapter.ViewHolder>  {
    ArrayList<HeroAcademyModel> academyModels;
    Context context;

    public HeroAcademyRvAdapter(ArrayList<HeroAcademyModel> misiModels, Context context) {
        this.academyModels = misiModels;
        this.context = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView foto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foto = itemView.findViewById(R.id.foto_academy);
        }
    }

    @NonNull
    @Override
    public HeroAcademyRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.academy_rv, parent, false);
        return new HeroAcademyRvAdapter.ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull HeroAcademyRvAdapter.ViewHolder holder, int position) {

        HeroAcademyModel tugas = academyModels.get(position);
        holder.foto.setImageResource(tugas.getImage());

    }

    @Override
    public int getItemCount() {
        return academyModels.size();
    }
}