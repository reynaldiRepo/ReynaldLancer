package com.reynaldlancer.reynaldlancer;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistorySaldo extends Fragment {


    public HistorySaldo() {
        // Required empty public constructor
    }

    RecyclerView RVhiistory;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_history_saldo, container, false);
        RVhiistory = v.findViewById(R.id.history_RV);
        LinearLayoutManager historyLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);
        RVhiistory.setLayoutManager(historyLayoutManager);
        RVhiistory.setItemAnimator(new DefaultItemAnimator());

        //dummy rv
        String dummy[][] = {{"Rp. 500.000", "01, January 2020", "Succcess"}, {"Rp. 500.000", "06, January 2020", "Succcess"}, {"Rp. 200.000", "12, January 2020", "Proccess"}};
        ArrayList<HistoryModel> historyModels = new ArrayList<HistoryModel>();
        for (int i = 0; i < dummy.length; i++){
            historyModels.add(new HistoryModel(dummy[i][0], dummy[i][1], dummy[i][2]));
        }
        RvHistoryAdapter rvHistoryAdapter = new RvHistoryAdapter( historyModels, getActivity());
        RVhiistory.setAdapter(rvHistoryAdapter);
        return v;
    }

}
