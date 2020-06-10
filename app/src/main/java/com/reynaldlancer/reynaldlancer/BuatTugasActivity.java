package com.reynaldlancer.reynaldlancer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BuatTugasActivity extends AppCompatActivity {

    Calendar myCalendar;
    TextView tugas_mulai, tugas_selesai;
    RadioGroup type_tempat_tugas, type_upah_tugas;
    LinearLayout lokasi_tugas, lokasi_upah;
    Spinner kategori_input;
    TextView counter_desc;
    EditText deskripsi;


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

        //radio button for lokasi tugas
        lokasi_tugas = findViewById(R.id.tempat_input_layout);
        type_tempat_tugas = findViewById(R.id.lokasi_tugas_radio_grup);
        type_tempat_tugas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tugas_via_online_radio_btn:
                        lokasi_tugas.setVisibility(View.GONE);
                        break;
                    case R.id.tugas_alamat_radio_btn:
                        lokasi_tugas.setVisibility(View.VISIBLE);
                        break;
                }
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

        //for upload --- only dummy\
        RecyclerView RVimagePrev = findViewById(R.id.tugas_img_des);
        LinearLayoutManager tugaslayoutManager = new LinearLayoutManager(BuatTugasActivity.this, LinearLayoutManager.HORIZONTAL, false);
        RVimagePrev.setLayoutManager(tugaslayoutManager);
        RVimagePrev.setItemAnimator(new DefaultItemAnimator());

        ArrayList<ImageDeskripsiModel> image_dummy = new ArrayList<ImageDeskripsiModel>();
        image_dummy.add(new ImageDeskripsiModel(R.drawable.upload_img));
        ImageDeskripsiAdapter imageDeskripsiAdapter = new ImageDeskripsiAdapter(image_dummy, BuatTugasActivity.this);
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
                counter_desc.setText(Integer.toString(deskripsi.getText().length())+"/100 Karakter");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


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
