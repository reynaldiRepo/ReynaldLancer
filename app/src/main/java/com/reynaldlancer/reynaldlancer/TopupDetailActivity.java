package com.reynaldlancer.reynaldlancer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopupDetailActivity extends AppCompatActivity {


    TextView nominal, idtransaksi;
    Button konfirm, batal;

    //User Api
    RestApiUSER user_api;
    //Loading
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup_detail);
        final Intent intent = getIntent();
        nominal = findViewById(R.id.nominal_view_detail);
        konfirm = findViewById(R.id.konfirmasi);
        idtransaksi = findViewById(R.id.id_transaksi_add_saldo);
        batal = findViewById(R.id.batalkan_topup);

        String transaksi_id = intent.getStringExtra("id");
        idtransaksi.setText(transaksi_id);

        //initiate user api
        user_api = RetrofitClient.getRetrofitInstance().create(RestApiUSER.class);
        loadingDialog = new LoadingDialog();

        //get transaksi_detail;
        loadingDialog.show(getSupportFragmentManager(), "load");
        Call<ModelTransaksiSaldo> get_transaksi = user_api.get_transaksi(transaksi_id);
        get_transaksi.enqueue(new Callback<ModelTransaksiSaldo>() {
            @Override
            public void onResponse(Call<ModelTransaksiSaldo> call, Response<ModelTransaksiSaldo> response) {
                loadingDialog.dismiss();
                nominal.setText(new StringFormater().toCurrency(response.body().getJumlah().toString()));
            }

            @Override
            public void onFailure(Call<ModelTransaksiSaldo> call, Throwable t) {
                loadingDialog.dismiss();
                Toast.makeText(TopupDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



        konfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TopupDetailActivity.this, KonfirmasiTopUp.class);
                i.putExtra("id",transaksi_id);
                startActivity(i);
            }
        });

        batal.setOnClickListener(v -> {
            loadingDialog.show(getSupportFragmentManager(), "load");
            Call<JsonObject> update_transaksi = user_api.update_transaksi(transaksi_id, "GAGAL");
            update_transaksi.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    loadingDialog.dismiss();
                    startActivity(new Intent(TopupDetailActivity.this, SaldoActivity.class));
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    loadingDialog.dismiss();
                    Toast.makeText(TopupDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });


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
