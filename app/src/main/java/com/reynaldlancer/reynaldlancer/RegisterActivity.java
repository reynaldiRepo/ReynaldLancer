package com.reynaldlancer.reynaldlancer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    TextView link_to_login;
    EditText ET_nama, ET_email, ET_no_telephone, ET_password;
    LoadingDialog loading;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //for login link==============================================
        link_to_login = findViewById(R.id.login_link);
        link_to_login.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));
        //===============================================================

        //register trigger
        ET_nama = findViewById(R.id.register_nama_EditText);
        ET_email = findViewById(R.id.register_email_EditText);
        ET_no_telephone = findViewById(R.id.register_no_telp_EditText);
        ET_password = findViewById(R.id.register_password_EditText);

        Button RegisterBTN = findViewById(R.id.btn_register);
        RegisterBTN.setOnClickListener(v -> register());

        //inisilaisasi loading dialog
        fm = getSupportFragmentManager();
        loading = new LoadingDialog();
    }

    private void register() {
//        register proses
        EDValidation validator = new EDValidation();
        if (validator.required(ET_nama) && validator.required(ET_no_telephone)
                && validator.input_password(ET_password, 8) && validator.email_validate(ET_email)) {
            //panggil loading
            loading.show(fm, "show Loading");
            //make post
            RestApiUSER user = RetrofitClient.getRetrofitInstance().create(RestApiUSER.class);
            Call<JsonObject> call = user.register(
                    ET_email.getText().toString(),
                    ET_nama.getText().toString(),
                    ET_no_telephone.getText().toString(),
                    ET_password.getText().toString()
            );
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(response.isSuccessful()){
                        loading.dismiss();
                        Toast.makeText(RegisterActivity.this, response.body().get("status").getAsString(), Toast.LENGTH_SHORT).show();
                        boolean status = response.body().get("status").getAsBoolean();
                        if (status){
                            SessionController session = new SessionController();
                            session.addSession(RegisterActivity.this, ET_email.getText().toString());
                            startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                            finish();
                        }else{
                            Toast.makeText(RegisterActivity.this, "Email sudah digunakan", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(RegisterActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    Log.e("Error", t.getMessage(),t );
                }
            });

        } else {
            Toast.makeText(this, "Input Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
}
