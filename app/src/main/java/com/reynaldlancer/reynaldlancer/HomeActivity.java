package com.reynaldlancer.reynaldlancer;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName() ;
    ChipNavigationBar bottom_vav;
    FragmentManager fragmentManager;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //set start fragment
        fragment = new HomeFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmaent_place, fragment).commit();


        //set bottom nav
        bottom_vav = findViewById(R.id.bottom_nav);
        bottom_vav.setItemSelected(R.id.Home, true);
        bottom_vav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                fragment = null;
                switch (id) {
                    case R.id.Home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.Order:
                        fragment = new OrderFragment();
                        break;
                    case R.id.Inbox:
                        fragment = new InboxFragment();
                        break;
                    case R.id.Profile:
                        fragment = new ProfileFragment();
                        break;
                }
                if (fragment != null){
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragmaent_place, fragment).commit();
                }else{
                    Log.e(TAG, "Error Get Fragment");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAndRemoveTask();
        System.exit(0);
    }
}
