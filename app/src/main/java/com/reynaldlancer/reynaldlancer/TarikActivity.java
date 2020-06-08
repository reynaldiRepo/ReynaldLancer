package com.reynaldlancer.reynaldlancer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class TarikActivity extends AppCompatActivity {

    Spinner bank_tujuan ;
    ImageView back_nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarik);
        bank_tujuan = findViewById(R.id.bank_tujuan_tarik);
        ArrayAdapter<CharSequence> adapterBankTujuan = ArrayAdapter.createFromResource(this, R.array.bank_arr,
                android.R.layout.simple_spinner_dropdown_item);
        adapterBankTujuan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bank_tujuan.setAdapter(adapterBankTujuan);

        back_nav = findViewById(R.id.nav_back_from_tarik);
        back_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TarikActivity.this, SaldoActivity.class);
                startActivity(intent);
            }
        });

    }
}

