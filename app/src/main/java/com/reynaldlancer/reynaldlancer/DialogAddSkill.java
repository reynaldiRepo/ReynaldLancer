package com.reynaldlancer.reynaldlancer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Callback;


public class DialogAddSkill extends DialogFragment implements onAddSkill {
    View DialogLayout;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        DialogLayout = inflater.inflate(R.layout.dialog_pilih_skill, null);
        dialog.setView(DialogLayout);
        setCancelable(false);
        return dialog.create();
    }


    ListView listViewKategori;
    RecyclerView RV_itemSkill;
    TextView State;
    Button back_state, cancel;
    ArrayList<ModelSkill> modelSkills;
    ArrayList<String> UsersSkill;
    RvSkillAdapter rvSkillAdapter;

    //interface for send to activity
    onAddSkillFromDialog addSkillFromDialog;


    public DialogAddSkill(ArrayList<String> usersSkill, onAddSkillFromDialog onAddSkillFromDialoginterface) {
        this.UsersSkill = usersSkill;
        addSkillFromDialog = onAddSkillFromDialoginterface;
    }

    @Override
    public void onStart() {
        super.onStart();

        cancel = DialogLayout.findViewById(R.id.close_dialog_skill);
        cancel.setOnClickListener(v -> {
            dismiss();
        });

        listViewKategori = DialogLayout.findViewById(R.id.skill_category);
        RV_itemSkill = DialogLayout.findViewById(R.id.skill_item_detail);
        RV_itemSkill.setVisibility(View.GONE);
        listViewKategori.setVisibility(View.VISIBLE);

        //giving adapter to kategori
        listViewKategori.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ArrayAdapter<CharSequence> kategori_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.jenis_keahlian,
                android.R.layout.simple_list_item_checked);
        listViewKategori.setAdapter(kategori_adapter);
        listViewKategori.setOnItemClickListener((parent, view, position, id) -> {
            load_skill_item(position);
        });


        //initiate modeluserskill
        modelSkills = new ArrayList<ModelSkill>();
//        giving adapter to item detail
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        RV_itemSkill.setLayoutManager(linearLayoutManager);
        rvSkillAdapter = new RvSkillAdapter(getActivity(), modelSkills, UsersSkill, this);
        RV_itemSkill.setAdapter(rvSkillAdapter);


//        for back_state
        back_state = DialogLayout.findViewById(R.id.back_state_choose_skill_btn);
        back_state.setOnClickListener(v -> {
            if (listViewKategori.getVisibility() == View.GONE) {
                RV_itemSkill.setVisibility(View.GONE);
                listViewKategori.setVisibility(View.VISIBLE);
            }
        });

    }

    private void load_skill_item(int position) {
        listViewKategori.setVisibility(View.GONE);
        RV_itemSkill.setVisibility(View.VISIBLE);
        switch (position) {
            case 0:
                String[] comp = getResources().getStringArray(R.array.comp_skill);
                update_arrayListskill(comp);
                break;
            case 1:
                String[] des = getResources().getStringArray(R.array.desain_skill);
                update_arrayListskill(des);
                break;
            case 2:
                String[] bus = getResources().getStringArray(R.array.bussines_skill);
                update_arrayListskill(bus);
                break;
        }
    }

    private void update_arrayListskill(String[] arrayString) {
        modelSkills.clear();
        rvSkillAdapter.notifyItemRangeChanged(0, 0);
        for (String sk : arrayString) {
            modelSkills.add(new ModelSkill(sk));
        }
        rvSkillAdapter.notifyDataSetChanged();
    }

    @Override
    public void DataFromRVToDialog(ArrayList<String> result) {
        UsersSkill = result;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        addSkillFromDialog.DataFromDialogToActivity(UsersSkill);
    }
}
