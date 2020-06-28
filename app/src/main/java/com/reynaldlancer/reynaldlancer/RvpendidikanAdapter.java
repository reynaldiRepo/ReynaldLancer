package com.reynaldlancer.reynaldlancer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class RvpendidikanAdapter extends RecyclerView.Adapter<RvpendidikanAdapter.ViewHolder> {
    Context context;
    ArrayList<ModelPendidikanUser> modelPendidikanUsers;
    onItemPendidikanClick deletCilckInterface;

    public RvpendidikanAdapter(Context context, ArrayList<ModelPendidikanUser> modelPendidikanUsers, onItemPendidikanClick deletCilckInterface) {
        this.context = context;
        this.modelPendidikanUsers = modelPendidikanUsers;
        this.deletCilckInterface = deletCilckInterface;
    }

    public void setModelPendidikanUsers(ArrayList<ModelPendidikanUser> modelPendidikanUsers) {
        this.modelPendidikanUsers = modelPendidikanUsers;
    }




    @NonNull
    @Override
    public RvpendidikanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.rv_pendidikan, parent, false);
        return new RvpendidikanAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RvpendidikanAdapter.ViewHolder holder, int position) {
        ModelPendidikanUser modePendidikan = modelPendidikanUsers.get(position);
        holder.tingkat.setText(modePendidikan.getTingkat());
        holder.namaPendidikan.setText(modePendidikan.getPendidikan());
        holder.delete.setOnClickListener(v -> {deletCilckInterface.onDeletePendidikan(modePendidikan.get_id());});
        holder.update.setOnClickListener(v -> {deletCilckInterface.onUpdatePendidikan(modePendidikan.get_id(),
                holder.namaPendidikan.getText().toString(), modePendidikan.getTingkat());});

    }

    @Override
    public int getItemCount() {
        return modelPendidikanUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tingkat;
        EditText namaPendidikan;
        Button delete, update;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tingkat = itemView.findViewById(R.id.tingkat);
            namaPendidikan = itemView.findViewById(R.id.nama_pendidikan);
            delete = itemView.findViewById(R.id.delete_pendidikan_btn);
            update = itemView.findViewById(R.id.update_pendidikan_btn);
        }
    }
}
