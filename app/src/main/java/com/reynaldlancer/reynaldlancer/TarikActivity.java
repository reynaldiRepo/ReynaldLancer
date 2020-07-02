package com.reynaldlancer.reynaldlancer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TarikActivity extends AppCompatActivity {

    Spinner bank_tujuan ;
    ImageView back_nav;

    TextView nominal;
    EditText no_req, nominal_input;
    Button konfirm_top_up;


    //User
    String User;

    //Rest api
    RestApiUSER user_api;

    //loading
    LoadingDialog loadingDialog;

    ModelUser modelUser;

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

        //assinn user component
        User = new SessionController().getActiveUser(this);
        user_api = RetrofitClient.getRetrofitInstance().create(RestApiUSER.class);

        //loadng
        loadingDialog = new LoadingDialog();


//        assingn
        nominal = findViewById(R.id.nominal_on_tarik);
        no_req = findViewById(R.id.no_rek_on_tarik);
        nominal_input = findViewById(R.id.nominal_input_on_tarik);

        konfirm_top_up  = findViewById(R.id.btn_konfirm_tarik);

        //assing model user wit load data
        loadingDialog.show(getSupportFragmentManager(), "load");
        Call<ModelUser> get_data_user = user_api.getUser(User);
        get_data_user.enqueue(new Callback<ModelUser>() {
            @Override
            public void onResponse(Call<ModelUser> call, Response<ModelUser> response) {
                loadingDialog.dismiss();
                modelUser = response.body();
                nominal.setText(new StringFormater().toCurrency(modelUser.getSaldo().toString()));
            }

            @Override
            public void onFailure(Call<ModelUser> call, Throwable t) {
                loadingDialog.dismiss();
                Toast.makeText(TarikActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        konfirm_top_up.setOnClickListener(v -> {
            loadingDialog.show(getSupportFragmentManager(), "load");
            if(new EDValidation().required(nominal_input) && new EDValidation().required(no_req) &&
            modelUser.getSaldo() > Integer.parseInt(nominal_input.getText().toString())) {
                Call<JsonObject> tarik = user_api.tarik_saldo(User, Integer.parseInt(nominal_input.getText().toString()));
                tarik.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        loadingDialog.dismiss();
                        startActivity(new Intent(TarikActivity.this, SaldoActivity.class));
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        loadingDialog.dismiss();
                        Toast.makeText(TarikActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                loadingDialog.dismiss();
                Toast.makeText(this, "Jumlah Tarik Salah", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

