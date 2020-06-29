package com.reynaldlancer.reynaldlancer;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */

public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    //profile komponen
    TextView nama, saldo;
    ImageView photo_profile;


    //button for edit
    Button edit_profile_btn;
    ImageView edit_skill, edit_pendidikan, edit_sosmed;

    //user id
    String User;
    //Api User;
    RestApiUSER user_api = RetrofitClient.getRetrofitInstance().create(RestApiUSER.class);

    //user model
    ModelUser modelUser;

    //firebase referance
    FirebaseHelper firebaseHelper = new FirebaseHelper();

    //Recyclview;
    RecyclerView skill_rv, sosmed_rv, pendidikan_rv;

    //recycler adapter
    RvskillProfileAdapter rvskillProfileAdapter;
    RvsosmedProfileAdapter rvsosmedProfileAdapter;
    RvpendidikanProfileAdapter rvpendidikanProfileAdapter;

    //model for skill, sosmed, pendidkan
    ArrayList<ModelUserSkill> modelUserSkillArrayList = new ArrayList<>();
    ArrayList<ModelSosmed> modelSosmedArrayList = new ArrayList<>();
    ArrayList<ModelPendidikanUser> modelPendidikanUserArrayList = new ArrayList<>();


    //textview no data
    TextView noskill, nososmed, nopendidikan;

    //int loading state;
    int loading_state = 0;

    //for profile progress
    ProgressBar progres;
    TextView status_progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        //initiate profile compomemt to layout
        nama = v.findViewById(R.id.nama_profile);
        photo_profile = v.findViewById(R.id.profile_image_on_banner);
        saldo = v.findViewById(R.id.saldo_profile);
        edit_profile_btn = v.findViewById(R.id.edit_profile_btn);
        edit_profile_btn.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), EditProfileActivity.class));
        });
        progres = v.findViewById(R.id.progressBar_status_profile);
        status_progress = v.findViewById(R.id.status_progress);

        //for button to edit
        edit_skill = v.findViewById(R.id.edit_skill);
        edit_pendidikan = v.findViewById(R.id.edit_pendidikan);
        edit_sosmed = v.findViewById(R.id.edit_sosmed);

        //assing to edit activity
        edit_skill.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), SkillUserActivity.class));
        });
        //adding to sosmed activity
        edit_sosmed.setOnClickListener(v1 -> {
            startActivity(new Intent(getActivity(), AddingSosmedActivity.class));
        });
        //adiing to pendidikan activity
        edit_pendidikan.setOnClickListener(v1 -> {
            startActivity(new Intent(getActivity(), PendidikanUserActivity.class));
        });

        //get active user
        SessionController session = new SessionController();
        User = session.getActiveUser(getActivity());

        //for icon map
        //data for sosmed type & icon
        HashMap<String, MaterialDrawableBuilder.IconValue> iconTypeSosmedHas = new HashMap<>();
        String[] sosmed_type = new String[getResources().getStringArray(R.array.sosmed_type).length];
        MaterialDrawableBuilder.IconValue[] iconArr = {MaterialDrawableBuilder.IconValue.INSTAGRAM,
                MaterialDrawableBuilder.IconValue.FACEBOOK, MaterialDrawableBuilder.IconValue.LINKEDIN, MaterialDrawableBuilder.IconValue.TWITTER,
                MaterialDrawableBuilder.IconValue.WEB};
        int count = 0;
        for (String s : getResources().getStringArray(R.array.sosmed_type)) {
            sosmed_type[count] = s;
            iconTypeSosmedHas.put(s, iconArr[count]);
            count++;
        }

        //assign rv
        skill_rv = v.findViewById(R.id.skill_profile_RV);
        sosmed_rv = v.findViewById(R.id.sosmed_profile_RV);
        pendidikan_rv = v.findViewById(R.id.pendidikan_profile_RV);

        //linear Layout Manager
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        skill_rv.setLayoutManager(linearLayoutManager1);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        sosmed_rv.setLayoutManager(linearLayoutManager2);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        pendidikan_rv.setLayoutManager(linearLayoutManager3);

        rvskillProfileAdapter = new RvskillProfileAdapter(getActivity(), modelUserSkillArrayList);
        rvsosmedProfileAdapter = new RvsosmedProfileAdapter(getActivity(), modelSosmedArrayList, iconTypeSosmedHas);
        rvpendidikanProfileAdapter = new RvpendidikanProfileAdapter(getActivity(), modelPendidikanUserArrayList);

        skill_rv.setAdapter(rvskillProfileAdapter);
        sosmed_rv.setAdapter(rvsosmedProfileAdapter);
        pendidikan_rv.setAdapter(rvpendidikanProfileAdapter);


        //Load user data
        loadData();

        //for textview
        noskill = v.findViewById(R.id.msg_tidak_ada_skill);
        nososmed = v.findViewById(R.id.msg_tidak_ada_sosmed);
        nopendidikan = v.findViewById(R.id.msg_tidak_ada_pendidikan);



        return v;
    }

    private void loadData() {
        LoadingDialog loadingDialog = new LoadingDialog();
        loadingDialog.show(getChildFragmentManager(), "load");
        Call<ModelUser> get_user = user_api.getUser(User);
        get_user.enqueue(new Callback<ModelUser>() {
            @Override
            public void onResponse(Call<ModelUser> call, Response<ModelUser> response) {
                if (response.isSuccessful()) {
                    modelUser = response.body();
                    firebaseHelper.load_iamge(getActivity(), firebaseHelper.getPhotoProfileDir(), modelUser.getPhoto_profile(), photo_profile);
                    nama.setText(modelUser.getNama().toUpperCase());
                    saldo.setText("Saldo : " + new StringFormater().toCurrency(modelUser.getSaldo().toString()));
                    loadSkill();loadSosmed();loadPendidikan();
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    loadingDialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), "Something Wrong", Toast.LENGTH_SHORT).show();
                    loadingDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ModelUser> call, Throwable t) {
                loadingDialog.dismiss();
                Log.e("Error", t.getMessage(), t);
            }
        });
    }

    private void loadSkill() {
        Call<ArrayList<ModelUserSkill>> get_skill = user_api.get_user_skill(User);
        get_skill.enqueue(new Callback<ArrayList<ModelUserSkill>>() {
            @Override
            public void onResponse(Call<ArrayList<ModelUserSkill>> call, Response<ArrayList<ModelUserSkill>> response) {
                if (response.isSuccessful()) {
                    modelUserSkillArrayList = response.body();
                    rvskillProfileAdapter.notifyItemRangeChanged(0, 0);
                    rvskillProfileAdapter.setModelUserSkills(response.body());
                    rvskillProfileAdapter.notifyDataSetChanged();
                    if(modelUserSkillArrayList.size() !=0 ){
                        noskill.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(getActivity(), "Failed Load Skill", Toast.LENGTH_SHORT).show();
                }
                check_all();
            }

            @Override
            public void onFailure(Call<ArrayList<ModelUserSkill>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed Load Skill", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadSosmed() {
        Call<ArrayList<ModelSosmed>> get_sosemd = user_api.get_user_sosmed(User);
        get_sosemd.enqueue(new Callback<ArrayList<ModelSosmed>>() {
            @Override
            public void onResponse(Call<ArrayList<ModelSosmed>> call, Response<ArrayList<ModelSosmed>> response) {
                if (response.isSuccessful()) {
                    modelSosmedArrayList = response.body();
                    rvsosmedProfileAdapter.notifyItemRangeRemoved(0, 0);
                    rvsosmedProfileAdapter.setModelSosmeds(modelSosmedArrayList);
                    rvsosmedProfileAdapter.notifyDataSetChanged();
                    if(modelSosmedArrayList.size() != 0){
                        nososmed.setVisibility(View.GONE);
                    }
                    check_all();
                } else {
                    Toast.makeText(getActivity(), "failed get sosmed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ModelSosmed>> call, Throwable t) {
                Toast.makeText(getActivity(), "failed get sosmed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadPendidikan() {
        Call<ArrayList<ModelPendidikanUser>> get_pendidikan = user_api.get_pendidikan(User);
        get_pendidikan.enqueue(new Callback<ArrayList<ModelPendidikanUser>>() {
            @Override
            public void onResponse(Call<ArrayList<ModelPendidikanUser>> call, Response<ArrayList<ModelPendidikanUser>> response) {
                if(response.isSuccessful()){
                    modelPendidikanUserArrayList = response.body();
                    rvpendidikanProfileAdapter.notifyItemRangeRemoved(0,0);
                    rvpendidikanProfileAdapter.setModelPendidikanUsers(modelPendidikanUserArrayList);
                    rvpendidikanProfileAdapter.notifyDataSetChanged();
                    if(modelPendidikanUserArrayList.size() != 0){
                        nopendidikan.setVisibility(View.GONE);
                    }
                    check_all();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ModelPendidikanUser>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void check_all(){
        loading_state += 1;
        if(loading_state == 2){
            update_progres();
            loading_state = 0;
        }
    }

    private void update_progres() {
        Integer progressCond = 25;
        if(modelUserSkillArrayList.size()!= 0){
            progressCond += 25;
        }
        if(modelPendidikanUserArrayList.size()!= 0){
            progressCond += 25;
        }
        if(modelSosmedArrayList.size()!=0){
            progressCond += 25;
        }

        String levelProfile = "";
        if(progressCond == 25){
            levelProfile = "Cupu";
        }else if(progressCond == 50){
            levelProfile = "Kurang";
        }else if(progressCond == 75){
            levelProfile = "Gokil";
        }else{
            levelProfile = "Dewa";
        }

        progres.setProgress(progressCond);
        status_progress.setText(levelProfile);

    }


    @Override
    public void onResume() {
        super.onResume();
        modelSosmedArrayList.clear();
        modelPendidikanUserArrayList.clear();
        modelUserSkillArrayList.clear();
        loadData();
    }
}
