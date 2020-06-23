package com.reynaldlancer.reynaldlancer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;

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
    Button upload_image_btn;
    TextView domisili;

    //firebaseHelper
    FirebaseHelper firebaseHelper = new FirebaseHelper();

    //for image upload
    private final int PICK_IMAGE_REQUEST = 71;

    //loading dialog
    LoadingDialog loading;


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
        domisili = findViewById(R.id.ET_profile_domisili_edit);


        //get_session
        session = new SessionController();
        User = session.getActiveUser(EditProfileActivity.this);

        //load user data
        loading = new LoadingDialog();
        loading.show(getSupportFragmentManager(), "loading");
        user_api = RetrofitClient.getRetrofitInstance().create(RestApiUSER.class);
        Call<ModelUser> get_user = user_api.getUser(User);
        get_user.enqueue(new Callback<ModelUser>() {
            @Override
            public void onResponse(Call<ModelUser> call, Response<ModelUser> response) {
                if (response.isSuccessful()) {
                    modelUser = response.body();
                    //adding to editText and form
                    loadData(modelUser);
                    loading.dismiss();
                }
            }
            @Override
            public void onFailure(Call<ModelUser> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        //for image_upload
        upload_image_btn = findViewById(R.id.uploade_image_profile_btn);
        upload_image_btn.setOnClickListener(v -> {
            chooseImage();
        });

        //for domisili
        domisili.setOnClickListener(v->{
            DialogChooseDomisili dialogChooseDomisili = new DialogChooseDomisili(domisili);
            dialogChooseDomisili.show(getSupportFragmentManager(), "domisili");
        });


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
            Uri filepath = data.getData();
            //place on image view
            Glide.with(this).load(filepath).centerCrop().into(photoProfile);

            //upload to database
            loading.show(getSupportFragmentManager(), "loading");
            user_api.update_photo_profire(User, User + "image_profile").enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    //upload to firebase
                    firebaseHelper.uploadImage(filepath, firebaseHelper.getPhotoProfileDir(),
                            User + "image_profile", EditProfileActivity.this, getSupportFragmentManager());
                    loading.dismiss();
                    Toast.makeText(EditProfileActivity.this, "Success Update", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(EditProfileActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
            });
        } else {
            Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadData(ModelUser modelUser){
        firebaseHelper.load_iamge(EditProfileActivity.this, firebaseHelper.getPhotoProfileDir(), modelUser.getPhoto_profile(), photoProfile);
        email.setText(modelUser.get_id());
        edit_nama.setText(modelUser.getNama());
        edit_alamat.setText(modelUser.getAlamat());
        edit_no_telp.setText(modelUser.getNo_telephone());
        edit_info.setText(modelUser.getInfo_tambahan());
        if (modelUser.getGender() == "L") {
            male_rb.setChecked(true);
        } else {
            female_rb.setChecked(true);
        }
    }

}
