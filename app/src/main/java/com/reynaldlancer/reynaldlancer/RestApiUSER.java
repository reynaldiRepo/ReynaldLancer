package com.reynaldlancer.reynaldlancer;

import com.google.gson.JsonObject;

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
    Call <JsonObject> register (
            @Field("_id") String _id,
            @Field("nama") String nama,
            @Field("no_telephone") String no_telephone,
            @Field("password") String  password
            );

    @FormUrlEncoded
    @POST("user/login")
    Call<JsonObject> login (
            @Field("_id") String _id,
            @Field("password")String password
    );

    @GET("user/get")
    Call<ModelUser> getUser (@Query("_id") String _id);



}
