package com.reynaldlancer.reynaldlancer;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class MisiOrder extends Fragment {


    public MisiOrder() {
        // Required empty public constructor
    }

    TabLayout tablayout;
    TabItem creator, hero;
    Button button_tidak_ada_misi;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_misi_order, container, false);
        tablayout = v.findViewById(R.id.misi_tablayout);
        creator = v.findViewById((R.id.ascreator_misi));
        hero = v.findViewById(R.id.ashero_misi);
        button_tidak_ada_misi = v.findViewById(R.id.tidakadamisi_btn);

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("tab", String.valueOf(tab.getPosition()));
                if (tab.getPosition() == 0){
                    button_tidak_ada_misi.setText("Join misi");
                }
                else{
                    button_tidak_ada_misi.setText("BUAT misi");
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
