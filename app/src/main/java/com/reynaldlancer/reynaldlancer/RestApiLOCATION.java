package com.reynaldlancer.reynaldlancer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApiLOCATION {
    @GET("/loc/prov")
    Call<ArrayList<ModelProv>> getProv();

    @GET("/loc/kota")
    Call<ArrayList<ModelKab>> getKab(
            @Query("prov") String province_id
    );

    @GET("/loc/kec")
    Call<ArrayList<ModelKec>> getKec(
            @Query("kab") String kab_id
    );

    @GET("/loc/des")
    Call<ArrayList<ModelDes>> getDes(
            @Query("kec") String kec_id
    );
}
