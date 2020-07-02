package com.reynaldlancer.reynaldlancer;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RestApiUSER {
    @FormUrlEncoded
    @POST("/user/register")
    Call<JsonObject> register(
            @Field("_id") String _id,
            @Field("nama") String nama,
            @Field("no_telephone") String no_telephone,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("/user/login")
    Call<JsonObject> login(
            @Field("_id") String _id,
            @Field("password") String password
    );

    @GET("/user/get")
    Call<ModelUser> getUser(@Query("_id") String _id);

    @FormUrlEncoded
    @POST("/user/update")
    Call<JsonObject> update_photo_profire(@Field("_id") String _id, @Field("photo_profile") String filename);

    @FormUrlEncoded
    @POST("/user/update")
    Call<JsonObject> update_profire(@Field("_id") String _id,
                                    @Field("nama") String nama,
                                    @Field("alamat") String alamat,
                                    @Field("no_telephone") String no_telephone,
                                    @Field("gender") String gender,
                                    @Field("info_tambahan") String info_tambahan,
                                    @Field("domisili") String domisili
    );

    @FormUrlEncoded
    @POST("user/add_skill")
    Call<JsonObject> add_user_skill(@Field("data") String data,
                                               @Field("user_id") String user_id);

    @GET("/user/skill")
    Call<ArrayList<ModelUserSkill>> get_user_skill(@Query("user_id") String user);

    @FormUrlEncoded
    @POST("/user/del_skill")
    Call<JsonObject> del_user_skill(@Field("_id") String _id);

    @GET("/user/sosmed")
    Call<ArrayList<ModelSosmed>> get_user_sosmed(@Query("user_id") String user_id);

    @FormUrlEncoded
    @POST("/user/add_sosmed")
    Call<JsonObject> add_user_sosmed(@Field("user_id") String user_id,
                                     @Field("sosmed_type") String sosmed_type,
                                     @Field("link_sosmed") String link_sosmed);

    @FormUrlEncoded
    @POST("/user/del_sosmed")
    Call<JsonObject> del_user_sosmed(@Field("_id") String _id);

    @FormUrlEncoded
    @POST("user/update_sosmed")
    Call <JsonObject> update_user_sosmed(@Field("_id") String _id, @Field("link") String link);


    @GET("user/pendidikan")
    Call <ArrayList<ModelPendidikanUser>> get_pendidikan(@Query("user_id") String user_id);

    @FormUrlEncoded
    @POST("user/add_pendidikan")
    Call<JsonObject> add_pendidikan(@Field("user_id") String user_id,
                                    @Field("pendidikan") String Pendidikan,
                                    @Field("tingkat") String tingkat);

    @FormUrlEncoded
    @POST("user/update_pendidikan")
    Call<JsonObject> update_pendidikan(@Field("_id") String _id ,@Field("pendidikan") String Pendidikan,
                                       @Field("tingkat") String tingkat);

    @FormUrlEncoded
    @POST("user/del_pendidikan")
    Call<JsonObject> del_pendidikan(@Field("_id") String _id);

    //for saldo
    @FormUrlEncoded
    @POST("user/create_transaksi")
    Call<ModelTransaksiSaldo> create_transaksi(@Field("user_id") String user_id, @Field("jumlah") String jumlah,
                                               @Field("status") String status, @Field("tanggal") String tanggal);

    @GET("user/get_transaksi_all")
    Call<ArrayList<ModelTransaksiSaldo>> get_transaksi_all(@Query("user_id") String user_id);

    @GET("user/get_transaksi")
    Call<ModelTransaksiSaldo> get_transaksi(@Query("_id") String _id);

    @FormUrlEncoded
    @POST("user/update_transaksi")
    Call<JsonObject> update_transaksi(@Field("_id") String _id, @Field("status") String status);

    @FormUrlEncoded
    @POST("user/tambah_saldo")
    Call<JsonObject> tambah_saldo(@Field("_id") String _id, @Field("jumlah") Integer Saldo);

    @FormUrlEncoded
    @POST("user/tarik_saldo")
    Call<JsonObject> tarik_saldo(@Field("_id") String _id, @Field("jumlah") Integer Saldo);

    @FormUrlEncoded
    @POST("/user/buat_tugas")
    Call<JsonObject> buat_tugas(@Field("user_id") String user_id,
                                @Field("kategori") String kategori,
                                @Field("judul") String judul,
                                @Field("deskripsi") String deskripsi,
                                @Field("image") String Image,
                                @Field("mulai") String mulai,
                                @Field("selesai") String selesai,
                                @Field("upah") String upah);

    @GET("user/get_tugas")
    Call<ModelTugas> get_tugas(@Query("_id") String _id);

    @GET("user/get_all_tugas")
    Call<ArrayList<ModelTugas>> get_all_tugas();

}
