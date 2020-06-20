package com.reynaldlancer.reynaldlancer;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestApiUSER {
    @FormUrlEncoded
    @POST("/user/register")
    Call <JsonObject> register (
            @Field("_id") String _id,
            @Field("nama") String nama,
            @Field("no_telephone") String no_telephone,
            @Field("password") String  password
            );

}
