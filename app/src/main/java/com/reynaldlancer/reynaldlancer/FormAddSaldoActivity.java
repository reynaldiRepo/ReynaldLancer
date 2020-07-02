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

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormAddSaldoActivity extends AppCompatActivity {

    EditText Nominal;
    Button topup_submit;
    ImageView nav_back;

    //User
    String User;

    //User api
    RestApiUSER user_api;

    //loading dialog
    LoadingDialog loadingDialog;
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

        //initiate loading
        loadingDialog = new LoadingDialog();
        User = new SessionController().getActiveUser(this);
        user_api = RetrofitClient.getRetrofitInstance().create(RestApiUSER.class);

        topup_submit.setOnClickListener(v -> {
            loadingDialog.show(getSupportFragmentManager(), "load");
            if (TextUtils.isEmpty(Nominal.getText())){
                Toast.makeText(FormAddSaldoActivity.this, "Isikan nominal yang akan disiikan", Toast.LENGTH_SHORT).show();
            }else{
                int nominal = Integer.parseInt(Nominal.getText().toString());
                if (nominal >= 15000){

                    java.util.Date date = new java.util.Date();
                    Call<ModelTransaksiSaldo> create_transaksi = user_api.create_transaksi(User,
                            Nominal.getText().toString(),
                            "PROSES",
                            date.toString());
                    create_transaksi.enqueue(new Callback<ModelTransaksiSaldo>() {
                        @Override
                        public void onResponse(Call<ModelTransaksiSaldo> call, Response<ModelTransaksiSaldo> response) {
                            loadingDialog.dismiss();
                            Intent i = new Intent(FormAddSaldoActivity.this, TopupDetailActivity.class);
                            i.putExtra("id", response.body().get_id());
                            startActivity(i);
                        }

                        @Override
                        public void onFailure(Call<ModelTransaksiSaldo> call, Throwable t) {
                            loadingDialog.dismiss();
                            Toast.makeText(FormAddSaldoActivity.this, "gagal", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    loadingDialog.dismiss();
                    Toast.makeText(FormAddSaldoActivity.this, "Minimal Top Up Rp. 15.000", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
}
