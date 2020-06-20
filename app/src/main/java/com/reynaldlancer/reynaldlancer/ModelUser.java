package com.reynaldlancer.reynaldlancer;

import com.google.gson.annotations.SerializedName;

public class ModelUser {
    @SerializedName("nama")
    String nama;
    @SerializedName("_id")
    String _id;
    @SerializedName("alamat")
    String alamat = "";
    @SerializedName("no_telephone")
    String no_telephone;
    @SerializedName("ratting")
    Double ratting = 0.0;
    @SerializedName("saldo")
    Integer saldo = 0;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNo_telephone() {
        return no_telephone;
    }

    public void setNo_telephone(String no_telephone) {
        this.no_telephone = no_telephone;
    }

    public Double getRatting() {
        return ratting;
    }

    public void setRatting(Double ratting) {
        this.ratting = ratting;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }
}


