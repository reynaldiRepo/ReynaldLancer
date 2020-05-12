package com.reynaldlancer.reynaldlancer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class TopupDetailActivity extends AppCompatActivity {

    TextView nominal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup_detail);
        Intent intent = getIntent();
        nominal = findViewById(R.id.nominal_view_detail);
        double number = intent.getIntExtra("amount", 0);
        nominal.setText("Rp. " + this.curecncyFormat(number));
    }

    private String curecncyFormat(double number){
        DecimalFormat formater = new DecimalFormat("###,###,###.0-");
        String f = formater.format(number);
        return f.replace(',', '.').replace(".0-", ",0-");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent  = new Intent(TopupDetailActivity.this, SaldoActivity.class);
        startActivity(intent);
    }
}
