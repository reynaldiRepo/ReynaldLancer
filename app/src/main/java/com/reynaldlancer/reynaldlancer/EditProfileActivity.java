package com.reynaldlancer.reynaldlancer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {


    //initiate session data
    SessionController session;

    //user_id
    String User = "";
    //user model
    ModelUser modelUser;

    //Api user
    RestApiUSER user_api;

    //component profile
    ImageView photoProfile;
    EditText edit_nama, edit_no_telp, edit_alamat, edit_info;
    RadioGroup gender_rb_Group;
    RadioButton male_rb, female_rb;
    TextView email;

    //firebaseHelper
    FirebaseHelper firebaseHelper = new FirebaseHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //assing to layout
        edit_nama = findViewById(R.id.ET_profile_nama_edit);
        edit_alamat = findViewById(R.id.ET_profile_alamat_edit);
        edit_no_telp = findViewById(R.id.ET_profile_no_telephone_edit);
        edit_info = findViewById(R.id.input_deskripsi_profile);
        photoProfile = findViewById(R.id.image_profile_preview_edit);
        gender_rb_Group = findViewById(R.id.gender_edit_rb_group);
        male_rb = findViewById(R.id.rb_male);
        female_rb = findViewById(R.id.rb_male);
        email = findViewById(R.id.ET_profile_email_edit);



        //get_session
        session = new SessionController();
        User = session.getActiveUser(EditProfileActivity.this);

        //load user data
        LoadingDialog loading = new LoadingDialog();
        loading.show(getSupportFragmentManager(), "loading");
        user_api = RetrofitClient.getRetrofitInstance().create(RestApiUSER.class);
        Call<ModelUser> get_user = user_api.getUser(User);
        get_user.enqueue(new Callback<ModelUser>() {
            @Override
            public void onResponse(Call<ModelUser> call, Response<ModelUser> response) {
                if(response.isSuccessful()){
                    modelUser = response.body();
                    //adding to editText and form
                    firebaseHelper.load_iamge(firebaseHelper.getPhotoProfileDir(), modelUser.getPhoto_profile(),photoProfile );
                    email.setText(modelUser.get_id());
                    edit_nama.setText(modelUser.getNama());
                    edit_alamat.setText(modelUser.getAlamat());
                    edit_no_telp.setText(modelUser.getNo_telephone());
                    edit_info.setText(modelUser.getInfo_tambahan());
                    if (modelUser.getGender() == "L"){
                        male_rb.setChecked(true);
                    }else{
                        female_rb.setChecked(true);
                    }
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ModelUser> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }
}
