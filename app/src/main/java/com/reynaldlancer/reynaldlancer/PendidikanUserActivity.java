package com.reynaldlancer.reynaldlancer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendidikanUserActivity extends AppCompatActivity implements onItemPendidikanClick {

    String User;

    //model pendidikan
    ArrayList<ModelPendidikanUser> modelPendidikanUsers =  new ArrayList<>();

    //component;
    Spinner tingkat_pendidikan;
    EditText nama_pendidikan;
    Button save_pendidikan;
    RecyclerView RV_Daftar_Pendidikan;

    //loading;
    LoadingDialog loadingDialog;

    //api user
    RestApiUSER api_user;

    //Recycler View Adpter
    RvpendidikanAdapter rvpendidikanAdapter;

    HashMap<String, Integer> tingkat_pendidikan_hashing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendidikan_user);

        //user
        SessionController sessionController = new SessionController();
        User = sessionController.getActiveUser(this);

        //api
        api_user = RetrofitClient.getRetrofitInstance().create(RestApiUSER.class);

        //loading
        loadingDialog = new LoadingDialog();

        //component
        tingkat_pendidikan = findViewById(R.id.tingkat_pendidikan_input);
        nama_pendidikan = findViewById(R.id.nama_pendidikan_input);
        save_pendidikan = findViewById(R.id.save_pendidikan_btn);
        RV_Daftar_Pendidikan = findViewById(R.id.daftar_pendidikan_user);

        //for spinner
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.tingkat_pendidikan,
                android.R.layout.simple_spinner_dropdown_item);
        tingkat_pendidikan.setAdapter(adapter);

//        for save pendidikan
        save_pendidikan.setOnClickListener(v -> {save_pendidikan();});

        tingkat_pendidikan_hashing = new HashMap<>();
        int idx = 0;
        for (String p : getResources().getStringArray(R.array.tingkat_pendidikan)){
            tingkat_pendidikan_hashing.put(p, idx);
            idx ++;
        }

        //initiate RV
        rvpendidikanAdapter = new RvpendidikanAdapter(this, modelPendidikanUsers, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RV_Daftar_Pendidikan.setLayoutManager(linearLayoutManager);
        RV_Daftar_Pendidikan.setAdapter(rvpendidikanAdapter);

        loadData();

    }

    private void save_pendidikan() {
        loadingDialog.show(getSupportFragmentManager(), "load");
        if (new EDValidation().required(nama_pendidikan)) {
            Call<JsonObject> save_pendidikan = api_user.add_pendidikan(User, nama_pendidikan.getText().toString(),
                    tingkat_pendidikan.getSelectedItem().toString());
            save_pendidikan.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(response.isSuccessful()){
                        if(response.body().get("status").getAsBoolean()){
                            loadingDialog.dismiss();
                            loadData();
                        }else{
                            loadingDialog.dismiss();
                            Toast.makeText(PendidikanUserActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        loadingDialog.dismiss();
                        Toast.makeText(PendidikanUserActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    loadingDialog.dismiss();
                    Toast.makeText(PendidikanUserActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void loadData(){
        loadingDialog.show(getSupportFragmentManager(), "load");
        Call<ArrayList<ModelPendidikanUser>> get_pendidikan = api_user.get_pendidikan(User);
        get_pendidikan.enqueue(new Callback<ArrayList<ModelPendidikanUser>>() {
            @Override
            public void onResponse(Call<ArrayList<ModelPendidikanUser>> call, Response<ArrayList<ModelPendidikanUser>> response) {
                if(response.isSuccessful()){
                    loadingDialog.dismiss();
                    modelPendidikanUsers.clear();
                    modelPendidikanUsers = response.body();
                    rvpendidikanAdapter.notifyItemRangeRemoved(0,0);
                    rvpendidikanAdapter.setModelPendidikanUsers(modelPendidikanUsers);
                    rvpendidikanAdapter.notifyDataSetChanged();
                }
                else{
                    loadingDialog.dismiss();
                    Toast.makeText(PendidikanUserActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ModelPendidikanUser>> call, Throwable t) {
                loadingDialog.dismiss();
                Toast.makeText(PendidikanUserActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void backward(View view) {
        onBackPressed();
    }

    @Override
    public void onDeletePendidikan(String _id) {
        loadingDialog.show(getSupportFragmentManager(), "load");
        Call<JsonObject> delete_pendidikan = api_user.del_pendidikan(_id);
        delete_pendidikan.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().get("status").getAsBoolean()){
                    loadingDialog.dismiss();
                    loadData();
                }else{
                    loadingDialog.dismiss();
                    Toast.makeText(PendidikanUserActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loadingDialog.dismiss();
                Toast.makeText(PendidikanUserActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onUpdatePendidikan(String _id, String pendidikan, String tingkat) {
        loadingDialog.show(getSupportFragmentManager(), "load");
        Call<JsonObject> update_pendidikan = api_user.update_pendidikan(_id, pendidikan, tingkat);
        update_pendidikan.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().get("status").getAsBoolean()){
                    loadingDialog.dismiss();
                    loadData();
                }else{
                    loadingDialog.dismiss();
                    Toast.makeText(PendidikanUserActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loadingDialog.dismiss();
                Toast.makeText(PendidikanUserActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
