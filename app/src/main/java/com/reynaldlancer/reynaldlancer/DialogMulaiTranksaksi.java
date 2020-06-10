package com.reynaldlancer.reynaldlancer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.w3c.dom.Text;

public class DialogMulaiTranksaksi extends DialogFragment {

    View DialogLayout;
    String Judul, isi;
    View.OnClickListener next_event;
    int ImageID;

    public int getImageID() {
        return ImageID;
    }

    public void setImageID(int imageID) {
        ImageID = imageID;
    }


    public String getJudul() {
        return Judul;
    }

    public void setJudul(String judul) {
        Judul = judul;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public View.OnClickListener getNext_event() {
        return next_event;
    }

    public void setNext_event(View.OnClickListener next_event) {
        this.next_event = next_event;
    }

    @NonNull
    @Override

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        DialogLayout = inflater.inflate(R.layout.dialog_mulai_tranksaksi, null);
        dialog.setView(DialogLayout);
        return dialog.create();
    }

    @Override
    public void onStart() {
        TextView judul = DialogLayout.findViewById(R.id.judul_transasksi_dialog);
        TextView isi = DialogLayout.findViewById(R.id.isi_mulai_tranksakksi);
        Button btn_next = DialogLayout.findViewById(R.id.bnt_to_create_trankasaksi);
        ImageView logo = DialogLayout.findViewById(R.id.logo_mulai_tranksasksi);
        judul.setText(this.Judul);
        isi.setText(this.isi);
        btn_next.setOnClickListener(this.next_event);
        logo.setImageResource(this.ImageID);
        super.onStart();
    }




}
