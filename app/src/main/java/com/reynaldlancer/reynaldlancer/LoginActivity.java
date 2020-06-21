package com.reynaldlancer.reynaldlancer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView link_to_regist;
    EditText ET_email, ET_password;
    Button login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ET_email = findViewById(R.id.login_email_EditText);
        ET_password = findViewById(R.id.login_password_EditText);
        login_btn = findViewById(R.id.btn_login);
        link_to_regist = findViewById(R.id.register_link);
        link_to_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        //login process
        login_btn.setOnClickListener(v->{
            Login();
        });

    }

    private void Login() {
        EDValidation validator = new EDValidation();
        if (validator.email_validate(ET_email) && validator.required(ET_password)){
            //showing loading
            LoadingDialog loading = new LoadingDialog();
            loading.show(getSupportFragmentManager(), "Loading");

            //login request
            RestApiUSER login = RetrofitClient.getRetrofitInstance().create(RestApiUSER.class);
            Call<JsonObject> login_proses = login.login(ET_email.getText().toString(), ET_password.getText().toString());
            login_proses.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()){
                        boolean status = response.body().get("status").getAsBoolean();
                        if (status){
                            loading.dismiss();
                            SessionController session = new SessionController();
                            session.addSession(LoginActivity.this, ET_email.getText().toString());
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();

                        }else{
                            loading.dismiss();
                            Toast.makeText(LoginActivity.this, "Email atau Password Salah", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        loading.dismiss();
                        Toast.makeText(LoginActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    Log.e("Error Login", t.getMessage(), t);
                }
            });
        }else {
            Toast.makeText(this, "Input Errror", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAndRemoveTask();
        System.exit(0);
    }
}
