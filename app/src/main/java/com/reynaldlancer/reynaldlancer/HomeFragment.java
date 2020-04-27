package com.reynaldlancer.reynaldlancer;


import android.content.Context;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.google.android.material.navigation.NavigationView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    NavigationView side_nav;
    Button button_nav;
    DrawerLayout drawerLayout;
    ViewFlipper promo_layout;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        side_nav = v.findViewById(R.id.side_nav);
        button_nav = v.findViewById(R.id.sidebar_button);
        drawerLayout = v.findViewById(R.id.drawer_container);
        promo_layout = v.findViewById(R.id.promo);

        //for images promo
        int image[] = {R.mipmap.slide1, R.mipmap.slide2, R.mipmap.slide3};
        for (int i = 0; i < image.length; i++){
            runpromo(image[i]);
        }

        button_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        return  v;
    }

    private  void  runpromo(int Image){
        ImageView imageView = new ImageView(getActivity());
        imageView.setBackgroundResource(Image);

        promo_layout.addView(imageView);
        promo_layout.setFlipInterval(4000);
        promo_layout.setAutoStart(true);

        promo_layout.setInAnimation(getActivity(), android.R.anim.slide_in_left);
        promo_layout.setOutAnimation(getActivity(), android.R.anim.slide_out_right);

    }

}
