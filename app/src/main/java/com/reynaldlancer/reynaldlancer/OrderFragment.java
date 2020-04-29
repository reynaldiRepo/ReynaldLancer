package com.reynaldlancer.reynaldlancer;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {


    public OrderFragment() {
        // Required empty public constructor
    }


    ChipNavigationBar ordermenu;
    FragmentManager fragmentManager;
    Fragment fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_order, container, false);
        fragment = new TugasOrder();
        fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.order_record, fragment).commit();



        ordermenu = v.findViewById(R.id.order_menu);
        ordermenu.setItemSelected(R.id.Tugas_menu, true);
        ordermenu.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                fragment = null;
                switch (id) {
                    case R.id.Tugas_menu:
                        fragment = new TugasOrder();
                        break;
                    case R.id.Misi_menu:
                        fragment = new MisiOrder();
                        break;
                    case R.id.Poster_menu:
                        fragment = new PosterOrder();
                        break;
                }
                if (fragment != null){
                    fragmentManager = getChildFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.order_record, fragment).commit();
                }else{
                    Log.e("error", "Error Get Fragment");
                }
            }
        });


        return  v;
    }

}
