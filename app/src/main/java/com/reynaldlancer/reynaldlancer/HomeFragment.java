package com.reynaldlancer.reynaldlancer;


import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = "Tugas dummy";
    private NavigationView side_nav;
    private Button button_nav;
    private DrawerLayout drawerLayout;
    private ViewFlipper promo_layout;
    private RecyclerView RV_tugas;
    private RecyclerView RV_misi;
    private RecyclerView RV_academy;
    private Button tambah_saldo, tarik_saldo;
    private LinearLayout posting_tugas;
    private LinearLayout posting_poster;

    private String User;

    //    #dialog_fragment
    private DialogMulaiTranksaksi dialog;

    //initiate helper for format string
    StringFormater formater = new StringFormater();

    //initiate rstApiUser
    RestApiUSER user_api = RetrofitClient.getRetrofitInstance().create(RestApiUSER.class);
    //initiate model for data
    ModelUser modelUser;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        side_nav = v.findViewById(R.id.side_nav);
        button_nav = v.findViewById(R.id.sidebar_button);
        drawerLayout = v.findViewById(R.id.drawer_container);
        promo_layout = v.findViewById(R.id.promo);
        RV_tugas = v.findViewById(R.id.tugas_rv);
        RV_misi = v.findViewById(R.id.misi_rv);
        RecyclerView RV_poster = v.findViewById(R.id.poster_rv);
        RV_academy = v.findViewById(R.id.heroac_rv);
        tambah_saldo = v.findViewById(R.id.tambah_saldo);
        tarik_saldo = v.findViewById(R.id.tarik_saldo);
        posting_tugas = v.findViewById(R.id.posting_tugas);
        LinearLayout posting_misi = v.findViewById(R.id.posting_misi);
        posting_poster = v.findViewById(R.id.posting_poster);


        //loading data
        LoadingDialog loading = new LoadingDialog();//for loading dialog
        loading.show(getChildFragmentManager(), "Loading");
        //get user active
        SessionController session = new SessionController();
        User = session.getActiveUser(getActivity());

        //load user data
        Call<ModelUser> getUser = user_api.getUser(
                User
        );
        getUser.enqueue(new Callback<ModelUser>() {
            @Override
            public void onResponse(Call<ModelUser> call, Response<ModelUser> response) {
                if(response.isSuccessful()){
                    Log.d("Get Data User", "sukses get data user"+response.body().toString());
                    modelUser = response.body();

                    //put saldo from model to view
                    TextView jumlah_saldo = v.findViewById(R.id.saldo_text);
                    jumlah_saldo.setText(formater.toCurrency(modelUser.getSaldo().toString()));

                    //dismiss loading dialog
                    loading.dismiss();
                }
            }
            @Override
            public void onFailure(Call<ModelUser> call, Throwable t) {
                Log.e("Fail Get User Home", t.getMessage(),t );
            }
        });










        //for images promo
        int image[] = {R.mipmap.slide1, R.mipmap.slide2, R.mipmap.slide3};
        for (int i = 0; i < image.length; i++) {
            runpromo(image[i]);
        }

        //for drawer
        button_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        //for logout
        side_nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String id = Integer.toString(item.getItemId());
                switch (item.getItemId()) {
                    case R.id.logout:
                        SQLiteDatabase db = new SqlHelper(getActivity()).getWritableDatabase();
                        db.execSQL("delete from login_data");
                        Intent i = new Intent(getActivity(), LoginActivity.class);
                        startActivity(i);
                }
                return false;
            }
        });


        //for tugas
        TugasDummy tugasDummy = new TugasDummy();
        LinearLayoutManager tugaslayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RV_tugas.setLayoutManager(tugaslayoutManager);
        RV_tugas.setItemAnimator(new DefaultItemAnimator());
        TugasRvApapter tugasRvApapter = new TugasRvApapter(tugasDummy.dummy(), getActivity());
        RV_tugas.setAdapter(tugasRvApapter);

        //for misi
        LinearLayoutManager tugaslayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RV_misi.setLayoutManager(tugaslayoutManager2);
        RV_misi.setItemAnimator(new DefaultItemAnimator());
        MisiRVAdapter misiRVAdapter = new MisiRVAdapter(tugasDummy.dummy(), getActivity());
        RV_misi.setAdapter(misiRVAdapter);


        //for poster
        LinearLayoutManager tugaslayoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RV_poster.setLayoutManager(tugaslayoutManager3);
        RV_poster.setItemAnimator(new DefaultItemAnimator());
        PosterRvAdapter posterRvAdapter = new PosterRvAdapter(tugasDummy.dummy(), getActivity());
        RV_poster.setAdapter(posterRvAdapter);

        //for hero academy
        HeroAcademyDummy dummyAc = new HeroAcademyDummy();
        LinearLayoutManager tugaslayoutManager4 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RV_academy.setLayoutManager(tugaslayoutManager4);
        RV_academy.setItemAnimator(new DefaultItemAnimator());
        HeroAcademyRvAdapter heroAcademyRvAdapter = new HeroAcademyRvAdapter(dummyAc.dummy(), getActivity());
        RV_academy.setAdapter(heroAcademyRvAdapter);


        //logic for feature
        //add saldo
        tambah_saldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent saldo = new Intent(getActivity(), SaldoActivity.class);
                startActivity(saldo);
            }
        });
        tarik_saldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent saldo = new Intent(getActivity(), TarikActivity.class);
                startActivity(saldo);
            }
        });

//        dialog before posting
        dialog = new DialogMulaiTranksaksi();
        posting_tugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setJudul("BUAT TUGAS");
                dialog.setIsi(getString(R.string.tugas_pendahuluan));
                dialog.setNext_event(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), BuatTugasActivity.class);
                        startActivity(intent);
                    }
                });
                dialog.setImageID(R.mipmap.tugas);
                dialog.show(getChildFragmentManager(), "Posting");
            }
        });

        posting_poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setJudul("BUAT POSTER");
                dialog.setIsi(getString(R.string.poster_pendahuluan));
                dialog.setNext_event(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), BuatPosterActivity.class);
                        startActivity(intent);
                    }
                });
                dialog.setImageID(R.mipmap.poster);
                dialog.show(getChildFragmentManager(), "Posting");
            }
        });

        posting_misi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setJudul("BUAT MISI");
                dialog.setIsi(getString(R.string.misi_pendahuluan));
                dialog.setNext_event(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), BuatMisiActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
                dialog.setImageID(R.mipmap.misi);
                dialog.show(getChildFragmentManager(), "Posting");
            }
        });

        return v;

    }

    private void runpromo(int Image) {
        ImageView imageView = new ImageView(getActivity());
        imageView.setBackgroundResource(Image);

        promo_layout.addView(imageView);
        promo_layout.setFlipInterval(4000);
        promo_layout.setAutoStart(true);

        promo_layout.setInAnimation(getActivity(), android.R.anim.slide_in_left);
        promo_layout.setOutAnimation(getActivity(), android.R.anim.slide_out_right);

    }

}
