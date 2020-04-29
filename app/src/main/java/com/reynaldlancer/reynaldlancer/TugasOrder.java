package com.reynaldlancer.reynaldlancer;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class TugasOrder extends Fragment {


    public TugasOrder() {
        // Required empty public constructor
    }


    TabLayout tablayout;
    TabItem creator, hero;
    Button button_tidak_ada_tugas;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_tugas_order, container, false);
            tablayout = v.findViewById(R.id.tugas_tablayout);
            creator = v.findViewById((R.id.ascreator_tugas));
            hero = v.findViewById(R.id.ashero_tugas);
            button_tidak_ada_tugas = v.findViewById(R.id.tidakadatugas_btn);

            tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    Log.d("tab", String.valueOf(tab.getPosition()));
                    if (tab.getPosition() == 0){
                        button_tidak_ada_tugas.setText("AMBIL TUGAS");
                    }
                    else{
                        button_tidak_ada_tugas.setText("BUAT TUGAS");
                    }
                }
                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

        return v;
    }

}
