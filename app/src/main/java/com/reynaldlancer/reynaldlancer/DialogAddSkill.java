package com.reynaldlancer.reynaldlancer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;



public class DialogAddSkill extends DialogFragment {
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
}
