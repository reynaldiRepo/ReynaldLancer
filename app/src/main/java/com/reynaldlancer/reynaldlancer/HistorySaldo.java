package com.reynaldlancer.reynaldlancer;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistorySaldo extends Fragment implements  onDetaliTransaksiClick {


    public HistorySaldo() {
        // Required empty public constructor
    }

    RecyclerView RVhiistory;

    //User
    String User;
    RestApiUSER user_api;
    LoadingDialog loadingDialog;

    ArrayList<ModelTransaksiSaldo> modelTransaksiSaldoArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_history_saldo, container, false);

        //User
        User = new SessionController().getActiveUser(getActivity());
        modelTransaksiSaldoArrayList = new ArrayList<>();

        //initiate api
        user_api = RetrofitClient.getRetrofitInstance().create(RestApiUSER.class);

        //setting history RV
        RVhiistory = v.findViewById(R.id.history_RV);
        LinearLayoutManager historyLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);
        RVhiistory.setLayoutManager(historyLayoutManager);
        RVhiistory.setItemAnimator(new DefaultItemAnimator());
        RvHistoryAdapter rvHistoryAdapter = new RvHistoryAdapter(modelTransaksiSaldoArrayList, getActivity(), this);
        RVhiistory.setAdapter(rvHistoryAdapter);

        loadingDialog = new LoadingDialog();
        loadingDialog.show(getChildFragmentManager(), "load");
        Call<ArrayList<ModelTransaksiSaldo>> get_transaksi = user_api.get_transaksi_all(User);
        get_transaksi.enqueue(new Callback<ArrayList<ModelTransaksiSaldo>>() {
            @Override
            public void onResponse(Call<ArrayList<ModelTransaksiSaldo>> call, Response<ArrayList<ModelTransaksiSaldo>> response) {
                loadingDialog.dismiss();
                modelTransaksiSaldoArrayList = response.body();
                rvHistoryAdapter.notifyItemRangeRemoved(0,0);
                rvHistoryAdapter.setHistoryModels(modelTransaksiSaldoArrayList);
                rvHistoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<ModelTransaksiSaldo>> call, Throwable t) {
                loadingDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        return v;
    }

    @Override
    public void onItemTransaksiClick(String _id) {
        Intent i = new Intent(getActivity(), TopupDetailActivity.class);
        i.putExtra("id", _id);
        startActivity(i);
    }
}
