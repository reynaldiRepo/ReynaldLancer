package com.reynaldlancer.reynaldlancer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVadapterDomisili  extends RecyclerView.Adapter<RVadapterDomisili.ViewHolder>  {
    public RVadapterDomisili(ArrayList<ModelDomisili> domisiliModel, Context context, OnClickDomisiliInterface onClickDomisiliInterface, int state) {
        this.domisiliModel = domisiliModel;
        this.context = context;
        this.onClickDomisiliInterface = onClickDomisiliInterface;
        this.state = state;
    }

    public ArrayList<ModelDomisili> getDomisiliModel() {
        return domisiliModel;
    }

    public void setDomisiliModel(ArrayList<ModelDomisili> domisiliModel) {
        this.domisiliModel = domisiliModel;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public OnClickDomisiliInterface getOnClickDomisiliInterface() {
        return onClickDomisiliInterface;
    }

    public void setOnClickDomisiliInterface(OnClickDomisiliInterface onClickDomisiliInterface) {
        this.onClickDomisiliInterface = onClickDomisiliInterface;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    ArrayList<ModelDomisili> domisiliModel;
    Context context;
    private OnClickDomisiliInterface onClickDomisiliInterface;
    private int state = 0 ;




    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text_place, id_place;
        RelativeLayout dom_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_place = itemView.findViewById(R.id.domisili_text_place);
            id_place = itemView.findViewById(R.id.id_domisili);
            dom_layout = itemView.findViewById(R.id.layout_dom);
        }
    }

    @NonNull
    @Override
    public RVadapterDomisili.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.domisiliinsi_rv, parent, false);
        return new RVadapterDomisili.ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull RVadapterDomisili.ViewHolder holder, int position) {
        ModelDomisili domisili = domisiliModel.get(position);
        holder.text_place.setText(domisili.getName());
        holder.id_place.setText(domisili.getId());
        holder.dom_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state += 1;
                onClickDomisiliInterface.onClickDomisiliPlace(holder.text_place.getText().toString(), holder.id_place.getText().toString(), state);
            }
        });
    }

    @Override
    public int getItemCount() {
        return domisiliModel.size();
    }
}