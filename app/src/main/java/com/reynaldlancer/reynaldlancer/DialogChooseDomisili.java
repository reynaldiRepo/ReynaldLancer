package com.reynaldlancer.reynaldlancer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.annotation.Target;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogChooseDomisili extends DialogFragment implements OnClickDomisiliInterface {
    View DialogLayout;
    RecyclerView domisili;
    ArrayList<ModelDomisili> modelDomisilis = new ArrayList<ModelDomisili>();

    //RvAdapter
    RVadapterDomisili rVadapterDomisili;

    //location api
    RestApiLOCATION restApiLOCATION = RetrofitClient.getRetrofitInstance().create(RestApiLOCATION.class);

    TextView target;

    public DialogChooseDomisili(TextView target) {
        this.target = target;
    }

    //state
    private int State = 0;
    private String res = "";
    private TextView stateTV;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        DialogLayout = inflater.inflate(R.layout.dialog_pilih_domisili, null);
        dialog.setView(DialogLayout);
        setCancelable(false);
        return dialog.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        domisili = DialogLayout.findViewById(R.id.RV_choose_domisili);
        //for domisili
        LinearLayoutManager tugaslayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        domisili.setLayoutManager(tugaslayoutManager1);
        domisili.setItemAnimator(new DefaultItemAnimator());
        rVadapterDomisili = new RVadapterDomisili(modelDomisilis, getActivity(), this, 0);
        domisili.setAdapter(rVadapterDomisili);

        //cancel button
        Button CancelBtn = DialogLayout.findViewById(R.id.btn_cancel_pilih_domisili);
        CancelBtn.setOnClickListener(v -> {
            dismiss();
        });

        //state info texview
        stateTV = DialogLayout.findViewById(R.id.state_pilih_domisili);

        get_prov();
    }

    private void get_prov() {
        LoadingDialog loadingDialog = new LoadingDialog();
        loadingDialog.show(getChildFragmentManager(), "loading");
        Call<ArrayList<ModelProv>> get_provinsi = restApiLOCATION.getProv();
        get_provinsi.enqueue(new Callback<ArrayList<ModelProv>>() {
            @Override
            public void onResponse(Call<ArrayList<ModelProv>> call, Response<ArrayList<ModelProv>> response) {
                modelDomisilis.removeAll(new ArrayList<ModelDomisili>());
                for (ModelProv m : response.body()) {
                    modelDomisilis.add(new ModelDomisili(m.getId(), m.getName()));
                }
                rVadapterDomisili.setDomisiliModel(modelDomisilis);
                rVadapterDomisili.notifyDataSetChanged();
                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<ModelProv>> call, Throwable t) {
                Toast.makeText(getActivity(), "Cant get Data", Toast.LENGTH_SHORT).show();
                res = "";
                dismiss();
                loadingDialog.dismiss();
            }
        });
    }


    private void getKab(String id) {
        LoadingDialog loadingDialog = new LoadingDialog();
        loadingDialog.show(getChildFragmentManager(), "loading");
        Call<ArrayList<ModelKab>> get_kab = restApiLOCATION.getKab(id);
        get_kab.enqueue(new Callback<ArrayList<ModelKab>>() {
            @Override
            public void onResponse(Call<ArrayList<ModelKab>> call, Response<ArrayList<ModelKab>> response) {
                modelDomisilis.clear();
                rVadapterDomisili.notifyItemRangeRemoved(0, 0);
                for (ModelKab m : response.body()) {
                    modelDomisilis.add(new ModelDomisili(m.getId(), m.getName()));
                }
                rVadapterDomisili.setDomisiliModel(modelDomisilis);
                rVadapterDomisili.notifyDataSetChanged();
                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<ModelKab>> call, Throwable t) {
                Toast.makeText(getActivity(), "Cant get Data", Toast.LENGTH_SHORT).show();
                res = "";
                loadingDialog.dismiss();
                dismiss();
            }
        });
    }

    private void getKec(String id) {
        LoadingDialog loadingDialog = new LoadingDialog();
        loadingDialog.show(getChildFragmentManager(), "loading");
        Call<ArrayList<ModelKec>> get_kec = restApiLOCATION.getKec(id);
        get_kec.enqueue(new Callback<ArrayList<ModelKec>>() {
            @Override
            public void onResponse(Call<ArrayList<ModelKec>> call, Response<ArrayList<ModelKec>> response) {
                modelDomisilis.clear();
                rVadapterDomisili.notifyItemRangeRemoved(0, 0);
                for (ModelKec m : response.body()) {
                    modelDomisilis.add(new ModelDomisili(m.getId(), m.getName()));
                }
                rVadapterDomisili.setDomisiliModel(modelDomisilis);
                rVadapterDomisili.notifyDataSetChanged();
                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<ModelKec>> call, Throwable t) {
                Toast.makeText(getActivity(), "Cant get Data", Toast.LENGTH_SHORT).show();
                res = "";
                loadingDialog.dismiss();
                dismiss();
            }
        });
    }

    private void getDes(String id) {
        LoadingDialog loadingDialog = new LoadingDialog();
        loadingDialog.show(getChildFragmentManager(), "loading");
        Call<ArrayList<ModelDes>> get_desa = restApiLOCATION.getDes(id);
        get_desa.enqueue(new Callback<ArrayList<ModelDes>>() {
            @Override
            public void onResponse(Call<ArrayList<ModelDes>> call, Response<ArrayList<ModelDes>> response) {
                modelDomisilis.clear();
                rVadapterDomisili.notifyItemRangeRemoved(0, 0);
                for (ModelDes m : response.body()) {
                    modelDomisilis.add(new ModelDomisili(m.getId(), m.getName()));
                }
                rVadapterDomisili.setDomisiliModel(modelDomisilis);
                rVadapterDomisili.notifyDataSetChanged();
                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<ModelDes>> call, Throwable t) {
                Toast.makeText(getActivity(), "Cant get Data", Toast.LENGTH_SHORT).show();
                res = "";
                loadingDialog.dismiss();
                dismiss();
            }
        });
    }


    @Override
    public void onClickDomisiliPlace(String name, String Id, int state) {
        if (state == 4) {
            res += "Desa "+name;
            Toast.makeText(getActivity(), res, Toast.LENGTH_SHORT).show();
            target.setText(res);
            dismiss();
        } else {

            if (state == 1) {
                res += "Prov "+name+", ";
                stateTV.setText("Pilih Kabupaten");
                getKab(Id);
            } else if (state == 2) {
                res += "Kab "+name+", ";
                stateTV.setText("Pilih Kecamatan");
                getKec(Id);
            } else if (state == 3) {
                res += "Kec "+name+", ";
                stateTV.setText("Pilih Kalurahan/Desa");
                getDes(Id);
            }
        }
    }
}
