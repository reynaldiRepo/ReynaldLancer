package com.reynaldlancer.reynaldlancer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaldoActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    TabLayout tabfaqhis;
    Button tambah_saldo, tarik_saldo;
    ImageView backward;
    TextView saldo_tv;

    //user api
    RestApiUSER user_api;
    String User;
    ModelUser modelUser;

    //loading
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saldo);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.saldo_faq_history_fragment, new FaqSaldoFragment()).commit();


        backward = findViewById(R.id.dompet_nav_back);
        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SaldoActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });


        tabfaqhis = findViewById(R.id.tab_faq_his);
        tabfaqhis.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        fragmentManager.beginTransaction().replace(R.id.saldo_faq_history_fragment, new FaqSaldoFragment()).commit();
                        break;
                    case 1:
                        fragmentManager.beginTransaction().replace(R.id.saldo_faq_history_fragment, new HistorySaldo()).commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        tambah_saldo = findViewById(R.id.tambah_saldo_in);
        tambah_saldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SaldoActivity.this, FormAddSaldoActivity.class);
                startActivity(intent);
            }
        });

        tarik_saldo = findViewById(R.id.tarik_saldo_in);
        tarik_saldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SaldoActivity.this, TarikActivity.class);
                startActivity(intent);
            }
        });

        //initiate saldo component
        saldo_tv = findViewById(R.id.saldo_on_tambah_saldo);

        //initiate loadingdialog
        loadingDialog = new LoadingDialog();
        loadingDialog.show(getSupportFragmentManager(), "load");
        //user saldo
        User = new SessionController().getActiveUser(this);
        user_api = RetrofitClient.getRetrofitInstance().create(RestApiUSER.class);
        Call<ModelUser> get_user = user_api.getUser(User);
        get_user.enqueue(new Callback<ModelUser>() {
            @Override
            public void onResponse(Call<ModelUser> call, Response<ModelUser> response) {
                if (response.isSuccessful()) {
                    modelUser = response.body();
                    saldo_tv.setText(new StringFormater().toCurrency(modelUser.getSaldo().toString()));
                    loadingDialog.dismiss();
                } else {
                    Toast.makeText(SaldoActivity.this, "Somthing wromg", Toast.LENGTH_SHORT).show();
                    loadingDialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<ModelUser> call, Throwable t) {
                Toast.makeText(SaldoActivity.this, "Somthing wromg", Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
            }
        });


    }
}
