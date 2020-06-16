package com.reynaldlancer.reynaldlancer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class BuatPosterActivity extends AppCompatActivity {

    RecyclerView item_poster_rv;
    CheckBox poster_tempat_checkbox;
    LinearLayout layout_tempat_poster;
    TextView counter_desc;
    EditText deskripsi;
    Spinner kategori_poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_poster);

        //for kategori spinner
        kategori_poster = findViewById(R.id.kategori_poster);
        ArrayAdapter<CharSequence> adapterKategori = ArrayAdapter.createFromResource(this, R.array.kategori_poster_arr,
                android.R.layout.simple_spinner_dropdown_item);
        adapterKategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kategori_poster.setAdapter(adapterKategori);


        //for tempat_poster (Lokasi)
        poster_tempat_checkbox = findViewById(R.id.poster_lokasi_chekcbox);
        layout_tempat_poster = findViewById(R.id.tempat_poster_input_layout);
        poster_tempat_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    layout_tempat_poster.setVisibility(View.VISIBLE);
                } else {
                    layout_tempat_poster.setVisibility(View.GONE);
                }
            }
        });


        //for image item RV
        item_poster_rv = findViewById(R.id.item_poster_rv);
        LinearLayoutManager tugaslayoutManager4 = new LinearLayoutManager(
                BuatPosterActivity.this,
                LinearLayoutManager.VERTICAL,
                false);
        item_poster_rv.setLayoutManager(tugaslayoutManager4);
        item_poster_rv.setItemAnimator(new DefaultItemAnimator());

        final ArrayList<ItemposterModel> ArrayItemposter = new ArrayList<ItemposterModel>();
        ArrayItemposter.add(new ItemposterModel());
        final ItemPosterRVAdapter item_poster_adapter = new ItemPosterRVAdapter(ArrayItemposter, BuatPosterActivity.this);
        item_poster_rv.setAdapter(item_poster_adapter);
        //--// when add new item poster
        TextView tambahItem = findViewById(R.id.tambah_item_poster);
        tambahItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayItemposter.add(new ItemposterModel());
                item_poster_adapter.notifyDataSetChanged();
                item_poster_rv.post(new Runnable() {
                    @Override
                    public void run() {
                        item_poster_rv.smoothScrollToPosition(item_poster_adapter.getItemCount() - 1);
                    }
                });
            }
        });


        //deskripsi
        counter_desc = findViewById(R.id.input_deskripsi_poster_counter);
        deskripsi = findViewById(R.id.input_deskripsi_poster);
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

    }
}
