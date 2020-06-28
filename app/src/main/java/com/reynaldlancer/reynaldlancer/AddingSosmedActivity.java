package com.reynaldlancer.reynaldlancer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonObject;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddingSosmedActivity extends AppCompatActivity implements onItemSosmedClick {

    //for backward
    ImageView back_nav;

    //sosmed data component
    Spinner sosmed_type_spinner;
    EditText linkSosmed;
    Button add_sosmed_btn;
    //RV
    RecyclerView RV_sosmed;
    //user data
    String User;

    //UserApi
    RestApiUSER user_api;

    //loading
    LoadingDialog loadingDialog;

    //for model sosmed
    ArrayList<ModelSosmed> sosmedUserArrayList;

    //rvadapter for sosmed model
    RVsosmedAdapter rVsosmedAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_sosmed);

        //backward activity
        back_nav = findViewById(R.id.add_sosmed_nav_back);
        back_nav.setOnClickListener(v -> {onBackPressed();});

//        initiate for load
        loadingDialog = new LoadingDialog();

        //load user
        SessionController sessionController = new SessionController();
        User = sessionController.getActiveUser(this);

        //initiate api user
        user_api = RetrofitClient.getRetrofitInstance().create(RestApiUSER.class);

        //data for sosmed type & icon
        HashMap<String, MaterialDrawableBuilder.IconValue> iconTypeSosmedHas = new HashMap<>();
        String[] sosmed_type = new String[getResources().getStringArray(R.array.sosmed_type).length];
        MaterialDrawableBuilder.IconValue[] iconArr = {MaterialDrawableBuilder.IconValue.INSTAGRAM,
                MaterialDrawableBuilder.IconValue.FACEBOOK, MaterialDrawableBuilder.IconValue.LINKEDIN, MaterialDrawableBuilder.IconValue.TWITTER,
                MaterialDrawableBuilder.IconValue.WEB};
        int count = 0;
        for (String s : getResources().getStringArray(R.array.sosmed_type)){
            sosmed_type[count] = s;
            iconTypeSosmedHas.put(s, iconArr[count]);
            count++;
        }

//        for Spinner
        sosmed_type_spinner = findViewById(R.id.jenis_sosmed_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sosmed_type,
                android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sosmed_type_spinner.setAdapter(adapter);

        //for other adding sosmed field
        linkSosmed = findViewById(R.id.link_sosmed_input);
        add_sosmed_btn = findViewById(R.id.add_sosmed_btn);
        add_sosmed_btn.setOnClickListener(v -> {save_data();});

        //for sosmed rv
        sosmedUserArrayList = new ArrayList<>();
        RV_sosmed = findViewById(R.id.RV_Sosmed);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddingSosmedActivity.this,
                LinearLayoutManager.VERTICAL, false);
        RV_sosmed.setLayoutManager(linearLayoutManager);
        rVsosmedAdapter = new RVsosmedAdapter(sosmedUserArrayList, iconTypeSosmedHas, this,this);
        RV_sosmed.setAdapter(rVsosmedAdapter);
        load_data();

    }

    private void save_data() {
        loadingDialog.show(getSupportFragmentManager(), "load");
        EDValidation validation = new EDValidation();
        if(validation.required(linkSosmed)){
            Call<JsonObject> save_sosmed = user_api.add_user_sosmed(User, sosmed_type_spinner.getSelectedItem().toString(),
                    linkSosmed.getText().toString());
            save_sosmed.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(response.isSuccessful()){
                        if(response.body().get("status").getAsBoolean()){
                            Toast.makeText(AddingSosmedActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            loadingDialog.dismiss();
                            load_data();

                        }else{
                            Toast.makeText(AddingSosmedActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            loadingDialog.dismiss();
                        }
                    }else{
                        Toast.makeText(AddingSosmedActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                    }

                }
                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(AddingSosmedActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    loadingDialog.dismiss();
                }
            });
        }else{
            loadingDialog.dismiss();
        }
    }

    private void load_data(){
        loadingDialog.show(getSupportFragmentManager(), "load");
        Call<ArrayList<ModelSosmed>> load_sosmed = user_api.get_user_sosmed(User);
        load_sosmed.enqueue(new Callback<ArrayList<ModelSosmed>>() {
            @Override
            public void onResponse(Call<ArrayList<ModelSosmed>> call, Response<ArrayList<ModelSosmed>> response) {
                if(response.isSuccessful()){
                    sosmedUserArrayList = response.body();
                    rVsosmedAdapter.notifyItemRangeRemoved(0,0);
                    rVsosmedAdapter.setSosmedArrayList(sosmedUserArrayList);
                    rVsosmedAdapter.notifyDataSetChanged();
                    loadingDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ModelSosmed>> call, Throwable t) {
                loadingDialog.dismiss();
                Toast.makeText(AddingSosmedActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private  void  delete_sosmed(String id){
        loadingDialog.show(getSupportFragmentManager(), "load");
        Call<JsonObject> del_sosmed = user_api.del_user_sosmed(id);
        del_sosmed.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    if (response.body().get("status").getAsBoolean()){
                        Toast.makeText(AddingSosmedActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                    }else{
                        Toast.makeText(AddingSosmedActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                    }
                }
                load_data();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(AddingSosmedActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
            }
        });
    }

    private void update_sosmed(String id, String link){
        loadingDialog.show(getSupportFragmentManager(), "load");
        Call <JsonObject> update_user = user_api.update_user_sosmed(id, link);
        update_user.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    if(response.body().get("status").getAsBoolean()){
                        Toast.makeText(AddingSosmedActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                    }else{
                        Toast.makeText(AddingSosmedActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                    }
                }
                load_data();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(AddingSosmedActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
            }
        });
    }


    @Override
    public void onDeleteIconClick(String id) {
        delete_sosmed(id);
    }

    @Override
    public void onUpdateIconClick(String id, String link) {
        update_sosmed(id, link);
    }
}
