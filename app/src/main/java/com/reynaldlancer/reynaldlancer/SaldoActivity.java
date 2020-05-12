package com.reynaldlancer.reynaldlancer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class SaldoActivity extends AppCompatActivity {

    ChipNavigationBar tollbar;
    FragmentManager fragmentManager;
    TabLayout tabfaqhis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saldo);
        tollbar = findViewById(R.id.saldo_tollbar);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.saldo_faq_history_fragment, new FaqSaldoFragment()).commit();
        tollbar = findViewById(R.id.saldo_tollbar);
        tollbar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                    Intent intent = new Intent(SaldoActivity.this, HomeActivity.class);
                    startActivity(intent);
            }
        });

        tabfaqhis = findViewById(R.id.tab_faq_his);
        tabfaqhis.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0 :
                        fragmentManager.beginTransaction().replace(R.id.saldo_faq_history_fragment, new FaqSaldoFragment()).commit();
                        break;
                    case 1 :
                        fragmentManager.beginTransaction().replace(R.id.saldo_faq_history_fragment, new HistorySaldo()).commit();
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });



    }
}
