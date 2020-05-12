package com.reynaldlancer.reynaldlancer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class FormAddSaldoActivity extends AppCompatActivity {

    EditText Nominal;
    Button topup_submit;
    ImageView nav_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_add_saldo);
        Nominal = findViewById(R.id.nominal_topup);
        topup_submit = findViewById(R.id.topup_btn);
        nav_back = findViewById(R.id.nav_back);

        nav_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FormAddSaldoActivity.this, SaldoActivity.class));
            }
        });

        topup_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(Nominal.getText())){
                    Toast.makeText(FormAddSaldoActivity.this, "Isikan nominal yang akan disiikan", Toast.LENGTH_SHORT).show();
                }else{
                    int nominal = Integer.parseInt(Nominal.getText().toString());
                    if (nominal >= 15000){
                        Toast.makeText(FormAddSaldoActivity.this, "kerja cok", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(FormAddSaldoActivity.this, "Minimal Top Up Rp. 15.000", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
}
