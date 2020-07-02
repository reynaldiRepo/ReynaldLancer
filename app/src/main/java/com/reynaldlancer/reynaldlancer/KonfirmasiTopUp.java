package com.reynaldlancer.reynaldlancer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KonfirmasiTopUp extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 71 ;
    //User
    String User;
    Integer saldo_tambah;

    //Loading
    LoadingDialog loadingDialog;

    //component
    TextView nominal;
    EditText  rek, nama;
    ImageView bukti;
    Button konfirm;

    //user api
    RestApiUSER user_api;

    //transaksi id
    String Transaksi_id;

    //uri bukti
    Uri buktipath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_top_up);
        loadingDialog = new LoadingDialog();
        user_api = RetrofitClient.getRetrofitInstance().create(RestApiUSER.class);

        User = new SessionController().getActiveUser(this);


        //assign component
        nominal = findViewById(R.id.konfirmasi_top_up_jumlah);
        rek = findViewById(R.id.no_rek_top_up);
        nama = findViewById(R.id.nama_rek_top_up);
        bukti = findViewById(R.id.insert_bukti_top_up);
        konfirm = findViewById(R.id.konfirmasi_top_up);

        //get id from intent;
        Intent i = getIntent();
        Transaksi_id = i.getStringExtra("id");

        loadingDialog.show(getSupportFragmentManager(), "load");
        //load data transaksi
        Call<ModelTransaksiSaldo> get_transaksi = user_api.get_transaksi(Transaksi_id);
        get_transaksi.enqueue(new Callback<ModelTransaksiSaldo>() {
            @Override
            public void onResponse(Call<ModelTransaksiSaldo> call, Response<ModelTransaksiSaldo> response) {
                loadingDialog.dismiss();
                nominal.setText(new StringFormater().toCurrency(response.body().getJumlah().toString()));
                saldo_tambah = response.body().getJumlah();
            }

            @Override
            public void onFailure(Call<ModelTransaksiSaldo> call, Throwable t) {
                loadingDialog.dismiss();
                Toast.makeText(KonfirmasiTopUp.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        konfirm.setOnClickListener(v -> {konfirm_top_up();});
        bukti.setOnClickListener(v -> {chooseImage();});

    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //upload image
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null
                && data.getData() != null) {
            buktipath = data.getData();
            //place on image view
            Glide.with(this).load(buktipath).centerCrop().into(bukti);
        }else{
            Toast.makeText(this, "No Image Picked", Toast.LENGTH_SHORT).show();
        }
    }


    private void konfirm_top_up() {
        EDValidation validator = new EDValidation();
        if(buktipath != null && validator.required(rek) && validator.required(nama)){
            loadingDialog.show(getSupportFragmentManager(), "load");
            Call<JsonObject> update = user_api.update_transaksi(Transaksi_id, "SUKSES");
            update.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Call<JsonObject> tambah = user_api.tambah_saldo(User, saldo_tambah);
                    tambah.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            loadingDialog.dismiss();
                            startActivity(new Intent(KonfirmasiTopUp.this, SaldoActivity.class));
                            finish();
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            loadingDialog.dismiss();
                            Toast.makeText(KonfirmasiTopUp.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    loadingDialog.dismiss();
                    Toast.makeText(KonfirmasiTopUp.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
