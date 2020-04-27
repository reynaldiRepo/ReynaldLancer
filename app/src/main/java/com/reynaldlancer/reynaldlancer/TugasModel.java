package com.reynaldlancer.reynaldlancer;

public class TugasModel {
    public String nama, judul, deskripsi;
    public boolean tempat;
    int harga;

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    int foto;

    public TugasModel(String nama, String judul, String deskripsi, boolean tempat, int harga, int foto) {
        this.nama = nama;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.tempat = tempat;
        this.harga = harga;
        this.foto = foto;

    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
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

    public boolean isTempat() {
        return tempat;
    }

    public void setTempat(boolean tempat) {
        this.tempat = tempat;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}
