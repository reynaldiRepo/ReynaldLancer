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

public class BuatMisiActivity extends AppCompatActivity {

    RecyclerView item_misi_rv;
    CheckBox misi_tempat_checkbox;
    LinearLayout layout_tempat_misi;
    TextView counter_desc;
    EditText deskripsi;
    Spinner kategori_misi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_misi);

        //for kategori spinner
        kategori_misi = findViewById(R.id.kategori_misi);
        ArrayAdapter<CharSequence> adapterKategori = ArrayAdapter.createFromResource(this, R.array.kategori_misi_arr,
                android.R.layout.simple_spinner_dropdown_item);
        adapterKategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kategori_misi.setAdapter(adapterKategori);




        //for tempat_misi
        misi_tempat_checkbox = findViewById(R.id.misi_lokasi_chekcbox);
        layout_tempat_misi = findViewById(R.id.tempat_misi_input_layout);
        misi_tempat_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout_tempat_misi.setVisibility(View.VISIBLE);
                }else{
                    layout_tempat_misi.setVisibility(View.GONE);
                }
            }
        });


        //for image item RV
        item_misi_rv = findViewById(R.id.item_misi_rv);
        LinearLayoutManager tugaslayoutManager4 = new LinearLayoutManager(
                BuatMisiActivity.this,
                LinearLayoutManager.VERTICAL,
                false);
        item_misi_rv.setLayoutManager(tugaslayoutManager4);
        item_misi_rv.setItemAnimator(new DefaultItemAnimator());

        final ArrayList<ItemMisiModel> ArrayItemMisi = new ArrayList<ItemMisiModel>();
        ArrayItemMisi.add(new ItemMisiModel());
        final ItemMisiRVAdapter item_misi_adapter = new ItemMisiRVAdapter(ArrayItemMisi, BuatMisiActivity.this);
        item_misi_rv.setAdapter(item_misi_adapter);
        //--// when add new item misi
        TextView tambahItem = findViewById(R.id.tambah_item_misi);
        tambahItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayItemMisi.add(new ItemMisiModel());
                item_misi_adapter.notifyDataSetChanged();
                item_misi_rv.post(new Runnable() {
                    @Override
                    public void run() {
                        item_misi_rv.smoothScrollToPosition(item_misi_adapter.getItemCount() - 1);
                    }
                });
            }
        });


        //deskripsi
        counter_desc = findViewById(R.id.input_deskripsi_misi_counter);
        deskripsi = findViewById(R.id.input_deskripsi_misi);
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
}
