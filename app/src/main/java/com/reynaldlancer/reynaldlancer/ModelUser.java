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
    @SerializedName("photo_profile")
    String photo_profile = "default.png";
    @SerializedName("gender")
    String gender = "L";
    @SerializedName("info_tambahan")
    String info_tambahan;
    @SerializedName("domisili")
    String domisili;

    public String getDomisili() {
        return domisili;
    }

    public void setDomisili(String domisili) {
        this.domisili = domisili;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getInfo_tambahan() {
        return info_tambahan;
    }

    public void setInfo_tambahan(String info_tambahan) {
        this.info_tambahan = info_tambahan;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @SerializedName("status")
    Boolean status;

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

    public String getPhoto_profile() {
        return photo_profile;
    }

    public void setPhoto_profile(String photo_profile) {
        this.photo_profile = photo_profile;
    }
}


