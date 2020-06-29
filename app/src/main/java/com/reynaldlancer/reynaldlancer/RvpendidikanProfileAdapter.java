package com.reynaldlancer.reynaldlancer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.ArrayList;

public class RvpendidikanProfileAdapter extends RecyclerView.Adapter<RvpendidikanProfileAdapter.ViewHolder> {

    Context ctx;
    ArrayList<ModelPendidikanUser> modelPendidikanUsers;

    public RvpendidikanProfileAdapter(Context ctx, ArrayList<ModelPendidikanUser> modelPendidikanUsers) {
        this.ctx = ctx;
        this.modelPendidikanUsers = modelPendidikanUsers;
    }

    @NonNull
    @Override
    public RvpendidikanProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.sosmed_pendidikan_rv, parent, false);
        return new RvpendidikanProfileAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RvpendidikanProfileAdapter.ViewHolder holder, int position) {
        ModelPendidikanUser modelPendidikanUser = modelPendidikanUsers.get(position);
        holder.tingkat.setText(modelPendidikanUser.getTingkat());
        holder.nama.setText(modelPendidikanUser.getPendidikan());
    }

    @Override
    public int getItemCount() {
        return modelPendidikanUsers.size();
    }

    public void setModelPendidikanUsers(ArrayList<ModelPendidikanUser> modelPendidikanUsers) {
        this.modelPendidikanUsers = modelPendidikanUsers;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tingkat, nama;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tingkat = itemView.findViewById(R.id.tingkat_pendidikan_profile);
            nama = itemView.findViewById(R.id.nama_pendidikan_profile);
        }
    }
}
