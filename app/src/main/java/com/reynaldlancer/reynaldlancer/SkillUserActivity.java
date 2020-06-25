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

public class SkillUserActivity extends AppCompatActivity implements  onDeleteUserSkill {


    //for back
    ImageView nav_back;

    //rest api USER
    RestApiUSER user_api = RetrofitClient.getRetrofitInstance().create(RestApiUSER.class);

    //User
    String User;

    //RV_LIST_SKILL
    RecyclerView RV_SKILL;
    LoadingDialog loading;

    //for skil model arraylist
    ArrayList<ModelUserSkill> modelUserSkills = new ArrayList<ModelUserSkill>() ;
    RvSkillUserAdapter rvSkillUserAdapter;

    //dialog for add skill;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_user);

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
        load_data();

        //load user data
        SessionController sessionController = new SessionController();
        User = sessionController.getActiveUser(SkillUserActivity.this);

    }

    private void load_data(){
        loading.show(getSupportFragmentManager(), "load");
        Call<ArrayList<ModelUserSkill>> get_skill = user_api.get_user_skill(User);
        get_skill.enqueue(new Callback<ArrayList<ModelUserSkill>>() {
            @Override
            public void onResponse(Call<ArrayList<ModelUserSkill>> call, Response<ArrayList<ModelUserSkill>> response) {
                if (response.isSuccessful()) {
                    modelUserSkills = response.body();
                    rvSkillUserAdapter.SkillModels.clear();
                    rvSkillUserAdapter.notifyItemRangeRemoved(0,0);
                    rvSkillUserAdapter.setSkillModels(modelUserSkills);
                    rvSkillUserAdapter.notifyDataSetChanged();
                    loading.dismiss();
                }else{
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

    private void delete_skill(String _id, int pos){
        loading.show(getSupportFragmentManager(), "load");
        Call<JsonObject> del_skill = user_api.del_user_skill(_id);
        del_skill.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body().get("status").getAsBoolean()){
                    Toast.makeText(SkillUserActivity.this, "Delete Success", Toast.LENGTH_SHORT).show();
                    modelUserSkills.remove(pos);
                    rvSkillUserAdapter.setSkillModels(modelUserSkills);
                    rvSkillUserAdapter.notifyItemRemoved(pos);
                }else{
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
}
