package com.reynaldlancer.reynaldlancer;

public class ItemMisiModel {
    Integer harga;
    Integer komisi;
    Integer Target;

    public ItemMisiModel() {
        this.harga = 0;
        this.komisi = 0;
        Target = 0;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public Integer getKomisi() {
        return komisi;
    }

    public void setKomisi(Integer komisi) {
        this.komisi = komisi;
    }

    public Integer getTarget() {
        return Target;
    }

    public void setTarget(Integer target) {
        Target = target;
    }


}
