package com.reynaldlancer.reynaldlancer;

import com.google.gson.annotations.SerializedName;

public class ModelTugas {

    @SerializedName("user_id")
    String user_id;
    @SerializedName("kategori")
    String kategori;
    @SerializedName("judul")
    String judul;
    @SerializedName("deskripsi")
    String deskripsi;
    @SerializedName("image")
    String image;
    @SerializedName("mulai")
    String mulai;
    @SerializedName("selesai")
    String selesai;

    public ModelTugas(String user_id, String kategori, String judul, String deskripsi, String image, String mulai, String selesai) {
        this.user_id = user_id;
        this.kategori = kategori;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.image = image;
        this.mulai = mulai;
        this.selesai = selesai;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMulai() {
        return mulai;
    }

    public void setMulai(String mulai) {
        this.mulai = mulai;
    }

    public String getSelesai() {
        return selesai;
    }

    public void setSelesai(String selesai) {
        this.selesai = selesai;
    }

}
