package com.reynaldlancer.reynaldlancer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RvSkillUserAdapter extends RecyclerView.Adapter<RvSkillUserAdapter.ViewHolder>  {
    public ArrayList<ModelUserSkill> getSkillModels() {
        return SkillModels;
    }

    public void setSkillModels(ArrayList<ModelUserSkill> skillModels) {
        SkillModels = skillModels;
    }

    ArrayList<ModelUserSkill> SkillModels;
    Context context;
    onDeleteUserSkill onDeleteUserSkill;
    public RvSkillUserAdapter(ArrayList<ModelUserSkill> SkillModels, Context context, onDeleteUserSkill onDeleteUserSkill) {
        this.SkillModels = SkillModels;
        this.context = context;
        this.onDeleteUserSkill = onDeleteUserSkill;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, id;
        Button del_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_skill);
            id = itemView.findViewById(R.id.skill_id);
            del_btn = itemView.findViewById(R.id.del_skill_btn);
        }
    }

    @NonNull
    @Override
    public RvSkillUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.skill_rv, parent, false);
        return new RvSkillUserAdapter.ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull RvSkillUserAdapter.ViewHolder holder, int position) {
        ModelUserSkill skill = SkillModels.get(position);
        holder.id.setText(skill.get_id());
        holder.name.setText(skill.getSkill());
        holder.del_btn.setOnClickListener(v -> {onDeleteUserSkill.onDeleteBtnClick(holder.id.getText().toString(), position);});
    }

    @Override
    public int getItemCount() {
        return SkillModels.size();
    }
}
