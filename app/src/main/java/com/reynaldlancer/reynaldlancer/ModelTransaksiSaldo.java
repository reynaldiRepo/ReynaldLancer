package com.reynaldlancer.reynaldlancer;

import com.google.gson.annotations.SerializedName;

public class ModelTransaksiSaldo {
    @SerializedName("_id")
    String _id;
    @SerializedName("user_id")
    String user_id;
    @SerializedName("jumlah")
    Integer jumlah;
    @SerializedName("status")
    String status;
    @SerializedName("tanggal")
    String tanggal;

    public ModelTransaksiSaldo(String _id, String user_id, Integer jumlah, String status, String tanggal) {
        this._id = _id;
        this.user_id = user_id;
        this.jumlah = jumlah;
        this.status = status;
        this.tanggal = tanggal;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
