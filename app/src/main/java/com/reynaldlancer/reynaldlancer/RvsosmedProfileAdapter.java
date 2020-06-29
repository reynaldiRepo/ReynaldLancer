package com.reynaldlancer.reynaldlancer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.ArrayList;
import java.util.HashMap;

public class RvsosmedProfileAdapter extends RecyclerView.Adapter<RvsosmedProfileAdapter.ViewHolder> {
    Context ctx;
    ArrayList<ModelSosmed> modelSosmeds;
    HashMap<String, MaterialDrawableBuilder.IconValue> iconMapping;


    public RvsosmedProfileAdapter(Context ctx, ArrayList<ModelSosmed> modelSosmeds, HashMap<String, MaterialDrawableBuilder.IconValue> iconMapping) {
        this.ctx = ctx;
        this.modelSosmeds = modelSosmeds;
        this.iconMapping = iconMapping;
    }

    @NonNull
    @Override
    public RvsosmedProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.sosmed_profile_rv, parent, false);
        return new RvsosmedProfileAdapter.ViewHolder(v);
    }

    public void setModelSosmeds(ArrayList<ModelSosmed> modelSosmeds) {
        this.modelSosmeds = modelSosmeds;
    }

    @Override
    public void onBindViewHolder(@NonNull RvsosmedProfileAdapter.ViewHolder holder, int position) {
        ModelSosmed modelSosmed = modelSosmeds.get(position);
        holder.iconSosmed.setIcon(iconMapping.get(modelSosmed.getSosmed_type()));
        holder.iconSosmed.setOnClickListener(v -> {
            String url = modelSosmed.getLink_sosmed();
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            ctx.startActivity(browserIntent);
        });
    }

    @Override
    public int getItemCount() {
        return modelSosmeds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MaterialIconView iconSosmed;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconSosmed = itemView.findViewById(R.id.icon_sosmed_on_profile);
        }
    }
}
