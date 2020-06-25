package com.reynaldlancer.reynaldlancer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RvSkillAdapter extends RecyclerView.Adapter<RvSkillAdapter.ViewHolder> {

    Context context;
    ArrayList<ModelSkill> modelUserSkills;
    ArrayList<String> modelUserSkillsFromUser;
    onAddSkill onAddSkillInterface;

    public RvSkillAdapter(Context context, ArrayList<ModelSkill> modelUserSkills, ArrayList<String> modelUserSkillsFromUser, onAddSkill onAddSkillInterface) {
        this.context = context;
        this.modelUserSkills = modelUserSkills;
        this.modelUserSkillsFromUser = modelUserSkillsFromUser;
        this.onAddSkillInterface = onAddSkillInterface;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView skill_name;
        CheckBox checkBox_skill;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            skill_name = itemView.findViewById(R.id.skill_choices);
            checkBox_skill = itemView.findViewById(R.id.skill_checkbox);
        }
    }

    @NonNull
    @Override
    public RvSkillAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.rv_skill, parent, false);
        return new RvSkillAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RvSkillAdapter.ViewHolder holder, int position) {
        ModelSkill modelUserSkill = modelUserSkills.get(position);
        holder.skill_name.setText(modelUserSkill.getSkill());
        if (modelUserSkillsFromUser.contains(modelUserSkill.getSkill())) {
            holder.checkBox_skill.setChecked(true);
        } else {
            holder.checkBox_skill.setChecked(false);
        }
        holder.checkBox_skill.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && modelUserSkillsFromUser.contains(holder.skill_name.getText().toString())) {
                    onAddSkillInterface.DataFromRVToDialog(modelUserSkillsFromUser);
                } else if (isChecked && !modelUserSkillsFromUser.contains(holder.skill_name.getText().toString())) {
                    modelUserSkillsFromUser.add(holder.skill_name.getText().toString());
                    onAddSkillInterface.DataFromRVToDialog(modelUserSkillsFromUser);
                } else if (!isChecked) {
                    modelUserSkillsFromUser.remove(holder.skill_name.getText().toString());
                    onAddSkillInterface.DataFromRVToDialog(modelUserSkillsFromUser);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return modelUserSkills.size();
    }

}
