package com.reynaldlancer.reynaldlancer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkillUserActivity extends AppCompatActivity implements onDeleteUserSkill, onAddSkillFromDialog{


    //for back
    ImageView nav_back;

    //rest api USER
    RestApiUSER user_api;

    //User
    String User;

    //RV_LIST_SKILL
    RecyclerView RV_SKILL;
    LoadingDialog loading;

    //for skil model arraylist
    ArrayList<ModelUserSkill> modelUserSkills = new ArrayList<ModelUserSkill>();
    RvSkillUserAdapter rvSkillUserAdapter;

    //dialog for add skill;
    DialogAddSkill dialogAddSkill;

    //button to add skill
    Button add_skilButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_user);

        //load user data
        SessionController sessionController = new SessionController();
        User = sessionController.getActiveUser(SkillUserActivity.this);

        //for loading
        loading = new LoadingDialog();

        //for back
        nav_back = findViewById(R.id.skill_nav_back);
        nav_back.setOnClickListener(v -> {
            onBackPressed();
        });

        //loading list skill
        RV_SKILL = findViewById(R.id.RV_Daftar_Skill);
        LinearLayoutManager tugaslayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RV_SKILL.setLayoutManager(tugaslayoutManager);
        RV_SKILL.setItemAnimator(new DefaultItemAnimator());
        rvSkillUserAdapter = new RvSkillUserAdapter(modelUserSkills, SkillUserActivity.this, this);
        RV_SKILL.setAdapter(rvSkillUserAdapter);

        user_api = RetrofitClient.getRetrofitInstance().create(RestApiUSER.class);
        load_data();


        //add skill
        add_skilButton = findViewById(R.id.add_skill_btn);
        add_skilButton.setOnClickListener(v -> {
            dialogAddSkill.show(getSupportFragmentManager(), "add_skill");
        });


    }

    private void load_data() {
        loading.show(getSupportFragmentManager(), "load");
        Call<ArrayList<ModelUserSkill>> get_skill = user_api.get_user_skill(User);
        get_skill.enqueue(new Callback<ArrayList<ModelUserSkill>>() {
            @Override
            public void onResponse(Call<ArrayList<ModelUserSkill>> call, Response<ArrayList<ModelUserSkill>> response) {
                if (response.isSuccessful()) {
                    modelUserSkills.clear();
                    modelUserSkills = response.body();
                    rvSkillUserAdapter.notifyItemRangeRemoved(0, 0);
                    rvSkillUserAdapter.setSkillModels(modelUserSkills);
                    rvSkillUserAdapter.notifyDataSetChanged();
                    loading.dismiss();
                    dialogAddSkill = new DialogAddSkill(ConvertArrayListSkillToArrayListString(response.body()), SkillUserActivity.this);
                } else {
                    loading.dismiss();
                    Toast.makeText(SkillUserActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ModelUserSkill>> call, Throwable t) {
                Toast.makeText(SkillUserActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        });
    }

    private void delete_skill(String _id, int pos) {
        loading.show(getSupportFragmentManager(), "load");
        Call<JsonObject> del_skill = user_api.del_user_skill(_id);
        del_skill.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body().get("status").getAsBoolean()) {
                    Toast.makeText(SkillUserActivity.this, "Delete Success", Toast.LENGTH_SHORT).show();
                    modelUserSkills.remove(pos);
                    rvSkillUserAdapter.setSkillModels(modelUserSkills);
                    rvSkillUserAdapter.notifyItemRemoved(pos);
                } else {
                    Toast.makeText(SkillUserActivity.this, "Delete Failed", Toast.LENGTH_SHORT).show();
                }
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(SkillUserActivity.this, "delete failed", Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        });

    }

    @Override
    public void onDeleteBtnClick(String _id, int pos) {
        delete_skill(_id, pos);
    }

    private ArrayList<String> ConvertArrayListSkillToArrayListString(ArrayList<ModelUserSkill> modelUserSkills1) {
        ArrayList<String> res = new ArrayList<String>();
        for (ModelUserSkill m : modelUserSkills1) {
            res.add(m.getSkill());
        }
        return res;
    }

    @Override
    public void DataFromDialogToActivity(ArrayList<String> result) {
        addSkill(result);
    }

    private void addSkill(ArrayList<String> data){
        loading.show(getSupportFragmentManager(), "load");
        String dataStr = "";
        for (int i = 0 ; i < data.size(); i++ ){
            if (i != data.size()-1) {
                dataStr += data.get(i) + ",";
            }else{
                dataStr += data.get(i);
            }
        }

        Call <JsonObject> add_user = user_api.add_user_skill(dataStr, User);
        add_user.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    loading.dismiss();
                    Toast.makeText(SkillUserActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    load_data();
                }else{
                    loading.dismiss();
                    Toast.makeText(SkillUserActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(SkillUserActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
