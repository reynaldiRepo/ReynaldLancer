package com.reynaldlancer.reynaldlancer;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

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
    Button edit_profile_btn;


    //user id
    String User;
    //Api User;
    RestApiUSER user_api = RetrofitClient.getRetrofitInstance().create(RestApiUSER.class);

    //user model
    ModelUser modelUser;

    //Inisiasi Lading Dialog
    LoadingDialog loading = new LoadingDialog();




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
        edit_profile_btn.setOnClickListener(view->{startActivity(new Intent(getActivity(), EditProfileActivity.class));});

        //instatiate firebase referance
        //firebase referance
        FirebaseHelper firebaseHelper = new FirebaseHelper();

        //get active user
        SessionController session = new SessionController();
        User = session.getActiveUser(getActivity());

        //Load user data
        loading.show(getChildFragmentManager(), "loading" );
        Call<ModelUser> get_user = user_api.getUser(User);
        get_user.enqueue(new Callback<ModelUser>() {
            @Override
            public void onResponse(Call<ModelUser> call, Response<ModelUser> response) {
                if (response.isSuccessful()){
                    modelUser = response.body();

                    firebaseHelper.load_iamge("photo_profile", modelUser.getPhoto_profile(), photo_profile);
                    nama.setText(modelUser.getNama().toUpperCase());
                    saldo.setText("Saldo : "+new StringFormater().toCurrency(modelUser.getSaldo().toString()));
                    loading.dismiss();
                }else{
                    Toast.makeText(getActivity(), "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ModelUser> call, Throwable t) {
                Log.e("Error", t.getMessage(),t );
            }
        });


        return v;
    }

}
