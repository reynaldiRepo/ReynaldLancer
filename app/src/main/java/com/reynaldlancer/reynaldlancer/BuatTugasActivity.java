package com.reynaldlancer.reynaldlancer;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuatTugasActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 71;
    Calendar myCalendar;
    TextView tugas_mulai, tugas_selesai;
    RadioGroup type_upah_tugas;
    LinearLayout lokasi_upah;
    Spinner kategori_input;
    TextView counter_desc;
    EditText deskripsi, judul, upah, personel;
    String User;
    RestApiUSER user_api;
    LoadingDialog loadingDialog;
    Button buat_tugas, tambah_gambar;
    ImageDeskripsiAdapter imageDeskripsiAdapter;

    ArrayList<ImageDeskripsiModel> imageDeskripsiModelArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_tugas);

        //setting instance calendar
        myCalendar = Calendar.getInstance();

        //date input for mulai selesai
        tugas_mulai = findViewById(R.id.mulai_tugas_input);
        tugas_selesai = findViewById(R.id.selesai_tugas_input);
        tugas_mulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(tugas_mulai);
            }
        });
        tugas_selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(tugas_selesai);
            }
        });


        //kategori
        kategori_input = findViewById(R.id.kategori_tugas);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.kategori_arr,
                android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kategori_input.setAdapter(adapter);

        //upah
        lokasi_upah = findViewById(R.id.tempat_input_upah_tugas);
        type_upah_tugas = findViewById(R.id.upah_tugas_radio_grup);
        type_upah_tugas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.buka_penawaran_tugas_radio_btn:
                        lokasi_upah.setVisibility(View.GONE);
                        break;
                    case R.id.upah_tugas_radio_btn:
                        lokasi_upah.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        //for upload image
        RecyclerView RVimagePrev = findViewById(R.id.tugas_img_des);
        LinearLayoutManager tugaslayoutManager = new LinearLayoutManager(BuatTugasActivity.this, LinearLayoutManager.HORIZONTAL, false);
        RVimagePrev.setLayoutManager(tugaslayoutManager);
        RVimagePrev.setItemAnimator(new DefaultItemAnimator());

        imageDeskripsiModelArrayList = new ArrayList<>();
        imageDeskripsiAdapter = new ImageDeskripsiAdapter(imageDeskripsiModelArrayList, BuatTugasActivity.this);
        RVimagePrev.setAdapter(imageDeskripsiAdapter);


        //deskripsi
        counter_desc = findViewById(R.id.input_deskripsi_tugas_counter);
        deskripsi = findViewById(R.id.input_deskripsi_tugas);
        deskripsi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                counter_desc.setText(Integer.toString(deskripsi.getText().length()) + "/100 Karakter");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //assigncomponent
        judul = findViewById(R.id.input_judul_tugas);
        upah = findViewById(R.id.input_upah_tugas);
        personel = findViewById(R.id.personel_tugas);
        buat_tugas = findViewById(R.id.btn_posting_tugas);
        tambah_gambar = findViewById(R.id.btn_image_tugas);

        User = new SessionController().getActiveUser(this);
        user_api = RetrofitClient.getRetrofitInstance().create(RestApiUSER.class);
        loadingDialog = new LoadingDialog();

        tambah_gambar.setOnClickListener(v -> {
            chooseImage();
        });
        buat_tugas.setOnClickListener(v -> {
            save_tugas();
        });

    }

    private void save_tugas() {
        loadingDialog.show(getSupportFragmentManager(), "load");
        EDValidation validation = new EDValidation();
        if (validation.required(judul) && validation.required(deskripsi) && validation.required(personel) && tugas_mulai.getText() != ""
                && tugas_selesai.getText() != "") {
            String namePhoto = "";
            FirebaseHelper firebaseHelper = new FirebaseHelper();
            int count = 0;
            for (ImageDeskripsiModel i : imageDeskripsiModelArrayList) {
                String ImageName = User + "Tugas" + UUID.randomUUID();
                if (count != imageDeskripsiModelArrayList.size() - 1) {
                    namePhoto += ImageName + ",";
                } else {
                    namePhoto += ImageName;
                    firebaseHelper.uploadImageNoLoading(i.getImage_uri(), "tugas", ImageName, BuatTugasActivity.this);
                }
                count += 1;
            }
            Call<JsonObject> save_tugas = user_api.buat_tugas(User,
                    kategori_input.getSelectedItem().toString(),
                    judul.getText().toString(),
                    deskripsi.getText().toString(),
                    namePhoto,
                    tugas_mulai.getText().toString(),
                    tugas_selesai.getText().toString(),
                    upah.getText().toString());

            save_tugas.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Toast.makeText(BuatTugasActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    loadingDialog.dismiss();
                    startActivity(new Intent(BuatTugasActivity.this, HomeActivity.class));
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    loadingDialog.dismiss();
                    Toast.makeText(BuatTugasActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(this, "Tolong Isi Data", Toast.LENGTH_SHORT).show();
            loadingDialog.dismiss();
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //upload image
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null
                && data.getData() != null) {
            Uri filepath = data.getData();
            imageDeskripsiModelArrayList.add(new ImageDeskripsiModel(filepath));
            imageDeskripsiAdapter.setImageDeskripsiModel(imageDeskripsiModelArrayList);
            imageDeskripsiAdapter.notifyDataSetChanged();
        }
    }


    private void showDateDialog(final TextView target) {
        new DatePickerDialog(BuatTugasActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String formatTanggal = "dd-MM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(formatTanggal);
                target.setText(sdf.format(myCalendar.getTime()));
            }
        },
                myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
