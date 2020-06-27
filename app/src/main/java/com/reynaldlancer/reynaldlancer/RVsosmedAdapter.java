package com.reynaldlancer.reynaldlancer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.ArrayList;
import java.util.HashMap;

public class RVsosmedAdapter extends RecyclerView.Adapter<RVsosmedAdapter.ViewHolder> {

    public void setSosmedArrayList(ArrayList<ModelSosmed> sosmedArrayList) {
        this.sosmedArrayList = sosmedArrayList;
    }

    public RVsosmedAdapter(ArrayList<ModelSosmed> sosmedArrayList, HashMap<String, MaterialDrawableBuilder.IconValue> iconMapping, Context context, onItemSosmedClick interfaceSosmedClick) {
        this.sosmedArrayList = sosmedArrayList;
        this.iconMapping = iconMapping;
        this.context = context;
        this.interfaceSosmedClick = interfaceSosmedClick;
    }

    ArrayList<ModelSosmed> sosmedArrayList;
    HashMap<String, MaterialDrawableBuilder.IconValue> iconMapping;
    Context context;
    onItemSosmedClick interfaceSosmedClick;

    @NonNull
    @Override
    public RVsosmedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.sosmed_rv, parent, false);
        return new RVsosmedAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RVsosmedAdapter.ViewHolder holder, int position) {
        ModelSosmed modelSosmed = sosmedArrayList.get(position);
        Log.d("ICON SOSMED", String.valueOf(iconMapping.get(modelSosmed.getSosmed_type())));
        holder.icon_sosmed.setIcon(iconMapping.get(modelSosmed.getSosmed_type()));
        holder.link_sosmed_user.setText(modelSosmed.getLink_sosmed());
        holder.delete_btn.setOnClickListener(v -> {interfaceSosmedClick.onDeleteIconClick(modelSosmed.get_id());});
        holder.update_btn.setOnClickListener(v -> interfaceSosmedClick.onUpdateIconClick(modelSosmed.get_id(),
                holder.link_sosmed_user.getText().toString()));
    }

    @Override
    public int getItemCount() {
        return sosmedArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialIconView icon_sosmed;
        EditText link_sosmed_user;
        Button update_btn, delete_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon_sosmed = itemView.findViewById(R.id.icon_sosmed);
            link_sosmed_user = itemView.findViewById(R.id.link_sosmed_user);
            update_btn = itemView.findViewById(R.id.update_sosmed);
            delete_btn = itemView.findViewById(R.id.delete_sosmed);
        }
    }
}
