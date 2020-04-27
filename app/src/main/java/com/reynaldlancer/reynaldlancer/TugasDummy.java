package com.reynaldlancer.reynaldlancer;

import java.util.ArrayList;

public class TugasDummy {


    public TugasDummy() {

    }


    ArrayList<TugasModel> dummy() {
        ArrayList<TugasModel> data;
        data = new ArrayList<TugasModel>();
        String nama = "Lorem Ipsum";
        String Judul = "Lorem ipsum dolor sit amet, consectetur adipisicing elit";
        String Deskripsi = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse";
        boolean tempat = false;
        int harga = 250000;
        int foto[] = {R.mipmap.user1, R.mipmap.user2, R.mipmap.user3, R.mipmap.user4, R.mipmap.user5, R.mipmap.user6, R.mipmap.user7, R.mipmap.user8, R.mipmap.user9, R.mipmap.user10};

        for (int i = 0 ; i < 10; i++){
            data.add(new TugasModel(nama, Judul, Deskripsi, tempat, harga ,foto[i]));
        }
        return data;
    }
}
