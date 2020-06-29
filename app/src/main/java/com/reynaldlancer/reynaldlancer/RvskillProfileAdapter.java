package com.reynaldlancer.reynaldlancer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RvskillProfileAdapter extends RecyclerView.Adapter<RvskillProfileAdapter.ViewHolder> {

    Context contex;
    ArrayList<ModelUserSkill> modelUserSkills;

    public RvskillProfileAdapter(Context contex, ArrayList<ModelUserSkill> modelUserSkills) {
        this.contex = contex;
        this.modelUserSkills = modelUserSkills;
    }

    public void setModelUserSkills(ArrayList<ModelUserSkill> modelUserSkills) {
        this.modelUserSkills = modelUserSkills;
    }

    @NonNull
    @Override
    public RvskillProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(contex).inflate(R.layout.skill_rv_profile, parent, false);
        return new RvskillProfileAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RvskillProfileAdapter.ViewHolder holder, int position) {
        ModelUserSkill modelUserSkill = modelUserSkills.get(position);
        holder.skillName.setText(modelUserSkill.getSkill());
    }

    @Override
    public int getItemCount() {
        return modelUserSkills.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView skillName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            skillName = itemView.findViewById(R.id.skill_name_on_profile);
        }
    }
}
